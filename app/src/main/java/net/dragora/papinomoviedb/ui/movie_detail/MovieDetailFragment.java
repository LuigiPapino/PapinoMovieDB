package net.dragora.papinomoviedb.ui.movie_detail;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.dragora.papinomoviedb.MyApplication;
import net.dragora.papinomoviedb.R;
import net.dragora.papinomoviedb.common.ImageZoomActivity;
import net.dragora.papinomoviedb.common.fragment.MyFragment;
import net.dragora.papinomoviedb.data.MainPrefs_;
import net.dragora.papinomoviedb.data.MovieDbHelper;
import net.dragora.papinomoviedb.model.Movie;
import net.dragora.papinomoviedb.ui.movie_list.MovieListActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */

@EFragment(R.layout.fragment_movie_detail)
public class MovieDetailFragment extends MyFragment {

    @ViewById
    ImageView thumbnail, backdrop;
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
    @ViewById
    MaterialTabHost materialTabHost;
    @ViewById
    ViewPager viewpager;

    @Pref
    MainPrefs_ mainPrefs;

    private Movie movie;

    @FragmentArg
    String movieId;

    @AfterViews
    public void setup() {
        SQLiteDatabase db = new MovieDbHelper(getActivity()).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movie WHERE id = ?", new String[]{movieId});
        if (cursor != null && cursor.moveToFirst()) {
            movie = Movie.fromCursor(cursor);
            bind(movie);
            cursor.close();
        }
        db.close();


        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        viewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                materialTabHost.setSelectedNavigationItem(position);
            }
        });
        // insert all tabs from pagerAdapter data
        materialTabHost.addTab(
                materialTabHost.newTab()
                        .setTabListener(materialTabListener)
                        .setText(getString(R.string.cast))
        );
        materialTabHost.addTab(
                materialTabHost.newTab()
                        .setTabListener(materialTabListener)
                        .setText(getString(R.string.trailers))
        );
        materialTabHost.addTab(
                materialTabHost.newTab()
                        .setTabListener(materialTabListener)
                        .setText(getString(R.string.reviews))
        );
        materialTabHost.addTab(
                materialTabHost.newTab()
                        .setTabListener(materialTabListener)
                        .setText(getString(R.string.crew))
        );


    }

    private MaterialTabListener materialTabListener = new MaterialTabListener() {
        @Override
        public void onTabSelected(MaterialTab materialTab) {
            viewpager.setCurrentItem(materialTab.getPosition());
        }

        @Override
        public void onTabReselected(MaterialTab materialTab) {

        }

        @Override
        public void onTabUnselected(MaterialTab materialTab) {

        }
    };

    @Click
    public void thumbnail() {
        if (MyApplication.configuration != null) {
            int size = MyApplication.configuration.getImages().getPosterSizes().size() - 1;
            String url = String.format("%s%s%s", //http://image.tmdb.org/t/p/w92/2i0JH5WqYFqki7WDhUW56Sg0obh.jpg
                    MyApplication.configuration.getImages().getBaseUrl(),
                    MyApplication.configuration.getImages().getPosterSizes().get(size), //TODO check length
                    movie.getPosterPath()
            );
            ImageZoomActivity.startWithTansition(thumbnail, getMyActivity(), url);

        }
    }

    @Click
    public void backdrop() {
        if (MyApplication.configuration != null) {
            int size = MyApplication.configuration.getImages().getBackdropSizes().size() - 1;
            String url = String.format("%s%s%s", //http://image.tmdb.org/t/p/w92/2i0JH5WqYFqki7WDhUW56Sg0obh.jpg
                    MyApplication.configuration.getImages().getBaseUrl(),
                    MyApplication.configuration.getImages().getBackdropSizes().get(size), //TODO check length
                    movie.getBackdropPath()
            );
            ImageZoomActivity.startWithTansition(backdrop, getMyActivity(), url);
        }
    }

    public void bind(final Movie movie) {
        this.movie = movie;
        title.setText(movie.getTitle());
        thumbnail.setImageDrawable(null);
        backdrop.setImageResource(R.color.accent);
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
                    MyApplication.configuration.getImages().getPosterSizes().get(1), //TODO check length
                    movie.getPosterPath()
            );
            ImageLoader.getInstance().displayImage(url, thumbnail);


            String urlBackdrop = String.format("%s%s%s", //http://image.tmdb.org/t/p/w92/2i0JH5WqYFqki7WDhUW56Sg0obh.jpg
                    MyApplication.configuration.getImages().getBaseUrl(),
                    MyApplication.configuration.getImages().getBackdropSizes().get(1), //TODO check length
                    movie.getBackdropPath()
            );
            ImageLoader.getInstance().displayImage(urlBackdrop, backdrop);
        }


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {
            if (num == 0) {
                return CastFragment_.builder().movieId(movieId).build();
            } else if (num == 3) {
                return CrewFragment_.builder().movieId(movieId).build();
            } else if (num == 2) {
                return ReviewMovieFragment_.builder().movieId(movieId).build();
            }else if (num == 1) {
                return TrailerMovieFragment_.builder().movieId(movieId).build();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "tab";
        }
    }

    private SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private SimpleDateFormat sdfOutput = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
}
