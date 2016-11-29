package com.volodia.estafetatest.utils;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;


public class ProgressLayout extends FrameLayout {

    private static final int APPEARING_DURATION = 300;
    private static final int DISAPPEARING_DURATION = 200;
    private static final Interpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(2.6f);
    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    private View progressBar;
    private View child;
    private boolean isInProgress;

    public ProgressLayout(Context context) {
        super(context);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        progressBar = new ProgressBar(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(0, 0);
        params.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(params);
        progressBar.setScaleX(0);
        progressBar.setScaleY(0);
        hide(progressBar);
        addView(progressBar);
    }

    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() == 2)
            throw new IllegalStateException("ProgressLayout can't have more than 1 child");
        this.child = child;
        super.addView(child, index, params);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (child == null)
            throw new IllegalStateException("ProgressLayout must have exactly 1 child");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        child.setPivotX(child.getMeasuredWidth() / 2);
        child.setPivotY(child.getMeasuredHeight() / 2);
        int min = Math.min(getMeasuredWidth(), getMeasuredHeight());
        progressBar.getLayoutParams().width = min;
        progressBar.getLayoutParams().height = min;
        int spec = MeasureSpec.makeMeasureSpec(min, MeasureSpec.AT_MOST);
        progressBar.measure(spec, spec);
        progressBar.setPivotX(min / 2);
        progressBar.setPivotY(min / 2);
    }

    public void setProgressVisibility(boolean visible) {
        isInProgress = visible;
        final View appearing = visible ? progressBar : child;
        final View disappearing = visible ? child : progressBar;
        appearing.animate().cancel();
        disappearing.animate().cancel();
        disappearing.setScaleX(1);
        disappearing.setScaleY(1);
        show(disappearing);
        disappearing.animate()
                .scaleX(0)
                .scaleY(0)
                .setDuration(DISAPPEARING_DURATION)
                .setInterpolator(ACCELERATE_INTERPOLATOR)
                .setListener(new SimpleAnimatorListener() {
                    @Override public void onAnimationEnd(Animator animation) {
                        hide(disappearing);
                        appearing.setScaleX(0);
                        appearing.setScaleY(0);
                        show(appearing);
                        appearing.animate()
                                .scaleX(1)
                                .scaleY(1)
                                .setDuration(APPEARING_DURATION)
                                .setInterpolator(OVERSHOOT_INTERPOLATOR)
                                .setListener(null);
                    }
                });
    }

    public boolean isInProgress() {
        return isInProgress;
    }



    public static <T extends View> T show(T view) {
        if(view.getVisibility() != VISIBLE) {
            view.setVisibility(VISIBLE);
        }
        return view;
    }

    public static <T extends View> T hide(T view) {
        if(view.getVisibility() != INVISIBLE) {
            view.setVisibility(INVISIBLE);
        }
        return view;
    }
}
