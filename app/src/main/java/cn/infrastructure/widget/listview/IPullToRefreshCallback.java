package cn.infrastructure.widget.listview;

/**
 * The interface to be implemented by header view to be used with OverScrollListView
 * 
 * Created by Frank on 2016/7/13.
 */
public interface IPullToRefreshCallback {
    void onStartPulling();

    // scrollY = how far have we pulled?
    void onPull(int scrollY);

    void onReachAboveHeaderViewHeight();
    void onReachBelowHeaderViewHeight();

    void onStartRefreshing();
    void onEndRefreshing();
    void onFinishRefreshingSuccess();
    void onFinishRefreshingFailed();
}
