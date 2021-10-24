package com.bumptech.glide.samples.svg2bitmap;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.FutureTarget;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
  private RequestBuilder<Bitmap> requestBuilder;
  private final String mTag = MainActivity.class.getName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    /**
     * Construct RequestBuilder
     */
    requestBuilder = GlideApp.with(this).as(Bitmap.class).transform(new CenterInside());
//    loadSVGIntoImageView();
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        loadSVGtoBitmap();
      }
    });
    thread.start();
  }
  /**
   *Load SVG into imageView
   */
  void loadSVGIntoImageView(){
    ImageView imageView = findViewById(R.id.imageView);
    Uri uri = Uri.parse("http://www.clker.com/cliparts/u/Z/2/b/a/6/android-toy-h.svg");
    requestBuilder.load(uri).into(imageView);
  }
  /**
   * Get Bitmap from SVG
   */
  void loadSVGtoBitmap(){
    Uri uri = Uri.parse("http://www.clker.com/cliparts/u/Z/2/b/a/6/android-toy-h.svg");
    FutureTarget<Bitmap> target =  requestBuilder.load(uri).submit();
    try {
      Bitmap bitmap = target.get();
      Log.d(mTag,String.format("info: width = %d height = %d",bitmap.getWidth(),bitmap.getHeight()));
      /**
       * Display on ImageView
       */
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          displayBitmap(bitmap);
        }
      });
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Display a Bitmap in ImageView
   * @param bitmap Bitmap to display
   */
  void displayBitmap(Bitmap bitmap){
    ImageView imageView = findViewById(R.id.imageView);
    imageView.setImageBitmap(bitmap);
  }
}