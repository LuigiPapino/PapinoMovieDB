package net.dragora.papinomoviedb.ui.movie_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.dragora.papinomoviedb.MyApplication;
import net.dragora.papinomoviedb.common.view.RecyclerViewAdapterBase;
import net.dragora.papinomoviedb.common.view.ViewWrapper;
import net.dragora.papinomoviedb.model.ReviewMovie;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;


/**
 * Created by nietzsche on 19/01/15.
 */
@EBean
public class ReviewMovieRecyclerAdapter extends RecyclerViewAdapterBase<ReviewMovie, ReviewMovieView> {

    @RootContext
    Context context;


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



    public void setItems(List<ReviewMovie> array) {
        items = array ;
    }



    @Override
    public void onBindViewHolder(ViewWrapper<ReviewMovieView> holder, int position) {
        ReviewMovieView view = holder.getView();
        ReviewMovie shop = items.get(position);
        view.bind(shop);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    protected ReviewMovieView onCreateItemView(ViewGroup parent, int viewType) {
        return ReviewMovieView_.build(context);
    }


}
