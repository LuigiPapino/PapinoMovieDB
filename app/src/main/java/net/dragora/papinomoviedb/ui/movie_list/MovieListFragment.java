package net.dragora.papinomoviedb.ui.movie_list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ListView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.squareup.otto.Subscribe;

import net.dragora.papinomoviedb.MyApplication;
import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.api.GetDiscoverMovieTask;
import net.dragora.papinomoviedb.common.Tools;
import net.dragora.papinomoviedb.common.api.ApiTaskCallbacks;
import net.dragora.papinomoviedb.common.fragment.MyFragment;
import net.dragora.papinomoviedb.data.MainPrefs_;
import net.dragora.papinomoviedb.data.MovieDbHelper;
import net.dragora.papinomoviedb.model.Movie;
import net.dragora.papinomoviedb.model.event_bus.GetDiscoverCompleted;
import net.dragora.papinomoviedb.model.event_bus.GetDiscoverError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;


@EFragment(R.layout.fragment_movie_list)
public class MovieListFragment extends MyFragment<MovieListActivity> {


    /**
     * The current activated item position. Only used on tablets.
     */
    @InstanceState
    int mActivatedPosition = ListView.INVALID_POSITION;
    @Pref
    MainPrefs_ mainPrefs;
    @ViewById
    UltimateRecyclerView recyclerView;
    private MovieRecyclerAdapter adapter;

    private LinearLayoutManager linearLayoutManager;

    public MovieListFragment() {

    }


    @AfterViews
    public void setup() {
        linearLayoutManager = new LinearLayoutManager(getMyActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.enableDefaultSwipeRefresh(true);
        adapter = MovieRecyclerAdapter_.getInstance_(getMyActivity());
        recyclerView.setAdapter(adapter);
        setRefreshing(true);
        setActivatedPosition();
        recyclerView.enableLoadmore();
        recyclerView.setOnLoadMoreListener(onLoadMoreListener);
        recyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearAll = true;
                new GetDiscoverMovieTask(apiTaskCallbacks, mainPrefs, new MovieDbHelper(getMyActivity()), true, false).execute();
            }
        });
    }

    boolean clearAll = false;


    @Override
    public void onStart() {
        super.onStart();
        MyApplication.registerOnEventBus(this);

    }

    private UltimateRecyclerView.OnLoadMoreListener onLoadMoreListener = new UltimateRecyclerView.OnLoadMoreListener() {
        @Override
        public void loadMore(int i, int i1) {
            setRefreshing(true);

            new GetDiscoverMovieTask(apiTaskCallbacks, mainPrefs, new MovieDbHelper(getMyActivity()), false, true).execute();
        }
    };


    ApiTaskCallbacks<List<Movie>> apiTaskCallbacks = new ApiTaskCallbacks<List<Movie>>() {

        @Override
        public void onInit() {

        }

        @Override
        public void onCancelled() {
            Tools.snackbar(getMyActivity(), R.string.request_error);
            setRefreshing(false);
        }

        @Override
        public void onResponse(List<Movie> result) {
            if (clearAll) {
                adapter.setItems(result);
                adapter.notifyDataSetChanged();
                clearAll = false;
            } else {
                int prevCount = adapter.getItemCount();
                adapter.items.addAll(prevCount, result);
                adapter.notifyItemRangeInserted(prevCount, prevCount + result.size() - 1);
                setRefreshing(false);
                Tools.snackbar(getMyActivity(), R.string.loaded_more);
            }

        }

        @Override
        public void onRequestError(String message) {
            Tools.snackbar(getMyActivity(), R.string.request_error);
            setRefreshing(false);

        }
    };

    @Override
    public void onDestroy() {
        MyApplication.unregisterOnEventBus(this);
        super.onDestroy();
    }

    @Subscribe
    public void discoverCompleted(GetDiscoverCompleted event) {
        setRefreshing(false);
        adapter.setItems(event.getMovies());
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void discoverError(GetDiscoverError event) {
        setRefreshing(false);
    }


    private void setRefreshing(final boolean isRefreshing) {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshing(isRefreshing);

            }
        });
    }

    private void setActivatedPosition() {
        //TODO Event bus position
    }

    public void setActivateOnItemClick(boolean b) {
        activateOnItemClick = b;
    }

    private boolean activateOnItemClick = false;
}
