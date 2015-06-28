package net.dragora.papinomoviedb.common;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import net.dragora.papinomoviedb.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;



@EActivity(R.layout.activity_image_zoom)
@OptionsMenu(R.menu.menu_image_zoom)
public class ImageZoomActivity extends AppCompatActivity {

    @ViewById
    SubsamplingScaleImageView image;

    @Extra
    String imageUri = null;



    @OptionsItem(android.R.id.home)
    void menuHome() {
        onBackPressed();
    }



    @OptionsItem(R.id.action_image_zoom_save)
    void saveImage() {

        try {
            DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(
                    Uri.parse(imageUri));
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "image.jpg");
            //TODO
            dm.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    void setup() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageLoader.getInstance().loadImage(imageUri, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                image.setImage(ImageSource.bitmap(loadedImage));

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        image.setMaxScale(8);
    }

    public static void startWithTansition(View view, AppCompatActivity activity, String url){
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                Pair.create(view, activity.getString(R.string.transition_zoom_image))
        );

        ActivityCompat.startActivity(activity,
                ImageZoomActivity_.intent(activity).imageUri(url).get()
                , optionsCompat.toBundle());
    }
}
