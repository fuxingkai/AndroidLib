package cn.infrastructure.widget.listview.groupable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import cn.infrastructure.widget.listview.IOverScrollListViewSetting;

/**
 * this is wrapper of GroupableOverScrollListView for easy use
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public class GroupableOverScrollListViewWrapper extends
        GroupableOverScrollListView implements IOverScrollListViewSetting {

    private GroupableOverScrollListViewHeFranker mListViewHeFranker;

    public GroupableOverScrollListViewWrapper(Context context) {
        super(context);
        initHeFranker();
    }

    public GroupableOverScrollListViewWrapper(Context context,
            AttributeSet attrs) {
        super(context, attrs);
        initHeFranker();
    }

    public GroupableOverScrollListViewWrapper(Context context,
            AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeFranker();
    }

    private void initHeFranker() {
        mListViewHeFranker = new GroupableOverScrollListViewHeFranker(this);
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
