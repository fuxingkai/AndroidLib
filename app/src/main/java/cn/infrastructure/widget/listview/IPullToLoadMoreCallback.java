package cn.infrastructure.widget.listview;

/**
 * The interface to be implemented by footer view to be used with OverScrollListView
 * 
 * Created by Frank on 2016/7/13.
 */
public interface IPullToLoadMoreCallback {
    void onReset();
    void onStartPulling();

    void onReachAboveRefreshThreshold();
    void onReachBelowRefreshThreshold();

    void onStartLoadingMore();
    void onEndLoadingMore();

    void onCancelPulling();
}
