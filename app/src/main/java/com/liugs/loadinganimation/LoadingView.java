package com.liugs.loadinganimation;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class LoadingView extends View {
    private static final String TAG = "LoadingView";
    private static final int CIRCLE_OUT_RADIUS = 50;
    private static final int CIRCLE_OUT_MIN_RADIUS = CIRCLE_OUT_RADIUS * 2;
    private static final int RADIUS_COEFFICIENT = 6;
    private static final int CIRCLE_OUT_DEGREES_INTERVAL = 60;
    private static final int CIRCLE_OUT_COUNT = 6;
    private static final long DURATION = 3000;
    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN};
    private int pointX;
    private int pointY;
    private int radius;
    private int currentRadius;
    private Paint paint;
    private OutCircle outCircle;
    private int currentAngle;
    private AnimatorSet animatorSet;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int topWidth = getWidth();
        int bottomWidth = getWidth();
        radius = topWidth / RADIUS_COEFFICIENT;
        currentRadius = radius;
        pointX = topWidth >> 1;
        pointY = bottomWidth >> 1;
        print(pointX + " " + pointY);
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (outCircle == null) {
            outCircle = new OutCircle();
            outCircle.onDraw(canvas);
            final ValueAnimator rotation = ValueAnimator.ofInt(360, 0);
            rotation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentAngle = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            rotation.setRepeatCount(ValueAnimator.INFINITE);
            rotation.setRepeatMode(ValueAnimator.RESTART);
            final ValueAnimator scale = ValueAnimator.ofInt(radius, CIRCLE_OUT_MIN_RADIUS);
            scale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentRadius = (int) animation.getAnimatedValue();
                }
            });
            scale.setRepeatCount(ValueAnimator.INFINITE);
            scale.setRepeatMode(ValueAnimator.REVERSE);
            animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new LinearInterpolator());
            animatorSet.setDuration(DURATION);
            animatorSet.playTogether(rotation, scale);
        } else {
            outCircle.onDraw(canvas);
        }
    }

    private void drawOutCircle(int argle, Canvas canvas) {
        double currentRadians = argle * Math.PI / 180;
        float currentX = (float) (pointX + Math.sin(currentRadians) * currentRadius);
        float currentY = (float) (pointY + Math.cos(currentRadians) * currentRadius);
        canvas.drawCircle(currentX, currentY, CIRCLE_OUT_RADIUS, paint);
    }

    private void print(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnimatorSet();
        super.onDetachedFromWindow();
    }

    public void stopAnimatorSet() {
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet = null;
        }
    }

    public void switchAnimatorSet() {
        if (animatorSet != null) {
            if (!animatorSet.isStarted()) {
                animatorSet.start();
            } else if (animatorSet.isPaused()) {
                animatorSet.resume();
            } else {
                animatorSet.pause();
            }
        }
    }

    /**
     * 画圆圈
     */
    private class OutCircle {

        OutCircle() {
        }

        private void onDraw(Canvas canvas) {
            for (int i = 0; i < CIRCLE_OUT_COUNT; i++) {
                paint.setColor(colors[i % 3]);
                drawOutCircle(currentAngle + i * CIRCLE_OUT_DEGREES_INTERVAL, canvas);
            }
        }
    }
}
