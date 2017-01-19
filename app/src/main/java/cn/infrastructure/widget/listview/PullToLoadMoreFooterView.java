package cn.infrastructure.widget.listview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.infrastructure.lib.R;
import cn.infrastructure.widget.RotateView;

/**
 * The default implementation of a pull-to-load-more footer view for OverScrollListView.
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public class PullToLoadMoreFooterView extends FrameLayout implements IPullToLoadMoreCallback {
    private TextView mTvLoadMore;
//    private ProgressBar mProgressBar;
    
    private View mRotateView;

    private String mPullText = "上拉加载更多";
    private String mClickText = "点击加载更多";
    private String mReleaseText = "松开加载更多";
    private String mLoadingText = "加载中...";

    public PullToLoadMoreFooterView(Context context) {
        super(context);
        initView();
    }

    public PullToLoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToLoadMoreFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ensuresLoadMoreViewsAvailability();
    }

    private void ensuresLoadMoreViewsAvailability() {
        if (mTvLoadMore == null) {
            int idTvLoadMore = getResources().getIdentifier("tv_load_more", "id", getContext().getPackageName());
            if (idTvLoadMore == 0) {
                throw new RuntimeException("can not found the id with \"tv_load_more\", check your xml file manually");
            }

            mTvLoadMore = (TextView)findViewById(idTvLoadMore);
            if (mTvLoadMore == null) {
                throw new RuntimeException("can not found the view with id \"tv_load_more\", check your xml file manually");
            }
        }

        mTvLoadMore.setText(mClickText);

        if (mRotateView == null) {
            int idRotateView = getResources().getIdentifier("view_rotate", "id", getContext().getPackageName());
            if (idRotateView == 0) {
                throw new RuntimeException("can not found the id with \"view_rotate\", check your xml file manually");
            }

            mRotateView = findViewById(idRotateView);

            if (mRotateView == null) {
                throw new RuntimeException("can not found the view with id \"view_rotate\", check your xml file manually");
            }
        }
    }

    private void initView() {
        super.setClickable(true);

        int paddingVertical = dp2px(20);

        mTvLoadMore = new TextView(getContext());
        mTvLoadMore.setText(mClickText);
        mTvLoadMore.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        mTvLoadMore.setTextColor(Color.parseColor("#ff999999"));

        LinearLayout layoutContent = new LinearLayout(getContext());
        layoutContent.setOrientation(LinearLayout.HORIZONTAL);
        layoutContent.setGravity(Gravity.CENTER_VERTICAL);
        layoutContent.setPadding(0, paddingVertical, 0, paddingVertical);

        int rotateViewSize = paddingVertical;
        RotateView rotateView = new RotateView(getContext());
        mRotateView = rotateView;
        rotateView.setImageResource(R.mipmap.ic_loading);
        rotateView.setVisibility(View.GONE);
        layoutContent.addView(rotateView, new LinearLayout.LayoutParams(rotateViewSize, rotateViewSize));
        
        LinearLayout.LayoutParams FrankLoadMore = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        FrankLoadMore.leftMargin = dp2px(12);
        layoutContent.addView(mTvLoadMore, FrankLoadMore);

        this.addView(layoutContent, new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));

        initClickEffect();
    }

    private void initClickEffect() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled },
                new ColorDrawable(Color.parseColor("#fff5f5f5")));
        stateListDrawable.addState(new int[] {}, new ColorDrawable(Color.WHITE));
        this.setBackgroundDrawable(stateListDrawable);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onReset() {
        ensuresLoadMoreViewsAvailability();
        getChildAt(0).setVisibility(VISIBLE);
    }

    @Override
    public void onStartPulling() {
        ensuresLoadMoreViewsAvailability();
        mTvLoadMore.setText(mPullText);
    }

    @Override
    public void onCanceFrankulling() {
        ensuresLoadMoreViewsAvailability();
        mTvLoadMore.setText(mClickText);
    }

    @Override
    public void onReachAboveRefreshThreshold() {
        mTvLoadMore.setText(mReleaseText);
    }

    @Override
    public void onReachBelowRefreshThreshold() {
        onStartPulling();
    }

    @Override
    public void onStartLoadingMore() {
        ensuresLoadMoreViewsAvailability();
        mRotateView.setVisibility(VISIBLE);
        mTvLoadMore.setText(mLoadingText);
    }

    @Override
    public void onEndLoadingMore() {
        mRotateView.setVisibility(GONE);
        mTvLoadMore.setText(mClickText);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        getChildAt(0).setVisibility(visibility);
    }

    public void setPullText(String pullText) {
        mPullText = pullText;
    }

    public void setClickText(String clickText) {
        mClickText = clickText;
    }

    public void setReleaseText(String releaseText) {
        mReleaseText = releaseText;
    }

    public void setLoadingText(String loadingText) {
        mLoadingText = loadingText;
    }
}
