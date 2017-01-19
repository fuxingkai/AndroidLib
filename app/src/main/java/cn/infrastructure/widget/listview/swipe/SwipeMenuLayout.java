package cn.infrastructure.widget.listview.swipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * the wrapper of the list view item
 * 
 * Created by Frank on 2016/7/13.
 * 
 */
public class SwipeMenuLayout extends FrameLayout {

	private static final int CONTENT_VIEW_ID = 1;
	private static final int MENU_VIEW_ID = 2;

	private static final int STATE_CLOSE = 0;
	private static final int STATE_OPEN = 1;

	/* package */static final long SET_PRESSED_DELAY = 200L;

	/* package */final int MIN_FLING = dp2px(15);
	/* package */final int MAX_VELOCITYX = -dp2px(200);

	/* package */View mContentView;
	/* package */View mPressedView;
	/* package */SwipeMenuView mMenuView;

	/* package */int mState = STATE_CLOSE;

	private int mDownX;
	private GestureDetectorCompat mGestureDetector;
	private OnGestureListener mGestureListener;
	private ScrollerCompat mOpenScroller;
	private ScrollerCompat mCloseScroller;
	private int mBaseX;

	/* package */boolean mIsFling;
	/* package */int mPosition;

	private Interpolator mCloseInterpolator;
	private Interpolator mOpenInterpolator;

	private int mAnimDuration = 350;

	/* package */boolean mPressedRunnableRemoved;
	private Runnable mSetPressedRunnable = new Runnable() {

		@Override
		public void run() {
			if (mContentView != null) {
				mContentView.setPressed(true);
				mPressedView = mContentView;
			}
		}
	};

	/* package */IOnSwipeItemClickListener mOnSwipeItemClickListener;
	/* package */int[] mViewLocationOnScreen = new int[2];
	/* package */boolean mCancelTapConfirm;

	/* package */int mScaledTouchSlop;
	/* package */long mActionDownTime;
	/* package */long mActionUpTime;

	public SwipeMenuLayout(View contentView, SwipeMenuView menuView,
			IOnSwipeItemClickListener onSwipeItemClickListener) {
		this(contentView, menuView, null, null, onSwipeItemClickListener);
	}

	@SuppressLint("NewApi")
	public SwipeMenuLayout(View contentView, SwipeMenuView menuView,
			Interpolator closeInterpolator, Interpolator openInterpolator,
			IOnSwipeItemClickListener onSwipeItemClickListener) {

		super(contentView.getContext());
		mCloseInterpolator = closeInterpolator;
		mOpenInterpolator = openInterpolator;
		mOnSwipeItemClickListener = onSwipeItemClickListener;

		mContentView = contentView;

		mMenuView = menuView;

		ViewConfiguration viewConfig = ViewConfiguration.get(getContext());
		mScaledTouchSlop = viewConfig.getScaledTouchSlop();

		init();
	}

	private SwipeMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private SwipeMenuLayout(Context context) {
		super(context);
	}

	public void setMenuView(SwipeMenuView menuView) {
		this.mMenuView = menuView;
		init();
	}

	public int getPosition() {
		return mPosition;
	}

	public void setPosition(int position) {
		this.mPosition = position;
		if (mMenuView != null) {
			mMenuView.setPosition(position);
		}
	}

