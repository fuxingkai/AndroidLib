package cn.infrastructure.widget.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.infrastructure.lib.R;
import cn.infrastructure.widget.RotateView;

/**
 * this is default pull to refresh header view
 * 
 * Created by Frank on 2016/7/13.
 */
public class PullToRefreshHeaderView extends LinearLayout implements
		IPullToRefreshCallback {

	private final static int ROTATE_ANIMATION_DURATION = 300;

	private ImageView mArrowView;
	private ImageView mFinishView;
	private TextView mTvRefresh;
	private View mRotateView;

	private Animation mAnimRotateUp;
	private Animation mAnimRotateDown;

	private String mPullText = "下拉刷新";
	private String mReleaseText = "松开刷新";
	private String mRefreshText = "正在刷新";
	private String mFinishSuccessText = "刷新成功";
	private String mFinishFailedText = "刷新失败";

	public PullToRefreshHeaderView(Context context) {
		super(context);
		init();
	}

	public PullToRefreshHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("NewApi")
	public PullToRefreshHeaderView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private void init() {
		mAnimRotateUp = new RotateAnimation(0, -180f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mAnimRotateUp.setDuration(ROTATE_ANIMATION_DURATION);
		mAnimRotateUp.setFillAfter(true);
		mAnimRotateDown = new RotateAnimation(-180f, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mAnimRotateDown.setDuration(ROTATE_ANIMATION_DURATION);
		mAnimRotateDown.setFillAfter(true);

		super.setOrientation(LinearLayout.HORIZONTAL);

		this.initView();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (mArrowView == null) {
			int idArrowView = getResources().getIdentifier("iv_down_arrow",
					"id", getContext().getPackageName());
			if (idArrowView == 0) {
				throw new RuntimeException(
						"can not found the id with \"iv_down_arrow\", check your xml file manually");
			}

			mArrowView = (ImageView) findViewById(idArrowView);

			if (mArrowView == null) {
				throw new RuntimeException(
						"can not found the view with id \"iv_down_arrow\", check your xml file manually");
			}
		}

		if (mFinishView == null) {
			int idFinishView = getResources().getIdentifier("iv_finish", "id",
					getContext().getPackageName());
			if (idFinishView == 0) {
				throw new RuntimeException(
						"can not found the id with \"iv_finish\", check your xml file manually");
			}

			mFinishView = (ImageView) findViewById(idFinishView);

			if (mFinishView == null) {
				throw new RuntimeException(
						"can not found the view with id \"iv_finish\", check your xml file manually");
			}
		}

		if (mRotateView == null) {
			int idRotateView = getResources().getIdentifier("view_rotate",
					"id", getContext().getPackageName());
			if (idRotateView == 0) {
				throw new RuntimeException(
						"can not found the id with \"view_rotate\", check your xml file manually");
			}

			mRotateView = findViewById(idRotateView);

			if (mRotateView == null) {
				throw new RuntimeException(
						"can not found the view with id \"view_rotate\", check your xml file manually");
			}
		}

		if (mTvRefresh == null) {
			int idTvRefresh = getResources().getIdentifier("tv_refresh", "id",
					getContext().getPackageName());
			if (idTvRefresh == 0) {
				throw new RuntimeException(
						"can not found the id with \"tv_refresh\", check your xml file manually");
			}

			mTvRefresh = (TextView) findViewById(idTvRefresh);

			if (mTvRefresh == null) {
				throw new RuntimeException(
						"can not found the view with id \"tv_refresh\", check your xml file manually");
			}
		}

	}

	private void initView() {
		LinearLayout layoutHeader = new LinearLayout(getContext());
		layoutHeader.setOrientation(LinearLayout.VERTICAL);
		layoutHeader.setGravity(Gravity.CENTER_HORIZONTAL);
		layoutHeader.setBackgroundResource(R.color.colorPrimary);
		int dimenContentVerticalMargin = dp2px(20);

		FrameLayout layoutIcon = new FrameLayout(getContext());
		int dimenLayoutIcon = dp2px(20);
		LayoutParams FrankLayoutIcon = new LayoutParams(
				dimenLayoutIcon, dimenLayoutIcon);
		FrankLayoutIcon.topMargin = dimenContentVerticalMargin;
		layoutHeader.addView(layoutIcon, FrankLayoutIcon);

		mArrowView = new ImageView(getContext());
		mArrowView.setImageDrawable(getResources().getDrawable(
				R.mipmap.ic_down_arrow));
		layoutIcon.addView(mArrowView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));

		mFinishView = new ImageView(getContext());
		mFinishView.setImageDrawable(getResources().getDrawable(
				R.mipmap.ic_load_success));
		mFinishView.setVisibility(View.GONE);
		layoutIcon.addView(mFinishView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));

		RotateView rotateView = new RotateView(getContext());
		rotateView.setImageResource(R.mipmap.ic_loading);
		mRotateView = rotateView;
		mRotateView.setVisibility(View.GONE);
		layoutIcon.addView(mRotateView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));

		mTvRefresh = new TextView(getContext());
		mTvRefresh.setText(mPullText);
		mTvRefresh.setGravity(Gravity.CENTER);
		mTvRefresh.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		mTvRefresh.setTextColor(Color.parseColor("#999999"));

		LayoutParams FrankTvRefresh = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		FrankTvRefresh.setMargins(0, dp2px(15), 0, dimenContentVerticalMargin);
		layoutHeader.addView(mTvRefresh, FrankTvRefresh);

		int dimenContentHeight = dp2px(105);

		this.addView(layoutHeader, new LayoutParams(
				LayoutParams.MATCH_PARENT, dimenContentHeight));
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getContext().getResources().getDisplayMetrics());
	}

	@Override
	public void onStartPulling() {
		mArrowView.setVisibility(View.VISIBLE);
		mRotateView.setVisibility(View.GONE);
		mFinishView.setVisibility(View.GONE);
		mTvRefresh.setVisibility(View.VISIBLE);
		mTvRefresh.setText(mPullText);
	}

	/**
	 * @param scrollY
	 *            [screenHeight, 0]
	 */
	@Override
	public void onPull(int scrollY) {
	}

	@Override
	public void onReachAboveHeaderViewHeight() {
		mArrowView.setVisibility(View.VISIBLE);
		mRotateView.setVisibility(View.GONE);
		mFinishView.setVisibility(View.GONE);
		mTvRefresh.setVisibility(View.VISIBLE);
		mTvRefresh.setText(mReleaseText);
		mArrowView.startAnimation(mAnimRotateUp);
	}

	@Override
	public void onReachBelowHeaderViewHeight() {
		mArrowView.setVisibility(View.VISIBLE);
		mRotateView.setVisibility(View.GONE);
		mFinishView.setVisibility(View.GONE);
		mTvRefresh.setVisibility(View.VISIBLE);
		mTvRefresh.setText(mPullText);
		mArrowView.startAnimation(mAnimRotateDown);
	}

	@Override
	public void onStartRefreshing() {
		mArrowView.setVisibility(View.GONE);
		mRotateView.setVisibility(View.VISIBLE);
		mFinishView.setVisibility(View.GONE);
		mTvRefresh.setVisibility(View.VISIBLE);
		mTvRefresh.setText(mRefreshText);
		mArrowView.clearAnimation();
	}

	@Override
	public void onEndRefreshing() {
		mArrowView.setVisibility(View.GONE);
		mRotateView.setVisibility(View.GONE);
		mFinishView.setVisibility(View.GONE);
		mTvRefresh.setVisibility(View.GONE);
	}

	@Override
	public void onFinishRefreshingSuccess() {
		mArrowView.setVisibility(View.GONE);
		mRotateView.setVisibility(View.GONE);
		mFinishView.setImageResource(R.mipmap.ic_load_success);
		mFinishView.setVisibility(View.VISIBLE);
		mTvRefresh.setVisibility(View.VISIBLE);
		mTvRefresh.setText(mFinishSuccessText);
	}

	@Override
	public void onFinishRefreshingFailed() {
		mArrowView.setVisibility(View.GONE);
		mRotateView.setVisibility(View.GONE);
		mFinishView.setImageResource(R.mipmap.ic_load_failed);
		mFinishView.setVisibility(View.VISIBLE);
		mTvRefresh.setVisibility(View.VISIBLE);
		mTvRefresh.setText(mFinishFailedText);
	}

	public void setPullText(String pullText) {
		mPullText = pullText;
	}

	public void setReleaseText(String releaseText) {
		mReleaseText = releaseText;
	}

	public void setRefreshText(String refreshText) {
		mRefreshText = refreshText;
	}

	public void setFinishSuccessText(String finishText) {
		mFinishSuccessText = finishText;
	}

	public void setFinishFailedText(String finishText) {
		mFinishFailedText = finishText;
	}

}