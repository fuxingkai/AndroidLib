package cn.infrastructure.widget.listview.swipe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import cn.infrastructure.widget.listview.IOverScrollListViewSetting;
import cn.infrastructure.widget.listview.OverScrollListViewHelper;

/**
 * This is a wrapper class that eases use of SwipeMenuListView
 *
 * Created by Frank on 2016/7/13.
 */
public class SwipeMenuListViewWrapper extends SwipeMenuListView implements IOverScrollListViewSetting{

    private OverScrollListViewHelper mListViewHelper;

    public SwipeMenuListViewWrapper(Context context) {
        super(context);
        initHelper();
    }

    public SwipeMenuListViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHelper();
    }

    public SwipeMenuListViewWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHelper();
    }
    
    private void initHelper() {
        mListViewHelper = new OverScrollListViewHelper(this);
    }

    @Override
    public void usePullToRefresh() {
        mListViewHelper.usePullToRefresh();
    }

    @Override
    public void usePullToRefresh(int layoutResId) {
        mListViewHelper.usePullToRefresh(layoutResId);
    }

    @Override
    public void usePullToLoadMore() {
        mListViewHelper.usePullToLoadMore();
    }

    @Override
    public void usePullToLoadMore(int layoutResId) {
        mListViewHelper.usePullToLoadMore(layoutResId);
    }

    @Override
    public void setPTRPullText(String pullText) {
        mListViewHelper.setPTRPullText(pullText);
    }

    @Override
    public void setPTRReleaseText(String releaseText) {
        mListViewHelper.setPTRReleaseText(releaseText);
    }

    @Override
    public void setPTRRefreshText(String refreshText) {
        mListViewHelper.setPTRRefreshText(refreshText);
    }

    @Override
    public void setPTRFinishSuccessText(String finishText) {
        mListViewHelper.setPTRFinishSuccessText(finishText);
    }

    @Override
    public void setPTRFinishFailText(String finishText) {
        mListViewHelper.setPTRFinishFailText(finishText);
    }

    @Override
    public void setPTLMPullText(String pullText) {
        mListViewHelper.setPTLMPullText(pullText);
    }

    @Override
    public void setPTLMClickText(String clickText) {
        mListViewHelper.setPTLMClickText(clickText);
    }

    @Override
    public void setPTLMReleaseText(String releaseText) {
        mListViewHelper.setPTLMReleaseText(releaseText);
    }

    @Override
    public void setPTLMLoadingText(String loadingText) {
        mListViewHelper.setPTLMLoadingText(loadingText);
    }

    @Override
    public void setPTLMBg(Drawable drawable) {
        mListViewHelper.setPTLMBg(drawable);
    }
}
