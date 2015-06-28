package net.dragora.papinomoviedb.api;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import net.dragora.papinomoviedb.MyApplication;
import net.dragora.papinomoviedb.common.api.ApiTaskCallbacks;
import net.dragora.papinomoviedb.common.api.BaseTask;
import net.dragora.papinomoviedb.data.MainPrefs_;
import net.dragora.papinomoviedb.model.Configuration;
import net.dragora.papinomoviedb.model.GenreMovie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Get configuration from server and store it in mainPreference for future use
 *
 * Created by nietzsche on 25/06/15.
 *
 */
public class GetConfigurationAndGenresTask extends BaseTask<Configuration> {
    protected MainPrefs_ mainPrefs;
    protected boolean forceUpdate = false;

    /**
     * Get configuration from server and store it in mainPreference for future use
     * @param callbacks
     * @param mainPrefs
     */
    public GetConfigurationAndGenresTask(ApiTaskCallbacks<Configuration> callbacks, MainPrefs_ mainPrefs) {
        super(callbacks);
        this.mainPrefs = mainPrefs;
    }

    /**
     * Get configuration from server and store it in mainPreference for future use
     * @param callbacks
     * @param mainPrefs
     * @param forceUpdate force the request to server and overwrite the current saved value
     */
    public GetConfigurationAndGenresTask(ApiTaskCallbacks<Configuration> callbacks, MainPrefs_ mainPrefs, boolean forceUpdate) {
        super(callbacks);
        this.mainPrefs = mainPrefs;
        this.forceUpdate = forceUpdate;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void cancelled() {

    }

    @Override
    protected void background() {
        Configuration configuration;
        List<GenreMovie> genres = null;
        // Check if configuration is alredy present in SharedPref;
        String serializedConfiguration = mainPrefs.configuration().get();
        if (TextUtils.isEmpty(serializedConfiguration) || forceUpdate) {
            MovieWebService webService = WebServiceFactory.getInstance().getMovieWebService();
            result = configuration = webService.configuration();
            mainPrefs.edit().configuration()
                    .put(new Gson().toJson(result))
                    .apply();

        } else {
            result = configuration = new Gson().fromJson(serializedConfiguration, Configuration.class);
        }

        String serializedGenres = mainPrefs.genres().get();
        Type listType = new TypeToken<ArrayList<GenreMovie>>() {
        }.getType();
        if (TextUtils.isEmpty(serializedGenres) || forceUpdate) {
            MovieWebService webService = WebServiceFactory.getInstance().getMovieWebService();
             JsonObject jsonGenres = webService.genreList();
            if (jsonGenres != null && jsonGenres.has("genres")){
                serializedGenres = jsonGenres.getAsJsonArray("genres").toString();
                mainPrefs.edit().genres().put(serializedGenres).apply();
                genres = new Gson().fromJson(serializedGenres, listType);
            }

        } else {
            genres = new Gson().fromJson(serializedGenres, listType);
        }


        MyApplication.setConfiguration(configuration);
        MyApplication.setGenres(genres);

    }
}
