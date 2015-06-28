package net.dragora.papinomoviedb.ui.movie_detail;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.dragora.papinomoviedb.MyApplication;
import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.model.Cast;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 27/06/15.
 */
@EViewGroup(R.layout.view_cast_item)
public class CastView extends RelativeLayout {
    @ViewById
    ImageView photo;
    @ViewById
    TextView name;
    @ViewById
    TextView character;

    public CastView(Context context) {
        super(context);
    }

    public CastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Cast cast;

    public void bind(Cast cast) {
        this.cast = cast;

        photo.setImageDrawable(null);
        if (MyApplication.getConfiguration() != null && !TextUtils.isEmpty(cast.getProfilePath())) {
            String url = String.format("%s%s%s",
                    MyApplication.getConfiguration().getImages().getBaseUrl(),
                    MyApplication.getConfiguration().getImages().getProfileSizes().get(1),
                    cast.getProfilePath()
            );
            ImageLoader.getInstance().displayImage(url, photo);
        }
        name.setText(cast.getName());
        character.setText(cast.getCharacter());

    }
}
