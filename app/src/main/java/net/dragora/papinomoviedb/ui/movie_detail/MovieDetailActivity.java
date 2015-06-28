package net.dragora.papinomoviedb.ui.movie_detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.ui.movie_list.MovieListActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link MovieDetailFragment}.
 */
@EActivity(R.layout.activity_movie_detail)
public class MovieDetailActivity extends AppCompatActivity {

    @Extra
    String itemId;
    private Bundle savedInstanceState;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @AfterViews
    public void setup() {
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            MovieDetailFragment fragment = MovieDetailFragment_.builder().movieId(itemId).build();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }

    @OptionsItem(android.R.id.home)
    public void onUpPressed() {
        // Something gone wrong with this
        //NavUtils.navigateUpFromSameTask(this);
        onBackPressed();

    }

}
