package net.dragora.papinomoviedb.ui.movie_detail;


import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.api.GetMovieReviewsTask;
import net.dragora.papinomoviedb.common.Tools;
import net.dragora.papinomoviedb.common.api.ApiTaskCallbacks;
import net.dragora.papinomoviedb.common.fragment.MyFragment;
import net.dragora.papinomoviedb.data.MovieDbHelper;
import net.dragora.papinomoviedb.model.ReviewMovie;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_cast)
public class ReviewMovieFragment extends MyFragment {

    @FragmentArg
    String movieId;

    @ViewById
    UltimateRecyclerView recyclerView;
    @ViewById
    TextView emptyView;

    ReviewMovieRecyclerAdapter adapter;

    @AfterViews
    public void setup() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = ReviewMovieRecyclerAdapter_.getInstance_(getActivity());
        recyclerView.setAdapter(adapter);

        new GetMovieReviewsTask( apiTaskCallbacks, movieId, new MovieDbHelper(getActivity())).execute();
    }

    ApiTaskCallbacks<List<ReviewMovie>> apiTaskCallbacks = new ApiTaskCallbacks<List<ReviewMovie>>() {
        @Override
        public void onInit() {

        }

        @Override
        public void onCancelled() {
            Tools.snackbar(getActivity(),R.string.request_error);
        }

        @Override
        public void onResponse(List<ReviewMovie> result) {

            adapter.setItems(result);
            adapter.notifyDataSetChanged();
            emptyView.setVisibility(adapter.getItemCount() != 0 ? View.GONE: View.VISIBLE);
        }

        @Override
        public void onRequestError(String message) {
            Tools.snackbar(getActivity(),R.string.request_error);
            emptyView.setVisibility(adapter.getItemCount() != 0 ? View.GONE: View.VISIBLE);
        }
    };
}
