package net.dragora.papinomoviedb.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nietzsche on 25/06/15.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.beginTransaction();
        sqLiteDatabase.execSQL(MovieDatabaseContract.MovieEntry.SQL_CREATE_TABLE);
        Log.d("MYLOG", MovieDatabaseContract.MovieEntry.SQL_CREATE_TABLE);
        //sqLiteDatabase.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.execSQL(MovieDatabaseContract.MovieEntry.SQL_DROP_TABLE);
        sqLiteDatabase.endTransaction();
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



}
