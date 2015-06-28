package net.dragora.papinomoviedb.model.event_bus;

import net.dragora.papinomoviedb.model.Movie;

import java.util.List;

/**
 * Created by nietzsche on 27/06/15.
 */
public class GetDiscoverCompleted {

    public List<Movie> getMovies() {
        return movies;
    }

    List<Movie> movies = null;

    public GetDiscoverCompleted(List<Movie> movies) {
        this.movies = movies;
    }
}
