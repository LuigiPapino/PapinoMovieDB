package net.dragora.papinomoviedb.model.event_bus;

import android.view.View;

/**
 * Created by nietzsche on 27/06/15.
 */
public class ShowMovieDetailAction {

    public View getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(View thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private View thumbnail;

    public ShowMovieDetailAction(int id, View thumbnail) {
        this.id = id;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id.toString();
    }

    Integer id;
}
