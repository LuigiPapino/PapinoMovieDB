package net.dragora.papinomoviedb;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.papinomoviedb.ui.movie_list.MovieListActivity_;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by nietzsche on 27/06/15.
 */
public class MovieListDetailTest extends ActivityInstrumentationTestCase2<MovieListActivity_> {
    public MovieListDetailTest() {
        super(MovieListActivity_.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();

    }

    public void testPullToRefresh() {

        UltimateRecyclerView recyclerView = (UltimateRecyclerView) getActivity().findViewById(R.id.recyclerView);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        onView(withId(R.id.ultimate_list)).perform(ViewActions.swipeDown());
        onView(withId(R.id.swipe_refresh_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    int moviesForPage = 20;
    int pages = 1;

    // this test need more assertion to verify that the behavior is correct
    //
    public void testMovieList() {

        //I can't use R.id.recyclerView because it is ad UltimateRecyclerView that extends from FrameLayout
        //Indeed i must use the real recyclerView in the FrameLAyout
        for (int i = 0; i < moviesForPage * pages; i++) {
            // SCroll for "load more" or "infinite scroll"
            onView(withId(R.id.ultimate_list)).perform(RecyclerViewActions.actionOnItemAtPosition(i, ViewActions.click()));

            onView(withText("TRAILERS")).perform(ViewActions.click());
            onView(withText("REVIEWS")).perform(ViewActions.click());
            onView(withText("CREW")).perform(ViewActions.click());


            Espresso.pressBack();
        }


    }



}
