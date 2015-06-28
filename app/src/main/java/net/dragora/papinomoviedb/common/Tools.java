package net.dragora.papinomoviedb.common;

import android.content.Context;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import net.dragora.papinomoviedb.R;

/**
 * Created by nietzsche on 27/06/15.
 */
public class Tools {

    public static void snackbar(Context context, int text) {
        snackbar(context, text, R.string.ok, null, Snackbar.SnackbarDuration.LENGTH_LONG);
    }

    public static void snackbar(Context context, String text) {
        snackbar(context, text, R.string.ok, null);
    }

    public static void snackbar(Context context, String text, int buttonText, ActionClickListener actionClickListener) {
        Snackbar snackbar = Snackbar.with(context).
                text(text).
                actionLabel(buttonText).
                actionColorResource(R.color.accent);
        if (actionClickListener != null) {
            snackbar.actionListener(actionClickListener);
        }
        SnackbarManager.show(snackbar);
    }

    public static void snackbar(Context context, int text, int buttonText, ActionClickListener actionClickListener, Snackbar.SnackbarDuration duration) {
        Snackbar snackbar = Snackbar.with(context).
                text(text).
                actionLabel(buttonText).
                actionColorResource(R.color.accent)
                .duration(duration);
        if (actionClickListener != null) {
            snackbar.actionListener(actionClickListener);
        }
        SnackbarManager.show(snackbar);
    }
}
