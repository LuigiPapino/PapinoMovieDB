package net.dragora.papinomoviedb.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import net.dragora.papinomoviedb.common.api.ApiTaskCallbacks;
import net.dragora.papinomoviedb.common.api.BaseTask;
import net.dragora.papinomoviedb.data.MainPrefs_;
import net.dragora.papinomoviedb.data.MovieDatabaseContract;
import net.dragora.papinomoviedb.data.MovieDbHelper;
import net.dragora.papinomoviedb.model.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Get configuration from server and store it in mainPreference for future use
 * <p/>
 * Created by nietzsche on 25/06/15.
 */
public class GetDiscoverMovieTask extends BaseTask<List<Movie>> {
    protected MainPrefs_ mainPrefs;
    protected boolean forceUpdate = false, loadMore = false;
    protected MovieDbHelper dbHelper;

    /**
     * Get configuration from server and store it in mainPreference for future use
     *
     * @param callbacks
     * @param mainPrefs
     */
    public GetDiscoverMovieTask(ApiTaskCallbacks<List<Movie>> callbacks, MainPrefs_ mainPrefs, MovieDbHelper dbHelper) {
        super(callbacks);
        this.mainPrefs = mainPrefs;
        this.dbHelper = dbHelper;
    }

    /**
     * Get configuration from server and store it in mainPreference for future use
     *
     * @param callbacks
     * @param mainPrefs
     * @param forceUpdate force the request to server and overwrite the current saved value
     */
    public GetDiscoverMovieTask(ApiTaskCallbacks<List<Movie>> callbacks, MainPrefs_ mainPrefs, MovieDbHelper dbHelper, boolean forceUpdate, boolean loadMore) {
        super(callbacks);
        this.mainPrefs = mainPrefs;
        this.forceUpdate = forceUpdate;
        this.dbHelper = dbHelper;
        this.loadMore = loadMore;

    }

    @Override
    protected void init() {

    }

    @Override
    protected void cancelled() {

    }

    @Override
    protected void background() {
        // Check if movies are present in DB;
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM 'movie';", null);
        if (cursor != null && cursor.moveToFirst() && !loadMore && !forceUpdate) {
            result = new ArrayList<>();
            do {
                result.add(Movie.fromCursor(cursor));
            } while (cursor.moveToNext());

        } else {
            MovieWebService webService = WebServiceFactory.getInstance().getMovieWebService();
            JsonObject jsonObject = webService.discoverMovie(loadMore ? mainPrefs.nextDiscoverPageToLoad().get(): 1);
            if (jsonObject != null && jsonObject.has("results")) {
                Type listType = new TypeToken<ArrayList<Movie>>() {
                }.getType();
                result = new Gson().fromJson(jsonObject.getAsJsonArray("results"), listType);
                SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
                if (forceUpdate){
                    dbWrite.execSQL("DELETE FROM movie");
                    mainPrefs.nextDiscoverPageToLoad().put(0 );
                }
                dbWrite.beginTransaction();
                for (Movie movie : result) {
                    ContentValues values = new ContentValues();

                    dbWrite.insert(MovieDatabaseContract.MovieEntry.TABLE_NAME, null, movie.toContentValues());
                }
                dbWrite.setTransactionSuccessful();
                dbWrite.endTransaction();
                dbWrite.close();
                mainPrefs.nextDiscoverPageToLoad().put(mainPrefs.nextDiscoverPageToLoad().get() +1 );
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        dbRead.close();
        log(result != null ? Integer.toString(result.size()) : "null");
    }
}
