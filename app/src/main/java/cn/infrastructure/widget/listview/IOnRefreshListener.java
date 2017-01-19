package cn.infrastructure.widget.listview;

/**
 * The listener to be registered through
 * OverScrollListView.setOnRefreshListener()
 * 
 * Created by Frank on 2016/7/13.
 */
public interface IOnRefreshListener {
    void onRefresh(Object bizContext);

    void onRefreshAnimationEnd();
}
