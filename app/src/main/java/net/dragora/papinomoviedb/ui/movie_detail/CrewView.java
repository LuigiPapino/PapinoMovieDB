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
import net.dragora.papinomoviedb.model.Crew;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 27/06/15.
 */
@EViewGroup(R.layout.view_crew_item)
public class CrewView extends RelativeLayout {
    @ViewById
    ImageView photo;
    @ViewById
    TextView name;
    @ViewById
    TextView character;

    public CrewView(Context context) {
        super(context);
    }

    public CrewView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CrewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Crew crew;

    public void bind(Crew crew) {
        this.crew = crew;

        photo.setImageDrawable(null);
        if (MyApplication.getConfiguration() != null && !TextUtils.isEmpty(crew.getProfilePath())) {
            String url = String.format("%s%s%s",
                    MyApplication.getConfiguration().getImages().getBaseUrl(),
                    MyApplication.getConfiguration().getImages().getProfileSizes().get(1),
                    crew.getProfilePath()
            );
            ImageLoader.getInstance().displayImage(url, photo);
        }
        name.setText(crew.getName());
        character.setText(crew.getDepartment() + ", " + crew.getJob() );

    }
}
