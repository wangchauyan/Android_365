package idv.chauyan.transitionanimation.transition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by ChauyanWang on 2/5/17.
 */

public class WaterWaveTransition extends Transition {

    private View baseView;
    private float baseViewOffsetX;
    private float baseViewOffsetY;

    private float gridWidth = 10;
    private float gridHeight = 20;

    private int[] rectangleCenter = new int[2];
    private Rect gridRectangle = new Rect();
    private RectF outterGridRectangle = new RectF();
    private Bitmap originBmp;

    public WaterWaveTransition() {

    }

    @Override
    public void setValue(View view, float offsetX, float offsetY) {
        baseView = view;
        baseViewOffsetX = offsetX;
        baseViewOffsetY = offsetY;
    }

    @Override
    public void animatedDraw(View view, Canvas canvas, float fraction) {
        drawOriginContent(view, canvas);
        drawRectangleContent(view, canvas, fraction);
    }

    @Override
    public void startingAnimation(View view) {
        if (baseView == null) baseView = view;
        baseView.buildDrawingCache();
    }

    @Override
    public void startedAnimation(View view) {
        if (baseView == null) baseView = view;

        baseView.getLocationOnScreen(rectangleCenter);
        if (baseViewOffsetX > 0 && baseViewOffsetY > 0) {
            rectangleCenter[0] += baseViewOffsetX;
            rectangleCenter[1] += baseViewOffsetY;
        }
        else {
            rectangleCenter[0] += baseView.getWidth()/2;
            rectangleCenter[1] += baseView.getWidth()/2;
        }
    }

    @Override
    public void endingAnimation(View view) {

    }

    @Override
    public void endedAnimation(View view) {
        baseView.destroyDrawingCache();
        if (originBmp != null)
            originBmp.recycle();
        originBmp = null;
    }

    private float delay(float x, float y, float cx, float cy, float w, float h) {
        x = (float) Math.pow(cx - x, 2) ;
        y = (float) Math.pow(cy - y, 2);
        w = (float) Math.pow(Math.max(cx, w - cx), 2);
        h = (float) Math.pow(Math.max(cy, h - cy), 2);
        return (float) Math.sqrt((x+y)/(w+h));
    }

    private void drawOriginContent(View view, Canvas canvas) {

        originBmp = view.getDrawingCache();
        if (originBmp != null) {
            canvas.drawBitmap(originBmp, 0, 0, null);
        }
    }

    private void drawRectangleContent(View view, Canvas canvas, float fraction) {
        final float cellFraction = .7f;
        final float widthFactor = view.getWidth() / gridWidth;
        final float heightFactor = view.getHeight() / gridHeight;

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                gridRectangle.left = (int) (x * widthFactor);
                gridRectangle.right = (int) (gridRectangle.left + widthFactor);
                gridRectangle.top = (int) (y * heightFactor);
                gridRectangle.bottom = (int) (gridRectangle.top + heightFactor);

                float delay = delay(
                        gridRectangle.centerX(),
                        gridRectangle.centerY(),
                        rectangleCenter[0],
                        rectangleCenter[1],
                        baseView.getWidth(),
                        baseView.getHeight());

                float localFraction = fraction / cellFraction - delay * (1 - cellFraction);
                localFraction = Math.max(0, localFraction);
                localFraction = Math.min(1, localFraction);

                if (localFraction < .5f) {
                    localFraction = 1f - localFraction * 2f;
                } else {
                    localFraction = (localFraction - .5f) * 2f;
                }

                outterGridRectangle.setEmpty();
                outterGridRectangle.right = gridRectangle.width() * localFraction;
                outterGridRectangle.bottom = gridRectangle.height() * localFraction;
                outterGridRectangle.offset(
                        gridRectangle.left + (gridRectangle.width() - outterGridRectangle.right) / 2,
                        gridRectangle.top + (gridRectangle.height() - outterGridRectangle.bottom) / 2
                );

                canvas.drawBitmap(originBmp, gridRectangle, outterGridRectangle, null);
            }
        }
    }
}
