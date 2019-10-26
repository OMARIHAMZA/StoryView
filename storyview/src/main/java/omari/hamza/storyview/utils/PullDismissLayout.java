package omari.hamza.storyview.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import omari.hamza.storyview.callback.TouchCallbacks;

public class PullDismissLayout extends FrameLayout {
    private PullDismissLayout.Listener listener;
    private ViewDragHelper dragHelper;
    private float minFlingVelocity;
    private float verticalTouchSlop;
    private TouchCallbacks mTouchCallbacks;
    private boolean animateAlpha;

    public PullDismissLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PullDismissLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullDismissLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @TargetApi(21)
    public PullDismissLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle, int defResStyle) {
        super(context, attrs, defStyle, defResStyle);
        init(context);
    }

    private void init(@NonNull Context context) {
        if (!isInEditMode()) {
            ViewConfiguration vc = ViewConfiguration.get(context);
            minFlingVelocity = (float) vc.getScaledMinimumFlingVelocity();
            dragHelper = ViewDragHelper.create(this, new PullDismissLayout.ViewDragCallback(this));
        }
    }

    public void computeScroll() {
        super.computeScroll();
        if (dragHelper != null && dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        boolean pullingDown = false;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                verticalTouchSlop = event.getY();
            case MotionEvent.ACTION_MOVE:
                final float dy = event.getY() - verticalTouchSlop;
                if (dy > dragHelper.getTouchSlop()) {
                    pullingDown = true;
                    mTouchCallbacks.touchPull();
                }else{
                    mTouchCallbacks.touchDown(event.getX());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                verticalTouchSlop = 0.0f;
                mTouchCallbacks.touchUp();
                break;
        }

        if (!dragHelper.shouldInterceptTouchEvent(event) && pullingDown) {
            if (dragHelper.getViewDragState() == ViewDragHelper.STATE_IDLE &&
                    dragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_VERTICAL)) {

                View child = getChildAt(0);
                if (child != null && !listener.onShouldInterceptTouchEvent()) {
                    dragHelper.captureChildView(child, event.getPointerId(0));
                    return dragHelper.getViewDragState() == ViewDragHelper.STATE_DRAGGING;
                }
            }
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return dragHelper.getCapturedView() != null;
    }

    public void setMinFlingVelocity(float velocity) {
        minFlingVelocity = velocity;
    }

    public void setAnimateAlpha(boolean b) {
        animateAlpha = b;
    }

    public void setListener(Listener l) {
        listener = l;
    }

    private static class ViewDragCallback extends ViewDragHelper.Callback {
        private PullDismissLayout pullDismissLayout;
        private int startTop;
        private float dragPercent;
        private View capturedView;
        private boolean dismissed;

        private ViewDragCallback(PullDismissLayout layout) {
            pullDismissLayout = layout;
            dragPercent = 0.0F;
            dismissed = false;
        }

        public boolean tryCaptureView(View view, int i) {
            return capturedView == null;
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            return top < 0 ? 0 : top;
        }

        public void onViewCaptured(View view, int activePointerId) {
            capturedView = view;
            startTop = view.getTop();
            dragPercent = 0.0F;
            dismissed = false;
        }

        @SuppressLint({"NewApi"})
        public void onViewPositionChanged(View view, int left, int top, int dx, int dy) {
            int range = pullDismissLayout.getHeight();
            int moved = Math.abs(top - startTop);
            if (range > 0) {
                dragPercent = (float) moved / (float) range;
            }
            if (pullDismissLayout.animateAlpha) {
                view.setAlpha(1.0F - dragPercent);
                pullDismissLayout.invalidate();
            }
        }

        public void onViewDragStateChanged(int state) {
            if (capturedView != null && dismissed && state == ViewDragHelper.STATE_IDLE) {
                pullDismissLayout.removeView(capturedView);
                if (pullDismissLayout.listener != null) {
                    pullDismissLayout.listener.onDismissed();
                }
            }
        }

        public void onViewReleased(View view, float xv, float yv) {
            dismissed = dragPercent >= 0.50F ||
                    (Math.abs(xv) > pullDismissLayout.minFlingVelocity && dragPercent > 0.20F);
            int finalTop = dismissed ? pullDismissLayout.getHeight() : startTop;
            if (!dismissed){
                pullDismissLayout.getmTouchCallbacks().touchUp();
            }
            pullDismissLayout.dragHelper.settleCapturedViewAt(0, finalTop);
            pullDismissLayout.invalidate();
        }
    }

    public void setmTouchCallbacks(TouchCallbacks mTouchCallbacks) {
        this.mTouchCallbacks = mTouchCallbacks;
    }

    public interface Listener {
        /**
         * Layout is pulled down to dismiss
         * Good time to finish activity, remove fragment or any view
         */
        void onDismissed();

        /**
         * Convenient method to avoid layout_color overriding event
         * If you have a RecyclerView or ScrollerView in our layout_color your can
         * avoid PullDismissLayout to handle event.
         *
         * @return true when ignore pull down event, f
         * false for allow PullDismissLayout handle event
         */
        boolean onShouldInterceptTouchEvent();
    }

    public TouchCallbacks getmTouchCallbacks() {
        return mTouchCallbacks;
    }
}