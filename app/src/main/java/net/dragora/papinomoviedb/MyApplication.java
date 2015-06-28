package net.dragora.papinomoviedb;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.otto.Bus;

import net.dragora.papinomoviedb.model.Configuration;
import net.dragora.papinomoviedb.model.GenreMovie;

import org.androidannotations.annotations.EApplication;

import java.util.HashMap;
import java.util.List;

/**
 * Created by nietzsche on 25/06/15.
 */
@EApplication
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);

    }

    private static Bus bus = new Bus();

    public static void registerOnEventBus(Object object){
        try {
            bus.register(object);
        }catch (Exception e){

        }

    }

    public static void unregisterOnEventBus(Object object){
        try {
            bus.unregister(object);
        }catch (Exception e){

        }

    }

    public static void postBusEvent(Object event){

        bus.post(event);
    }
    public static Bus getBus() {
        return bus;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        MyApplication.configuration = configuration;
    }

    public static Configuration configuration = null;

    public static String getGenre(Integer id) {
        return genres.containsKey(id) ? genres.get(id).getName() : null;
    }

    public static void setGenres(List<GenreMovie> genres) {
        for (GenreMovie genre: genres){
            MyApplication.genres.put(genre.getId(), genre);
        }
    }

    private static HashMap<Integer, GenreMovie> genres = new HashMap<>();

    public static boolean isTwoPane() {
        return isTwoPane;
    }

    public static void setIsTwoPane(boolean isTwoPane) {
        MyApplication.isTwoPane = isTwoPane;
    }

    private static boolean isTwoPane = false;
}
