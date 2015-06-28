package net.dragora.papinomoviedb.ui.movie_detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.model.TrailerMovie;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 27/06/15.
 */
@EViewGroup(R.layout.view_movie_trailer_item)
public class TrailerMovieView extends RelativeLayout {


    @ViewById
    TextView name;
    @ViewById
    TextView site;

    public TrailerMovieView(Context context) {
        super(context);
    }

    public TrailerMovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrailerMovieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private TrailerMovie trailer;

    public void bind(final TrailerMovie review) {
        this.trailer = review;


        name.setText(review.getName());
        site.setText(review.getSite());

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
                    getContext().startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                    getContext().startActivity(intent);
                }
            }
        });

    }
}
