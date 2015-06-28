package net.dragora.papinomoviedb.ui.movie_detail;


import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.api.GetMovieCastsTask;
import net.dragora.papinomoviedb.common.Tools;
import net.dragora.papinomoviedb.common.api.ApiTaskCallbacks;
import net.dragora.papinomoviedb.common.fragment.MyFragment;
import net.dragora.papinomoviedb.data.MovieDbHelper;
import net.dragora.papinomoviedb.model.Cast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_cast)
public class CastFragment extends MyFragment {

    @FragmentArg
    String movieId;

    @ViewById
    UltimateRecyclerView recyclerView;

    CastRecyclerAdapter adapter;

    @AfterViews
    public void setup() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = CastRecyclerAdapter_.getInstance_(getActivity());
        recyclerView.setAdapter(adapter);
        new GetMovieCastsTask( apiTaskCallbacks, movieId, new MovieDbHelper(getActivity())).execute();
    }

    ApiTaskCallbacks<List<Cast>> apiTaskCallbacks = new ApiTaskCallbacks<List<Cast>>() {
        @Override
        public void onInit() {

        }

        @Override
        public void onCancelled() {
            Tools.snackbar(getActivity(),R.string.request_error);
        }

        @Override
        public void onResponse(List<Cast> result) {

            adapter.setItems(result);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRequestError(String message) {
            Tools.snackbar(getActivity(),R.string.request_error);
        }
    };
}
