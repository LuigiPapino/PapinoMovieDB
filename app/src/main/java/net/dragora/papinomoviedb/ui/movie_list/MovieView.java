package net.dragora.papinomoviedb.ui.movie_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.otto.Subscribe;

import net.dragora.papinomoviedb.MyApplication;
import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.data.MainPrefs_;
import net.dragora.papinomoviedb.model.Movie;
import net.dragora.papinomoviedb.model.event_bus.ShowMovieDetailAction;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by nietzsche on 27/06/15.
 */
@EViewGroup(R.layout.view_movie_item)
public class MovieView extends RelativeLayout {

    @ViewById
    View selected;

    @ViewById
    ImageView thumbnail;
    @ViewById
    TextView title;
    @ViewById
    TextView year, categories;
    @ViewById
    TextView popularity;
    @ViewById
    ImageView star;
    @ViewById
    LinearLayout popularityLayout;
    @Pref
    MainPrefs_ mainPrefs;

    public MovieView(Context context) {
        super(context);
    }

    public MovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MyApplication.registerOnEventBus(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MyApplication.unregisterOnEventBus(this);
    }

    private String lastId = "-1";
    @Subscribe
    public void showMovieDetail(ShowMovieDetailAction action) {
        lastId = action.getId();
        selected.setVisibility(GONE);
        if (MyApplication.isTwoPane() && movie != null && action.getId().contentEquals(movie.getId().toString())){
            selected.setVisibility(VISIBLE);
        }
    }


    private Movie movie;

    public void bind(final Movie movie) {
        this.movie = movie;
        selected.setVisibility(MyApplication.isTwoPane() && lastId.contentEquals(movie.getId().toString()) ? VISIBLE : GONE);
        title.setText(movie.getTitle());
        thumbnail.setImageDrawable(null);

        try {
            year.setText(
                    sdfOutput.format(
                            sdfInput.parse(movie.getReleaseDate())
                    )
            );
        } catch (ParseException e) {
            year.setText("");
        }

        popularity.setText(String.format("%.1f", movie.getPopularity()));

        StringBuffer genres = new StringBuffer();
        for (Integer id : movie.getGenreIds()) {
            String name = MyApplication.getGenre(id);
            if (!TextUtils.isEmpty(name)) {
                genres.append(name + ", ");
            }
        }
        if (genres.length() > 0) {
            genres.delete(genres.length() - 3, genres.length() - 1);
            categories.setText(genres.toString());
        }

        if (MyApplication.configuration != null) {
            String url = String.format("%s%s%s", //http://image.tmdb.org/t/p/w92/2i0JH5WqYFqki7WDhUW56Sg0obh.jpg
                    MyApplication.configuration.getImages().getBaseUrl(),
                    MyApplication.configuration.getImages().getPosterSizes().get(0), //TODO check length
                    movie.getPosterPath()
            );
            ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    thumbnail.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.postBusEvent(new ShowMovieDetailAction(movie.getId(), thumbnail));
            }
        });
    }

    private SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private SimpleDateFormat sdfOutput = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
}
