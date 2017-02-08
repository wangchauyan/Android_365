package idv.chauyan.transitionanimation.transition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.view.View;

import idv.chauyan.transitionanimation.customizedview.AnimationContainer;

/**
 * Created by ChauyanWang on 2/5/17.
 */

public class CircleTransition extends Transition {

    private View baseView;
    private float baseViewOffsetX;
    private float baseViewOffsetY;

    private int[] circleCenter = new int[2];
    private Path drawPath = new Path();

    private Bitmap originBmp;

    public CircleTransition() {
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
        drawAnimationCircle(view, canvas, fraction);
    }

    @Override
    public void startingAnimation(View view) {
        if (baseView == null) baseView = view;
        baseView.buildDrawingCache();
    }

    @Override
    public void startedAnimation(View view) {
        if (baseView == null) baseView = view;

        baseView.getLocationOnScreen(circleCenter);
        if (baseViewOffsetX > 0 && baseViewOffsetY > 0) {
            circleCenter[0] += baseViewOffsetX;
            circleCenter[1] += baseViewOffsetY;
        }
        else {
            circleCenter[0] += baseView.getWidth()/2;
            circleCenter[1] += baseView.getWidth()/2;
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


    private void drawOriginContent(View view, Canvas canvas) {

        originBmp = view.getDrawingCache();
        if (originBmp != null) {
            canvas.drawBitmap(originBmp, 0, 0, null);
        }
    }

    private void drawAnimationCircle(View view, Canvas canvas, float fraction) {
        float x = circleCenter[0] + (view.getWidth() / 2 - circleCenter[0]) * fraction;
        float y = circleCenter[1] + (view.getHeight() / 2 - circleCenter[1]) * fraction;
        float w = view.getWidth();
        float h = view.getHeight();
        float radius = (float) Math.sqrt(w * w + h * h) / 2;
        drawPath.addCircle(x, y, radius * fraction, Path.Direction.CCW);

        canvas.save();
        canvas.clipPath(drawPath);
        ((AnimationContainer)view).dispatchDraw(canvas);
        canvas.restore();
        drawPath.reset();
    }
}
