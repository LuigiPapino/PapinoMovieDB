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
import net.dragora.papinomoviedb.model.Cast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Get configuration from server and store it in mainPreference for future use
 * <p/>
 * Created by nietzsche on 25/06/15.
 */
public class GetMovieCastsTask extends BaseTask<List<Cast>> {
    protected MovieDbHelper dbHelper;
    String movieId;

    /**
     * Get configuration from server and store it in mainPreference for future use
     *
     * @param callbacks
     */
    public GetMovieCastsTask(ApiTaskCallbacks<List<Cast>> callbacks, String movieId, MovieDbHelper dbHelper) {
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
        String serializedCredits = null;
        if (cursor != null && cursor.moveToFirst()) {
            int creditsColumIndex = cursor.getColumnIndex(MovieDatabaseContract.MovieEntry.CREDITS);
            serializedCredits = cursor.getString(creditsColumIndex);
        }
        if (serializedCredits != null){

        }
        else {
            MovieWebService webService = WebServiceFactory.getInstance().getMovieWebService();
            JsonObject jsonObject = webService.movieCredits(movieId);
            if (jsonObject != null && (jsonObject.has("cast") || jsonObject.has("crew"))) {
                serializedCredits = jsonObject.toString();
                // SAVE ON DB
                SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
                dbWrite.beginTransaction();
                ContentValues values = new ContentValues();
                values.put(MovieDatabaseContract.MovieEntry.CREDITS,serializedCredits );
                dbWrite.update(MovieDatabaseContract.MovieEntry.TABLE_NAME,
                        values,
                        "id = ?",
                        new String[]{movieId});
                dbWrite.setTransactionSuccessful();
                dbWrite.endTransaction();
                dbWrite.close();
            }
        }

        Type listType = new TypeToken<ArrayList<Cast>>() {
        }.getType();

        try {
            JSONObject json = new JSONObject(serializedCredits);
            if (json.has("cast")){
                result = new Gson().fromJson(json.getString("cast").toString(), listType );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (cursor != null) {
            cursor.close();
        }
        dbRead.close();
    }
}
