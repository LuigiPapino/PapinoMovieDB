package net.dragora.papinomoviedb.api;

import com.google.gson.JsonObject;

import net.dragora.papinomoviedb.model.Configuration;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by nietzsche on 25/06/15.
 */
public interface MovieWebService {
    @GET("/configuration")
    Configuration configuration();

    @GET("/discover/movie?sort_by=popularity.desc")
    JsonObject discoverMovie(@Query("page") int page);

    @GET("/genre/movie/list")
    JsonObject genreList();

    @GET("/movie/{id}/credits")
    JsonObject movieCredits(@Path("id") String id);

    @GET("/movie/{id}/reviews")
    JsonObject movieReviews(@Path("id") String id);

    @GET("/movie/{id}/videos")
    JsonObject movieTrailers(@Path("id") String id);

}
