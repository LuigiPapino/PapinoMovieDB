package net.dragora.papinomoviedb.model.event_bus;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by nietzsche on 27/06/15.
 */
public class GetDiscoverError {
    public String getMessage() {
        return message;
    }

    private String message = "Unknown Error";

    public GetDiscoverError(@Nullable String message) {
        if (!TextUtils.isEmpty(message)) {
            this.message = message;
        }
    }
}
