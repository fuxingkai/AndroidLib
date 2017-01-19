package cn.infrastructure.widget.listview.swipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListAdapter;

import cn.infrastructure.widget.listview.OverScrollListView;

/**
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public class SwipeMenuListView extends OverScrollListView {

    private static final int TOUCH_STATE_NONE = 0;
    private static final int TOUCH_STATE_X = 1;
    private static final int TOUCH_STATE_Y = 2;

    private int MAX_Y = 5;
    private int MAX_X = 3;
    private float mDownX;
    private float mDownY;
    private int mTouchState;
    private int mTouchPosition;

    /* package */ SwipeMenuLayout mTouchView;

    /* package */ IOnSwipeListener mOnSwipeListener;
    /* package */ ISwipeMenuCreator mMenuCreator;
    /* package */ ISwipeMenuViewCreator mMenuViewCreator;
    /* package */ IOnSwipeItemClickListener mOnSwipeMenuItemClickListener;

    private Interpolator mCloseInterpolator;
    private Interpolator mOpenInterpolator;

    private int[] mDefaultState = new int[] { 0 };
    private int[] mMenuViewScreenLocation = new int[2];
    private boolean mDownOnSwipeMenuItem = false;
    private boolean mSetPressedFalseFlag;

    public SwipeMenuListView(Context context) {
        super(context);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        MAX_X = dp2px(MAX_X);
        MAX_Y = dp2px(MAX_Y);
        mTouchState = TOUCH_STATE_NONE;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof SwipeMenuAdapter) {
            super.setAdapter(adapter);
            return;
        }

        super.setAdapter(new SwipeMenuAdapter(getContext(), adapter) {

            @Override
            public void onMenuItemClick(int position, int index) {
                if (mOnSwipeMenuItemClickListener != null) {
                    mOnSwipeMenuItemClickListener.onMenuItemClick(position, index);
                }

                if (mTouchView != null) {
                    if (mOnSwipeListener != null) {
                        mOnSwipeListener.onSwipeStart(mTouchView);
                    }

                    mTouchView.smoothCloseMenu();

                    if (mOnSwipeListener != null) {
                        mOnSwipeListener.onSwipeEnd(mTouchView);
                    }
                }
            }

            @Override
            public void onContentItemClick(int position) {
                if (mOnSwipeMenuItemClickListener != null) {
                    mOnSwipeMenuItemClickListener.onContentItemClick(position);
                }
            }

            @Override
            protected SwipeMenu createSwipeMenu(int itemViewType, int position) {
                if (mMenuCreator != null) {
                    return mMenuCreator.createSwipeMenu(itemViewType, position);
                }

                return null;
            }

            @Override
            protected SwipeMenuView createSwipeMenuView(Context context, SwipeMenu menu, int position) {
                if (mMenuViewCreator != null) {
                    return mMenuViewCreator.createSwipeMenuView(context, menu, position);
                }

                return null;
            }
        });
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        mCloseInterpolator = interpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        mOpenInterpolator = interpolator;
    }

    public Interpolator getOpenInterpolator() {
        return mOpenInterpolator;
    }

    public Interpolator getCloseInterpolator() {
        return mCloseInterpolator;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if ((ev.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {

            clearPressedState();

            mDownOnSwipeMenuItem = false;
            int oldPos = mTouchPosition;
            int touchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
            View view = getChildAt(touchPosition - getFirstVisiblePosition());
            SwipeMenuLayout touchItemLayout = null;
            if (view != null && view instanceof SwipeMenuLayout) {
                touchItemLayout = (SwipeMenuLayout) view;
            }

            if (touchPosition == oldPos && touchItemLayout != null
                    && touchItemLayout.getContentView() != null && touchItemLayout.getContentView().getLeft() < 0) {

                if (touchItemLayout.getMenuView() != null) {
                    touchItemLayout.getMenuView().getLocationOnScreen(mMenuViewScreenLocation);
                    int rawX = (int) ev.getRawX();
                    int rawY = (int) ev.getRawY();
                    if (rawX >= mMenuViewScreenLocation[0] && rawY >= mMenuViewScreenLocation[1]
                            && rawX <= mMenuViewScreenLocation[0] + mTouchView.getMenuView().getMeasuredWidth()
                            && rawY <= mMenuViewScreenLocation[1] + mTouchView.getMenuView().getMeasuredHeight()) {
                        mDownOnSwipeMenuItem = true;
                    }
                }
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & MotionEvent.ACTION_MASK;

        if (action != MotionEvent.ACTION_DOWN && mTouchView == null) {
            return super.onTouchEvent(ev);
        }

        switch (action) {

            case MotionEvent.ACTION_DOWN: {
                mSetPressedFalseFlag = false;
                boolean hasOpenedItem = false;

                int oldPos = mTouchPosition;
                mDownX = ev.getX();
                mDownY = ev.getY();
                mTouchState = TOUCH_STATE_NONE;

                mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());

                if (mTouchPosition == oldPos && mTouchView != null && mTouchView.isOpen()) {
                    mTouchState = TOUCH_STATE_X;
                    mTouchView.onSwipe(ev);
                    return true;
                }

                int firstVisiblePos = getFirstVisiblePosition();
                int visibleViewCount = getLastVisiblePosition() - firstVisiblePos;
                //try to close the other opened swipe menu
                for (int i = 0; i < visibleViewCount; i++) {
                    View checkView = getChildAt(i);
                    if ((checkView != null) && (mTouchPosition != i) && (checkView instanceof SwipeMenuLayout)) {
                        SwipeMenuLayout needCloseLayout = (SwipeMenuLayout) checkView;
                        View contentView = needCloseLayout.getContentView();

                        if (contentView != null && contentView.getLeft() < 0) {
                            needCloseLayout.smoothCloseMenu();
                            hasOpenedItem = true;
                        }
                    }
                }

                if (hasOpenedItem) {
                    // cancel the current sequence touch event
                    return false;
                }

                View view = getChildAt(mTouchPosition - firstVisiblePos);

                //check whether it has swipe menu
                if (mTouchView != null && mTouchView.getMenuView() != null && mTouchView.isOpen()) {

                    if (mOnSwipeListener != null) {
                        mOnSwipeListener.onSwipeStart(mTouchView);
                    }

                    mTouchView.smoothCloseMenu();

                    if (mOnSwipeListener != null) {
                        mOnSwipeListener.onSwipeEnd(mTouchView);
                    }

                    mTouchView = null;
                    return super.onTouchEvent(ev);
                }

                if (view instanceof SwipeMenuLayout) {
                    mTouchView = (SwipeMenuLayout) view;
                }

                if (mTouchView != null) {
                    mTouchView.onSwipe(ev);
                }

                break;
            }

            case MotionEvent.ACTION_MOVE: {

                switch (mTouchState) {

                    case TOUCH_STATE_X: {
                        if (mTouchView != null) {
                            mTouchView.onSwipe(ev);
                        }

                        getSelector().setState(mDefaultState);
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        super.onTouchEvent(ev);
                        return true;
                    }

                    case TOUCH_STATE_NONE: {
                        float dy = Math.abs((ev.getY() - mDownY));
                        float dx = Math.abs((ev.getX() - mDownX));

                        if (Math.abs(dy) > MAX_Y) {
                            mTouchState = TOUCH_STATE_Y;
                            if (mDownOnSwipeMenuItem) {
                                //avoid vertical move
                                ev.setAction(MotionEvent.ACTION_CANCEL);
                                super.onTouchEvent(ev);
                                return true;
                            }
                        } else if (dx > MAX_X) {
                            mTouchState = TOUCH_STATE_X;
                            if (mTouchView != null && mOnSwipeListener != null) {
                                mOnSwipeListener.onSwipeStart(mTouchView);
                            }
                        }

                        break;
                    }

                    default: {
                        if (!mSetPressedFalseFlag) {
                            mSetPressedFalseFlag = true;
                            clearPressedState();
                        }

                        if (mDownOnSwipeMenuItem) {
                            //when it down at swipe menu item, set the vertical move disable
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            super.onTouchEvent(ev);
                            return true;
                        }
                    }
                }

                break;
            }

            case MotionEvent.ACTION_UP: {
                try {

                    if (mTouchState == TOUCH_STATE_X || mTouchState == TOUCH_STATE_NONE) {
                        if (mTouchView != null) {

                            mTouchView.onSwipe(ev);

                            if (mOnSwipeListener != null && mTouchState == TOUCH_STATE_X) {
                                mOnSwipeListener.onSwipeEnd(mTouchView);
                            }

                            if (!mTouchView.isOpen()) {
                                mTouchPosition = -1;
                                mTouchView = null;
                            }
                        }

                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        super.onTouchEvent(ev);
                        return true;
                    }

                    if (mTouchView != null) {
                        mTouchView.canceFrankressed();
                    }

                    mTouchPosition = -1;
                    mTouchView = null;

                } finally {
                    clearPressedState();
                }

                break;
            }

            default: {
                break;
            }
        }

        return super.onTouchEvent(ev);
    }

    private void clearPressedState() {
        int firstVisiblePos = getFirstVisiblePosition();
        int visibleViewCount = getLastVisiblePosition() - firstVisiblePos;
        //try to set pressed false
        for (int i = 0; i < visibleViewCount; i++) {
            View checkView = getChildAt(i);
            if (checkView instanceof SwipeMenuLayout) {
                SwipeMenuLayout setPressFalseLayout = (SwipeMenuLayout) checkView;
                setPressFalseLayout.canceFrankressed();
            }
        }
    }

    public void smoothOpenMenu(int position) {
        if (position >= getFirstVisiblePosition() && position <= getLastVisiblePosition()) {
            View view = getChildAt(position - getFirstVisiblePosition());
            if (view instanceof SwipeMenuLayout) {
                mTouchPosition = position;
                if (mTouchView != null && (mTouchView.isOpen() || (mTouchView.getContentView() != null && mTouchView.getContentView().getLeft() < 0))) {
                    mTouchView.smoothCloseMenu();
                }
                mTouchView = (SwipeMenuLayout) view;
                mTouchView.smoothOpenMenu();
            }
        }
    }

    public void smoothCloseMenu() {

        if (mTouchPosition >= getFirstVisiblePosition() && mTouchPosition <= getLastVisiblePosition()) {
            View view = getChildAt(mTouchPosition - getFirstVisiblePosition());
            if (view instanceof SwipeMenuLayout) {
                mTouchPosition = -1;
                if (mTouchView != null && mTouchView.isOpen()) {
                    mTouchView.smoothCloseMenu();
                }

                mTouchView = (SwipeMenuLayout) view;
                mTouchView.smoothCloseMenu();

                if (mOnSwipeListener != null) {
                    mOnSwipeListener.onSwipeEnd((SwipeMenuLayout) view);
                }
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    public void setMenuCreator(ISwipeMenuCreator menuCreator) {
        this.mMenuCreator = menuCreator;
    }

    public void setMenuViewCreator(ISwipeMenuViewCreator menuViewCreator) {
        this.mMenuViewCreator = menuViewCreator;
    }

    public void setOnMenuItemClickListener(IOnSwipeItemClickListener onSwipeMenuItemClickListener) {
        this.mOnSwipeMenuItemClickListener = onSwipeMenuItemClickListener;
    }

    public void setOnSwipeListener(IOnSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }
}
