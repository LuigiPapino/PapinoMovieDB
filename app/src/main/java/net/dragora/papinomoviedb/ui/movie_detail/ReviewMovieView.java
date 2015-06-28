package net.dragora.papinomoviedb.ui.movie_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.model.ReviewMovie;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 27/06/15.
 */
@EViewGroup(R.layout.view_movie_review_item)
public class ReviewMovieView extends RelativeLayout {


    @ViewById
    TextView author;
    @ViewById
    TextView content;

    public ReviewMovieView(Context context) {
        super(context);
    }

    public ReviewMovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReviewMovieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ReviewMovie review;

    public void bind(final ReviewMovie review) {
        this.review = review;


        author.setText(review.getAuthor());
        content.setText(review.getContent());

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(review.getUrl()));
                getContext().startActivity(intent);
            }
        });

    }
}
