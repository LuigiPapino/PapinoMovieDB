package net.dragora.papinomoviedb.common.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by nietzsche on 18/05/15.
 */
public class MyFragment<T extends AppCompatActivity> extends Fragment {

    public static final String TAG ="MYLOG";

    public static void log (String s){
        Log.d(TAG, s);
    }

    public T getMyActivity(){
        if (getActivity() != null){
            return  (T)getActivity();
        }
        return null;
    }
}
