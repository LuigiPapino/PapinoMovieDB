package net.dragora.papinomoviedb.ui.movie_list;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Subscribe;

import net.dragora.papinomoviedb.MyApplication;
import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.api.GetConfigurationAndGenresTask;
import net.dragora.papinomoviedb.api.GetDiscoverMovieTask;
import net.dragora.papinomoviedb.common.Tools;
import net.dragora.papinomoviedb.common.api.ApiTaskCallbacks;
import net.dragora.papinomoviedb.data.MainPrefs_;
import net.dragora.papinomoviedb.data.MovieDbHelper;
import net.dragora.papinomoviedb.model.Configuration;
import net.dragora.papinomoviedb.model.Movie;
import net.dragora.papinomoviedb.model.event_bus.GetDiscoverCompleted;
import net.dragora.papinomoviedb.model.event_bus.GetDiscoverError;
import net.dragora.papinomoviedb.model.event_bus.ShowMovieDetailAction;
import net.dragora.papinomoviedb.ui.movie_detail.MovieDetailActivity;
import net.dragora.papinomoviedb.ui.movie_detail.MovieDetailActivity_;
import net.dragora.papinomoviedb.ui.movie_detail.MovieDetailFragment;
import net.dragora.papinomoviedb.ui.movie_detail.MovieDetailFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;


/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MovieListFragment} and the item details
 * (if present) is a {@link MovieDetailFragment}.
 * <p/>
 */
@EActivity(R.layout.activity_movie_list)
public class MovieListActivity extends AppCompatActivity {

    @Pref
    MainPrefs_ mainPrefs;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.registerOnEventBus(this);

        new GetConfigurationAndGenresTask(configurationApiTaskCallbacks, mainPrefs, false).execute();
    }


    @AfterViews
    public void setup() {
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            MyApplication.setIsTwoPane(mTwoPane);
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MovieListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.movie_list))
                    .setActivateOnItemClick(true);

        }
    }

    @Override
    protected void onDestroy() {
        MyApplication.unregisterOnEventBus(this);
        super.onDestroy();
    }

    private ApiTaskCallbacks<Configuration> configurationApiTaskCallbacks = new ApiTaskCallbacks<Configuration>() {
        @Override
        public void onInit() {

        }

        @Override
        public void onCancelled() {
            MyApplication.postBusEvent(new GetDiscoverError(null));
            Tools.snackbar(MovieListActivity.this, R.string.request_cancelled);


        }

        @Override
        public void onResponse(Configuration result) {
            new GetDiscoverMovieTask(discoverMovieApiTaskCallbacks, mainPrefs, new MovieDbHelper(MovieListActivity.this), false, false).execute();
        }

        @Override
        public void onRequestError(String message) {
            MyApplication.postBusEvent(new GetDiscoverError(message));
            Tools.snackbar(MovieListActivity.this, R.string.request_error);
        }
    };


    private ApiTaskCallbacks<List<Movie>> discoverMovieApiTaskCallbacks = new ApiTaskCallbacks<List<Movie>>() {
        @Override
        public void onInit() {

        }

        @Override
        public void onCancelled() {
            MyApplication.postBusEvent(new GetDiscoverError(null));
            Tools.snackbar(MovieListActivity.this, R.string.request_cancelled);

        }

        @Override
        public void onResponse(List<Movie> result) {

            MyApplication.postBusEvent(new GetDiscoverCompleted(result));
        }

        @Override
        public void onRequestError(String message) {
            MyApplication.postBusEvent(new GetDiscoverError(message));
            Tools.snackbar(MovieListActivity.this, R.string.request_error);
        }
    };

    @Subscribe
    public void showMovieDetail(ShowMovieDetailAction action) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            MovieDetailFragment fragment = MovieDetailFragment_.builder().movieId(action.getId()).build();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    Pair.create(action.getThumbnail(), getResources().getString(R.string.transitionMovieThumbnail))
            );

            ActivityCompat.startActivity(this,
                    MovieDetailActivity_.intent(this).itemId(action.getId()).get()
                    , optionsCompat.toBundle());

        }
    }

    @Subscribe
    public void discoverCompleted(GetDiscoverCompleted event) {
        // adding or replacing the detail fragment using a
        // fragment transaction.
        if (mTwoPane && event.getMovies() != null && event.getMovies().size() > 0) {
            MovieDetailFragment fragment = MovieDetailFragment_.builder().movieId(event.getMovies().get(0).getId().toString()).build();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }


}
