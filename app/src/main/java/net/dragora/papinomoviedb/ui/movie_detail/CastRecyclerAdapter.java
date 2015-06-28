package net.dragora.papinomoviedb.ui.movie_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.dragora.papinomoviedb.MyApplication;
import net.dragora.papinomoviedb.common.view.RecyclerViewAdapterBase;
import net.dragora.papinomoviedb.common.view.ViewWrapper;
import net.dragora.papinomoviedb.model.Cast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;


/**
 * Created by nietzsche on 19/01/15.
 */
@EBean
public class CastRecyclerAdapter extends RecyclerViewAdapterBase<Cast, CastView> {

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



    public void setItems(List<Cast> array) {
        items = array ;
    }



    @Override
    public void onBindViewHolder(ViewWrapper<CastView> holder, int position) {
        CastView view = holder.getView();
        Cast shop = items.get(position);
        view.bind(shop);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    protected CastView onCreateItemView(ViewGroup parent, int viewType) {
        return CastView_.build(context);
    }


}
