package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;

public class ZoomableScrollView extends FrameLayout {

    private static final float MIN_ZOOM = 0.3f;
    private static final float MAX_ZOOM = 4.0f;
    private static final float DRAG_THRESHOLD = 12f;

    private float mScaleFactor = 1.0f;
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    private boolean mInitialized = false;

    private ScaleGestureDetector mScaleDetector;

    private float mLastTouchX;
    private float mLastTouchY;
    private float mInterceptStartX;
    private float mInterceptStartY;
    private int mActivePointerId = MotionEvent.INVALID_POINTER_ID;
    private boolean mIsDragging = false;
    private boolean mIntercepting = false;

    public ZoomableScrollView(Context context) {
        super(context);
        init(context);
    }

    public ZoomableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mInitialized && getChildCount() > 0) {
            View child = getChildAt(0);
            float childWidth = child.getWidth();
            float childHeight = child.getHeight();
            float parentWidth = getWidth();
            float parentHeight = getHeight();

            if (childWidth > 0 && childHeight > 0 && parentWidth > 0 && parentHeight > 0) {
                mInitialized = true;
                float fitScaleX = parentWidth / childWidth;
                float fitScaleY = parentHeight / childHeight;
                mScaleFactor = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, Math.min(fitScaleX, fitScaleY)));
                mTranslateX = (parentWidth - childWidth * mScaleFactor) / 2f;
                mTranslateY = (parentHeight - childHeight * mScaleFactor) / 2f;
                applyTransformation();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() > 1) {
            mIntercepting = true;
            return true;
        }

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptStartX = ev.getX();
                mInterceptStartY = ev.getY();
                mIntercepting = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(ev.getX() - mInterceptStartX);
                float dy = Math.abs(ev.getY() - mInterceptStartY);
                if (dx > DRAG_THRESHOLD || dy > DRAG_THRESHOLD) {
                    mIntercepting = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIntercepting = false;
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleDetector.onTouchEvent(ev);

        final int action = ev.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mLastTouchX = ev.getX();
                mLastTouchY = ev.getY();
                mActivePointerId = ev.getPointerId(0);
                mIsDragging = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mScaleDetector.isInProgress()) break;
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) break;

                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);
                float dx = x - mLastTouchX;
                float dy = y - mLastTouchY;

                if (!mIsDragging) {
                    float dist = (float) Math.sqrt(dx * dx + dy * dy);
                    if (dist > DRAG_THRESHOLD) mIsDragging = true;
                }

                if (mIsDragging) {
                    mTranslateX += dx;
                    mTranslateY += dy;
                    clampTranslation();
                    applyTransformation();
                }

                mLastTouchX = x;
                mLastTouchY = y;
                break;
            }
            case MotionEvent.ACTION_UP: {
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            }
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = ev.getActionIndex();
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return true;
    }

    private void applyTransformation() {
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            // Pivot must be (0,0) so scale origin matches the translation math
            child.setPivotX(0);
            child.setPivotY(0);
            child.setScaleX(mScaleFactor);
            child.setScaleY(mScaleFactor);
            child.setTranslationX(mTranslateX);
            child.setTranslationY(mTranslateY);
        }
    }

    private void clampTranslation() {
        if (getChildCount() == 0) return;
        View child = getChildAt(0);
        float scaledWidth = child.getWidth() * mScaleFactor;
        float scaledHeight = child.getHeight() * mScaleFactor;
        float parentWidth = getWidth();
        float parentHeight = getHeight();

        float maxX, minX, maxY, minY;

        if (scaledWidth <= parentWidth) {
            // Content smaller than screen — keep centered
            mTranslateX = (parentWidth - scaledWidth) / 2f;
        } else {
            maxX = 0f;
            minX = parentWidth - scaledWidth;
            mTranslateX = Math.max(minX, Math.min(maxX, mTranslateX));
        }

        if (scaledHeight <= parentHeight) {
            mTranslateY = (parentHeight - scaledHeight) / 2f;
        } else {
            maxY = 0f;
            minY = parentHeight - scaledHeight;
            mTranslateY = Math.max(minY, Math.min(maxY, mTranslateY));
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float previousScale = mScaleFactor;
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, mScaleFactor));

            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();
            float scaleChange = mScaleFactor / previousScale;

            mTranslateX = focusX - scaleChange * (focusX - mTranslateX);
            mTranslateY = focusY - scaleChange * (focusY - mTranslateY);

            clampTranslation();
            applyTransformation();
            return true;
        }
    }
}
