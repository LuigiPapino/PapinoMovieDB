package net.dragora.papinomoviedb.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import net.dragora.papinomoviedb.common.api.ApiTaskCallbacks;
import net.dragora.papinomoviedb.common.api.BaseTask;
import net.dragora.papinomoviedb.data.MovieDatabaseContract;
import net.dragora.papinomoviedb.data.MovieDbHelper;
import net.dragora.papinomoviedb.model.ReviewMovie;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Get configuration from server and store it in mainPreference for future use
 * <p/>
 * Created by nietzsche on 25/06/15.
 */
public class GetMovieReviewsTask extends BaseTask<List<ReviewMovie>> {
    protected MovieDbHelper dbHelper;
    String movieId;

    /**
     * Get configuration from server and store it in mainPreference for future use
     *
     * @param callbacks
     */
    public GetMovieReviewsTask(ApiTaskCallbacks<List<ReviewMovie>> callbacks, String movieId, MovieDbHelper dbHelper) {
        super(callbacks);
        this.dbHelper = dbHelper;
        this.movieId = movieId;
    }


    @Override
    protected void init() {

    }

    @Override
    protected void cancelled() {

    }

    @Override
    protected void background() {
        // Check if movie credits are present in DB;
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM 'movie' WHERE id = ?;", new String[]{movieId});
        String serializedReviews = null;
        if (cursor != null && cursor.moveToFirst()) {
            int reviewsColumIndex = cursor.getColumnIndex(MovieDatabaseContract.MovieEntry.REVIEWS);
            serializedReviews = cursor.getString(reviewsColumIndex);
        }
        if (serializedReviews != null){

        }
        else {
            MovieWebService webService = WebServiceFactory.getInstance().getMovieWebService();
            JsonObject jsonObject = webService.movieReviews(movieId);
            if (jsonObject != null && jsonObject.has("results") ) {
                serializedReviews = jsonObject.getAsJsonArray("results").toString();
                // SAVE ON DB
                SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
                dbWrite.beginTransaction();
                ContentValues values = new ContentValues();
                values.put(MovieDatabaseContract.MovieEntry.REVIEWS,serializedReviews );
                dbWrite.update(MovieDatabaseContract.MovieEntry.TABLE_NAME,
                        values,
                        "id = ?",
                        new String[]{movieId});
                dbWrite.setTransactionSuccessful();
                dbWrite.endTransaction();
                dbWrite.close();
            }
        }

        Type listType = new TypeToken<ArrayList<ReviewMovie>>() {
        }.getType();

        try {
            JSONArray json = new JSONArray(serializedReviews);
                result = new Gson().fromJson(json.toString(), listType );

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (cursor != null) {
            cursor.close();
        }
        dbRead.close();
    }
}
