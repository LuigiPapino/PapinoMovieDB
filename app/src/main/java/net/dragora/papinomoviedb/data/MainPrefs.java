package net.dragora.papinomoviedb.data;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by nietzsche on 27/06/15.
 */
@SharedPref
public interface MainPrefs {

    @DefaultString("")
    String configuration();

    @DefaultString("")
    String genres();

    @DefaultInt(1)
    int nextDiscoverPageToLoad();
}
