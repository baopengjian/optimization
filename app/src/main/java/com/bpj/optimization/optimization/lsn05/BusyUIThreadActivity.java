package com.bpj.optimization.optimization.lsn05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-1-14.
 */
public class BusyUIThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busy_uithread);

        Button theButtonThatLoadsAnImage = findViewById(R.id.busyui_button_load);
        theButtonThatLoadsAnImage.setText("Display an image.(图片RGB色值变换加载)");

        theButtonThatLoadsAnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sepiaAndDisplayImage();
            }
        });

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }


    /**
     * Loads a hardcoded (Don't judge me) bitmap into memory, applies
     * a sepia filter, and displays it.
     */
    public void sepiaAndDisplayImage() {
        // The following blobs of code *gleefully* pilfered from the Android Training class,
        // appropriately titled "Loading Large Bitmaps Efficiently".  Efficiently, eh?
        // Challenge accepted!
        Bitmap loadedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.punk_droid);

        // By performing the sepia filter manipulations in an AsyncTask, our UI can continue while
        // the CPU is processing the filter. Notice how the dancing pirate never misses a beat now?
        new SepiaFilterTask().execute(loadedBitmap);
    }

    private class SepiaFilterTask extends AsyncTask<Bitmap, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            Bitmap loadedBitmap = bitmaps[0];

            int width = loadedBitmap.getWidth();
            int height = loadedBitmap.getHeight();
            Bitmap sepiaBitmap = Bitmap.createBitmap(width, height, loadedBitmap.getConfig());
            // Go through every single pixel in the bitmap, apply a sepia filter to it, and apply it
            // to the new "output" bitmap.
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    // get pixel color
                    int pixel = loadedBitmap.getPixel(x, y);
                    int alpha = Color.alpha(pixel);

                    // These are the starting values for this pixel.
                    int inputRed = Color.red(pixel);
                    int inputGreen = Color.green(pixel);
                    int inputBlue = Color.blue(pixel);

                    // These are the sepia-fied values for each pixel.
                    // Note that if the resulting value is over 255, Math.Min brings it back down.
                    // So effectively, min establishes a max value!  Isn't that weird?
                    int outRed = (int) Math.min(  (inputRed * .393) + (inputGreen *.769) + (inputBlue * .189), 255);
                    int outGreen = (int) Math.min(
                            (inputRed * .349) + (inputGreen *.686) + (inputBlue * .168), 255);

                    int outBlue = (int) Math.min(
                            (inputRed * .272) + (inputGreen *.534) + (inputBlue * .131), 255);
                    // apply new pixel color to output bitmap
                    sepiaBitmap.setPixel(x, y, Color.argb(alpha, outRed, outGreen, outBlue));
                }
            }
            return sepiaBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap sepiaBitmap) {
            ImageView imageView = findViewById(R.id.busyui_imageview);
            imageView.setImageBitmap(sepiaBitmap);
        }
    }

}
