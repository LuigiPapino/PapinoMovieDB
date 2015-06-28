package net.dragora.papinomoviedb.common.api;

/**
 * Created by nietzsche on 20/05/15.
 */
public abstract class ApiTaskCallbacks<T>  {

    public abstract void onInit();
    public abstract void onCancelled();
    public abstract void onResponse(T result);
    public abstract void onRequestError(String message);
}
