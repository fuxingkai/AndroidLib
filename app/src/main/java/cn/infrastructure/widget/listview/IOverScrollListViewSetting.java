package cn.infrastructure.widget.listview;

import android.graphics.drawable.Drawable;

/**
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public interface IOverScrollListViewSetting {

    /**
     * 使用默认的下拉刷新
     */
    void usePullToRefresh();

    /**
     * 使用自定义的下拉刷新， <br/>
     * NOTE: 需要实现接口 {@link IPullToRefreshCallback}, 否则会抛异常
     * 
     * @param layoutResId
     */
    void usePullToRefresh(int layoutResId);

    /**
     * 使用默认的上拉加载更多
     */
    void usePullToLoadMore();

    /**
     * 使用自定义的上拉加载更多， <br/>
     * NOTE: 需要实现接口 {@link IPullToLoadMoreCallback}， 否则会抛异常
     * 
     * @param layoutResId
     */
    void usePullToLoadMore(int layoutResId);

    /**
     * 设置下拉但未触发刷新的文本， 如"下拉刷新"
     * 
     * @param pullText
     */
    void setPTRPullText(String pullText);

    /**
     * 设置下拉但可触发刷新的文本，如"松开刷新"
     * 
     * @param releaseText
     */
    void setPTRReleaseText(String releaseText);

    /**
     * 设置正在刷新时的文本，如"正在刷新"
     * 
     * @param refreshText
     */
    void setPTRRefreshText(String refreshText);

    /**
     * 设置刷新成功时的文本，如"刷新成功"
     * 
     * @param finishText
     */
    void setPTRFinishSuccessText(String finishText);

    /**
     * 设置刷新失败时的文本，如"刷新失败"
     * 
     * @param finishText
     */
    void setPTRFinishFailText(String finishText);

    /**
     * 设置上拉但未触发加载时的文本，如"上拉加载更多"
     * 
     * @param pullText
     */
    void setPTLMPullText(String pullText);

    /**
     * 默认状态下加载更多的文本，如"点击加载更多"
     * 
     * @param clickText
     */
    void setPTLMClickText(String clickText);

    /**
     * 设置上拉且可触发加载更多时的文本，如"松开加载更多"
     * 
     * @param releaseText
     */
    void setPTLMReleaseText(String releaseText);

    /**
     * 设置正在加载更多时的文本，如"加载中..."
     * 
     * @param loadingText
     */
    void setPTLMLoadingText(String loadingText);

    /**
     * 设置默认状态下加载更多的背景
     * 
     * @param drawable
     */
    void setPTLMBg(Drawable drawable);
}