	private void init() {
		setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		mGestureListener = new SimpleOnGestureListener() {

			private int mLastDownX;
			private int mLastDownY;

			@Override
			public boolean onDown(MotionEvent e) {
				mIsFling = false;
				mLastDownX = (int) e.getRawX();
				mLastDownY = (int) e.getRawY();
				return false;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				if (null == e1 || null == e2) {
					return false;
				}
				if (Math.abs(e1.getX() - e2.getX()) > MIN_FLING
						&& velocityX < MAX_VELOCITYX) {
					mIsFling = true;
				}
				return false;
			}
			
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				if (mCancelTapConfirm) {
					return false;
				}

				int rawX = (int) e.getRawX();
				int rawY = (int) e.getRawY();

				int distanceX = rawX - mLastDownX;
				int distanceY = rawY - mLastDownY;

				if (Math.abs(distanceX) > mScaledTouchSlop
						|| Math.abs(distanceY) > mScaledTouchSlop) {
					// user moved some distance, ignore it
					return true;
				}

				if (mState == STATE_OPEN) {
					// check if click on swipe menu
					if (mMenuView != null && mMenuView.getChildCount() > 0) {
						if (isCoordinateInView(mMenuView, rawX, rawY)) {
							final int childCount = mMenuView.getChildCount();
							for (int i = 0; i < childCount; i++) {
								View view = mMenuView.getChildAt(i);
								if (isCoordinateInView(view, rawX, rawY)) {
									if (mOnSwipeItemClickListener != null) {
										mOnSwipeItemClickListener
												.onMenuItemClick(mPosition,
														view.getId());
									}
									break;
								}
							}
						}
					}

					smoothCloseMenu();
					return true;
				}

				doPerformContentClick();

				return true;
			}

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				if (mCancelTapConfirm) {
					return false;
				}

				int rawX = (int) e.getRawX();
				int rawY = (int) e.getRawY();

				int distanceX = rawX - mLastDownX;
				int distanceY = rawY - mLastDownY;

				if (Math.abs(distanceX) > mScaledTouchSlop
						|| Math.abs(distanceY) > mScaledTouchSlop) {
					// user moved some distance, ignore it
					return true;
				}

				if (mState == STATE_OPEN) {
					// check if click on swipe menu
					if (mMenuView != null && mMenuView.getChildCount() > 0) {
						if (isCoordinateInView(mMenuView, rawX, rawY)) {
							final int childCount = mMenuView.getChildCount();
							for (int i = 0; i < childCount; i++) {
								View view = mMenuView.getChildAt(i);
								if (isCoordinateInView(view, rawX, rawY)) {
									if (mOnSwipeItemClickListener != null) {
										mOnSwipeItemClickListener
												.onMenuItemClick(mPosition,
														view.getId());
									}
									break;
								}
							}
						}
					}

					smoothCloseMenu();
					return true;
				}

				doPerformContentClick();

				return true;
			}
		};

		mGestureDetector = new GestureDetectorCompat(getContext(),
				mGestureListener);

		if (mCloseInterpolator != null) {
			mCloseScroller = ScrollerCompat.create(getContext(),
					mCloseInterpolator);
		} else {
			mCloseScroller = ScrollerCompat.create(getContext());
		}
		if (mOpenInterpolator != null) {
			mOpenScroller = ScrollerCompat.create(getContext(),
					mOpenInterpolator);
		} else {
			mOpenScroller = ScrollerCompat.create(getContext());
		}

		LayoutParams contentParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mContentView.setLayoutParams(contentParams);
		if (mContentView.getId() < 1) {
			mContentView.setId(CONTENT_VIEW_ID);
		}
		removeAllViews();
		addView(mContentView);

		if (mMenuView != null) {
			mMenuView.setId(MENU_VIEW_ID);
			mMenuView.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			addView(mMenuView);
		}

		if (mContentView.getBackground() == null) {
			mContentView.setBackgroundColor(Color.WHITE);
		}

		// in android 2.x, MenuView height is MATCH_PARENT is not work.
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {
							setMenuHeight(mContentView.getHeight());
							getViewTreeObserver().removeGlobalOnLayoutListener(
									this);
						}
					});
		}

	}

	/* package */boolean isCoordinateInView(View view, int screenX, int screenY) {
		if (view == null || view.getVisibility() != View.VISIBLE
				|| view.getMeasuredWidth() == 0
				|| view.getMeasuredHeight() == 0) {
			return false;
		}

		view.getLocationOnScreen(mViewLocationOnScreen);
		if (screenX >= mViewLocationOnScreen[0]
				&& screenY >= mViewLocationOnScreen[1]
				&& screenX <= mViewLocationOnScreen[0]
						+ view.getMeasuredWidth()
				&& screenY <= mViewLocationOnScreen[1]
						+ view.getMeasuredHeight()) {
			return true;
		}

		return false;
	}

	/* package */void doPerformContentClick() {
		if (mActionUpTime - mActionDownTime < SET_PRESSED_DELAY) {
			// it means the pressed state is not triggered
			mSetPressedRunnable.run();
			postDelayed(new Runnable() {

				@Override
				public void run() {
					cancelPressed();
					if (mOnSwipeItemClickListener != null) {
						mOnSwipeItemClickListener.onContentItemClick(mPosition);
					}
				}
			}, 50L);

		} else {
			if (mOnSwipeItemClickListener != null) {
				mOnSwipeItemClickListener.onContentItemClick(mPosition);
			}
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if ((ev.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
			mDownX = (int) ev.getX();
		}
		return super.onInterceptTouchEvent(ev);
	}

	public boolean onSwipe(MotionEvent event) {
		if (null == mGestureDetector || null == event) {
			return false;
		}

		mGestureDetector.onTouchEvent(event);

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN: {

			mActionDownTime = System.currentTimeMillis();
			// mDownX = (int) event.getX();

			int rawX = (int) event.getRawX();
			int rawY = (int) event.getRawY();

			mIsFling = false;
			if (mState == STATE_CLOSE
					&& isCoordinateInView(mContentView, rawX, rawY)) {
				mPressedRunnableRemoved = false;
				postDelayed(mSetPressedRunnable, SET_PRESSED_DELAY);
			}

			break;
		}

		case MotionEvent.ACTION_MOVE: {
			int dis = (int) (mDownX - event.getX());
			if (mState == STATE_OPEN && mMenuView != null) {
				dis += mMenuView.getWidth();
			}

			cancelPressed();

			swipe(dis);

			break;
		}

		case MotionEvent.ACTION_UP: {
			mActionUpTime = System.currentTimeMillis();

			mCancelTapConfirm = false;
			cancelPressed();

			if (mMenuView != null) {
				if (mIsFling
						|| (mDownX - event.getX()) > (mMenuView
								.getMeasuredWidth() / 2)) {
					// open
					smoothOpenMenu();
				} else if (mState == STATE_OPEN
						&& (!isCoordinateInView(mMenuView,
								(int) event.getRawX(), (int) event.getRawY()) || Math
								.abs((int) event.getRawX() - mDownX) > mScaledTouchSlop)) {
					mCancelTapConfirm = true;
					smoothCloseMenu();
				} else if (mState == STATE_CLOSE && mContentView != null) {

					int absContentViewLeft = Math.abs(mContentView.getLeft());

					if (absContentViewLeft > (mMenuView.getMeasuredWidth() / 2)) {
						smoothOpenMenu();
					} else if (absContentViewLeft > 0) {
						mCancelTapConfirm = true;
						smoothCloseMenu();
					}
				}
			}

			break;
		}

		}

		return true;
	}

	public boolean isOpen() {
		return mState == STATE_OPEN;
	}

	private void swipe(int dis) {
		if (mMenuView == null) {
			dis = 0;
		} else if (dis > mMenuView.getWidth()) {
			dis = mMenuView.getWidth();
		}

		if (dis < 0) {
			dis = 0;
		}

		mContentView.layout(-dis, mContentView.getTop(),
				mContentView.getWidth() - dis, getMeasuredHeight());
		if (mMenuView != null) {
			mMenuView.layout(mContentView.getWidth() - dis, mMenuView.getTop(),
					mContentView.getWidth() + mMenuView.getWidth() - dis,
					mMenuView.getBottom());
		}
	}

	@Override
	public void computeScroll() {
		if (mState == STATE_OPEN) {
			if (mOpenScroller.computeScrollOffset()) {
				swipe(mOpenScroller.getCurrX());
				postInvalidate();
			}
		} else {
			if (mCloseScroller.computeScrollOffset()) {
				swipe(mBaseX - mCloseScroller.getCurrX());
				postInvalidate();
			}
		}
	}

	/* package */void cancelPressed() {

		if (!mPressedRunnableRemoved) {
			mPressedRunnableRemoved = true;
			removeCallbacks(mSetPressedRunnable);
		}

		if (mPressedView != null) {
			mPressedView.setPressed(false);
			mPressedView = null;
		}
	}

	public void smoothCloseMenu() {
		mState = STATE_CLOSE;

		if (mMenuView == null) {
			return;
		}

		if (mCloseScroller.computeScrollOffset()) {
			mCloseScroller.abortAnimation();
		}

		mBaseX = -mContentView.getLeft();
		mCloseScroller.startScroll(0, 0, mBaseX, 0, mAnimDuration);
		postInvalidate();
	}

	public void smoothOpenMenu() {
		mState = STATE_OPEN;

		if (mMenuView == null) {
			return;
		}

		if (mCloseScroller.computeScrollOffset()) {
			mCloseScroller.abortAnimation();
		}

		mOpenScroller.startScroll(-mContentView.getLeft(), 0,
				mMenuView.getWidth(), 0, mAnimDuration);
		postInvalidate();
	}

	public void closeMenu() {
		if (mCloseScroller.computeScrollOffset()) {
			mCloseScroller.abortAnimation();
		}

		if (mState == STATE_OPEN) {
			mState = STATE_CLOSE;
			swipe(0);
		}

		mState = STATE_CLOSE;
	}

	public void openMenu() {
		if (mState == STATE_CLOSE) {
			mState = STATE_OPEN;
			swipe(mMenuView == null ? 0 : mMenuView.getWidth());
		}
	}

	public View getContentView() {
		return mContentView;
	}

	public SwipeMenuView getMenuView() {
		return mMenuView;
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getContext().getResources().getDisplayMetrics());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mMenuView != null) {
			mMenuView.measure(MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(
					getMeasuredHeight(), MeasureSpec.EXACTLY));
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mContentView.layout(0, 0, getMeasuredWidth(),
				mContentView.getMeasuredHeight());
		if (mMenuView != null) {
			mMenuView.layout(getMeasuredWidth(), 0, getMeasuredWidth()
					+ mMenuView.getMeasuredWidth(),
					mContentView.getMeasuredHeight());
		}
	}

	public void setMenuHeight(int measuredHeight) {
		if (mMenuView == null) {
			return;
		}

		LayoutParams params = (LayoutParams) mMenuView.getLayoutParams();
		if (params.height != measuredHeight) {
			params.height = measuredHeight;
			mMenuView.setLayoutParams(mMenuView.getLayoutParams());
		}
	}
}
