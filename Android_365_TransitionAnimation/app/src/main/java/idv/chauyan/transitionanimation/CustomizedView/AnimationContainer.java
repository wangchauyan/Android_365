package idv.chauyan.transitionanimation.customizedview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import idv.chauyan.transitionanimation.transition.Transition;

/**
 * Created by ChauyanWang on 2/5/17.
 */

public class AnimationContainer extends FrameLayout {

    private ValueAnimator animator;
    private Transition transition;

    private UpdateAnimationListener listener;

    // in case too many update, prevent from VM stackoverflow exception.
    private boolean forceUpdateContent = false;

    public AnimationContainer(Context context) {
        super(context);
        initSelf();
    }

    public AnimationContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSelf();
    }

    public AnimationContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSelf();
    }

    @Override
    public void dispatchDraw(Canvas canvas) {

        if (animator != null && animator.isRunning() && !forceUpdateContent) {
            forceUpdateContent = true;
            if (transition != null)
                transition.animatedDraw(this, canvas, animator.getAnimatedFraction());
            forceUpdateContent = false;
        }
        else {
            super.dispatchDraw(canvas);
        }
    }

    public void startAnimation(Transition t) {
        if (t == null) {
            throw new IllegalStateException("Transition is null");
        }
        stopAnimation();


        transition = t;
        transition.startingAnimation(this);
        getViewTreeObserver().addOnPreDrawListener(listener);

        invalidate();
    }

    public void stopAnimation() {
        if (transition == null) return;

        forceUpdateContent = false;
        animator.cancel();
        transition = null;
        getViewTreeObserver().removeOnPreDrawListener(listener);
        getViewTreeObserver().removeOnDrawListener(listener);
    }

    private void initSelf() {

        listener = new UpdateAnimationListener();

        animator = new ValueAnimator();
        animator.setDuration(1000);
        animator.setFloatValues(0f, 0.5f);
        animator.addListener(listener);
        animator.addUpdateListener(listener);
    }

    private class UpdateAnimationListener extends AnimatorListenerAdapter implements
                ViewTreeObserver.OnPreDrawListener,
                ViewTreeObserver.OnDrawListener,
                ValueAnimator.AnimatorUpdateListener {

        @Override
        public boolean onPreDraw() {
            getViewTreeObserver().removeOnPreDrawListener(listener);

            if (transition != null)
                transition.startedAnimation(AnimationContainer.this);
            animator.start();

            // Return true to proceed with the current drawing pass, or false to cancel.
            return true;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            invalidate();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (transition != null) {
                transition.endingAnimation(AnimationContainer.this);
            }

            getViewTreeObserver().addOnDrawListener(listener);
            invalidate();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            if (transition != null) {
                transition.endingAnimation(AnimationContainer.this);
                transition.endedAnimation(AnimationContainer.this);
            }

            stopAnimation();
            invalidate();
        }

        @Override
        public void onDraw() {
            // Callback method to be invoked when the view tree is about to be drawn.
            // At this point, views cannot be modified in any way.

            if (transition != null) {
                transition.endedAnimation(AnimationContainer.this);
            }

            AnimationContainer.this.post(new Runnable() {
                @Override
                public void run() {
                    stopAnimation();
                }
            });
        }
    }

}
