package idv.chauyan.transitionanimation.Transition;

import android.graphics.Canvas;
import android.view.View;

/**
 * Created by ChauyanWang on 2/5/17.
 */

public abstract class Transition {

    public abstract void animatedDraw(View view, Canvas canvas, float fraction);
    public abstract void startingAnimation(View view);
    public abstract void startedAnimation(View view);
    public abstract void endingAnimation(View view);
    public abstract void endedAnimation(View view);
    public abstract void setValue(View view, float offsetX, float offsetY);
}
