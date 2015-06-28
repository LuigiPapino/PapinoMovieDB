package net.dragora.papinomoviedb.data;

import android.provider.BaseColumns;

import net.dragora.papinomoviedb.common.data.SQLCreateTableBuilder;
import net.dragora.papinomoviedb.common.data.SQLDropTableBuilder;

/**
 * Created by nietzsche on 25/06/15.
 */
public class MovieDatabaseContract {
    public MovieDatabaseContract() {
    }

    public static abstract class MovieEntry implements BaseColumns{
     public static final String TABLE_NAME = "movie";
        public static final String MOVIE_ID = "id";
        public static final String TITLE = "title";
        public static final String ADULT = "adult";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String GENRE_IDS = "genre_ids";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String POSTER_PATH = "poster_path";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";
        public static final String VIDEO = "video";
        public static final String CREDITS = "credits";
        public static final String REVIEWS = "reviews";
        public static final String TRAILERS = "trailers";

        public static final String SQL_CREATE_TABLE = SQLCreateTableBuilder.init(TABLE_NAME, MovieEntry._ID)
                .appendTextColumn(MOVIE_ID)
                .appendTextColumn(TITLE)
                .appendTextColumn(ADULT)
                .appendTextColumn(BACKDROP_PATH)
                .appendTextColumn(GENRE_IDS)
                .appendTextColumn(ORIGINAL_LANGUAGE)
                .appendTextColumn(ORIGINAL_TITLE)
                .appendTextColumn(OVERVIEW)
                .appendTextColumn(RELEASE_DATE)
                .appendTextColumn(POSTER_PATH)
                .appendTextColumn(POPULARITY)
                .appendTextColumn(VOTE_AVERAGE)
                .appendTextColumn(VOTE_COUNT)
                .appendTextColumn(VIDEO)
                .appendTextColumn(CREDITS)
                .appendTextColumn(REVIEWS)
                .appendTextColumn(TRAILERS)

                .build();
        public static final String SQL_DROP_TABLE = SQLDropTableBuilder.dropIfExist(TABLE_NAME);
    }
}

