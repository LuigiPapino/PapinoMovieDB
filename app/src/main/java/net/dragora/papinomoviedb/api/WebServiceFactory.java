package net.dragora.papinomoviedb.api;

import net.dragora.papinomoviedb.common.Constants;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by nietzsche on 25/06/15.
 */
public class WebServiceFactory {


    private static WebServiceFactory instance;
    private RestAdapter restAdapter;
    private MovieWebService movieWebService;

    public WebServiceFactory() {

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_HOST)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", Constants.API_KEY);
                    }
                }).build();
        movieWebService = restAdapter.create(MovieWebService.class);
    }

    public static WebServiceFactory getInstance() {
        if (instance == null) {
            instance = new WebServiceFactory();
        }
        return instance;
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public void setRestAdapter(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public MovieWebService getMovieWebService() {
        return movieWebService;
    }

    public void setMovieWebService(MovieWebService movieWebService) {
        this.movieWebService = movieWebService;
    }


}
