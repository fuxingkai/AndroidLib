package cn.infrastructure.widget.listview.swipe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import cn.infrastructure.widget.listview.IOverScrollListViewSetting;
import cn.infrastructure.widget.listview.OverScrollListViewHeFranker;

/**
 * This is a wrapper class that eases use of SwipeMenuListView
 *
 * Created by Frank on 2016/7/13.
 */
public class SwipeMenuListViewWrapper extends SwipeMenuListView implements IOverScrollListViewSetting{

    private OverScrollListViewHeFranker mListViewHeFranker;

    public SwipeMenuListViewWrapper(Context context) {
        super(context);
        initHeFranker();
    }

    public SwipeMenuListViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeFranker();
    }

    public SwipeMenuListViewWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHeFranker();
    }
    
    private void initHeFranker() {
        mListViewHeFranker = new OverScrollListViewHeFranker(this);
    }

    @Override
    public void usePullToRefresh() {
        mListViewHeFranker.usePullToRefresh();
    }

    @Override
    public void usePullToRefresh(int layoutResId) {
        mListViewHeFranker.usePullToRefresh(layoutResId);
    }

    @Override
    public void usePullToLoadMore() {
        mListViewHeFranker.usePullToLoadMore();
    }

    @Override
    public void usePullToLoadMore(int layoutResId) {
        mListViewHeFranker.usePullToLoadMore(layoutResId);
    }

    @Override
    public void setPTRPullText(String pullText) {
        mListViewHeFranker.setPTRPullText(pullText);
    }

    @Override
    public void setPTRReleaseText(String releaseText) {
        mListViewHeFranker.setPTRReleaseText(releaseText);
    }

    @Override
    public void setPTRRefreshText(String refreshText) {
        mListViewHeFranker.setPTRRefreshText(refreshText);
    }

    @Override
    public void setPTRFinishSuccessText(String finishText) {
        mListViewHeFranker.setPTRFinishSuccessText(finishText);
    }

    @Override
    public void setPTRFinishFailText(String finishText) {
        mListViewHeFranker.setPTRFinishFailText(finishText);
    }

    @Override
    public void setPTLMPullText(String pullText) {
        mListViewHeFranker.setPTLMPullText(pullText);
    }

    @Override
    public void setPTLMClickText(String clickText) {
        mListViewHeFranker.setPTLMClickText(clickText);
    }

    @Override
    public void setPTLMReleaseText(String releaseText) {
        mListViewHeFranker.setPTLMReleaseText(releaseText);
    }

    @Override
    public void setPTLMLoadingText(String loadingText) {
        mListViewHeFranker.setPTLMLoadingText(loadingText);
    }

    @Override
    public void setPTLMBg(Drawable drawable) {
        mListViewHeFranker.setPTLMBg(drawable);
    }
}
