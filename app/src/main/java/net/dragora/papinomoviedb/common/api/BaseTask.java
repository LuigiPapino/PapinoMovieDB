package net.dragora.papinomoviedb.common.api;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Common base task to make http request to the server
 * Created by nietzsche on 20/05/15.
 */
public abstract class BaseTask<T> extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "ApiBaseTask";
    protected ApiTaskCallbacks<T> callbacks;
    protected T result;

    public BaseTask(ApiTaskCallbacks<T> callbacks) {

        super();
        this.callbacks = callbacks;
    }

    @Override
    protected void onCancelled() {
        cancelled();
        callbacks.onCancelled();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (result == null || bacgroundException !=null) {
            callbacks.onRequestError(bacgroundException == null ? "Null response" : bacgroundException.getMessage());
        } else {
            callbacks.onResponse(result);
        }
    }

    @Override
    protected void onPreExecute() {
        callbacks.onInit();
        init();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            background();
        } catch (Exception e) {
            e.printStackTrace();
            bacgroundException = e;
        }
        return null;
    }

    private Exception bacgroundException = null;

    protected void log(String message){
        Log.d(TAG, message);
    }
    protected abstract void init();

    protected abstract void cancelled();

    protected abstract void background() ;
}
