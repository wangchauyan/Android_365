package com.upshotech.blureffect;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private SeekBar radiusBar;
    private ImageView backgroundImage;


    private Bitmap originalBitmap = null;
    private Bitmap blurredBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final float scaleFactor = 1;
        backgroundImage = (ImageView) findViewById(R.id.backgroundImage);
        backgroundImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                backgroundImage.getViewTreeObserver().removeOnPreDrawListener(this);
                backgroundImage.buildDrawingCache();
                originalBitmap = Bitmap.createBitmap(backgroundImage.getDrawingCache());

                Canvas canvas = new Canvas(originalBitmap);
                canvas.translate(-backgroundImage.getLeft() / scaleFactor,
                        -backgroundImage.getTop() / scaleFactor);
                canvas.scale(1 / scaleFactor, 1 / scaleFactor);
                Paint paint = new Paint();
                paint.setFlags(Paint.FILTER_BITMAP_FLAG);
                canvas.drawBitmap(backgroundImage.getDrawingCache(), 0, 0, paint);

                return true;
            }
        });

        radiusBar = (SeekBar) findViewById(R.id.radiusBar);
        radiusBar.setMax(25);
        radiusBar.setProgress(0);
        radiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int i, boolean b) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (blurredBitmap != null) blurredBitmap.recycle();
                        blurredBitmap = null;
                        blurredBitmap =
                                BlurEffect.genBlurImage(MainActivity.this, originalBitmap, (i == 0?1:i));
                        backgroundImage.post(new Runnable() {
                            @Override
                            public void run() {
                                backgroundImage.setImageBitmap(blurredBitmap);
                            }
                        });
                    }
                }).start();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
