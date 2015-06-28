package net.dragora.papinomoviedb.ui.movie_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.dragora.papinomoviedb.MyApplication;
import net.dragora.papinomoviedb.common.view.RecyclerViewAdapterBase;
import net.dragora.papinomoviedb.common.view.ViewWrapper;
import net.dragora.papinomoviedb.data.MainPrefs_;
import net.dragora.papinomoviedb.model.Movie;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;



/**
 * Created by nietzsche on 19/01/15.
 */
@EBean
public class MovieRecyclerAdapter extends RecyclerViewAdapterBase<Movie, MovieView> {

    @RootContext
    Context context;

    @Pref
    MainPrefs_ mainPrefs;

    public void update() {

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

            MyApplication.registerOnEventBus(this);

    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        try {
            MyApplication.getBus().unregister(this);
        } finally {

        }
    }



    public void setItems(List<Movie> array) {
        items = array ;
    }



    @Override
    public void onBindViewHolder(ViewWrapper<MovieView> holder, int position) {
        MovieView view = holder.getView();
        Movie shop = items.get(position);
        view.bind(shop);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    protected MovieView onCreateItemView(ViewGroup parent, int viewType) {
        return MovieView_.build(context);
    }


}
