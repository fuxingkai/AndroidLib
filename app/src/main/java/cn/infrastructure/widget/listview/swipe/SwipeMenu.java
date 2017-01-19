package cn.infrastructure.widget.listview.swipe;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public class SwipeMenu {

    private List<SwipeMenuItem> mItems;
    private int mViewType;

    public SwipeMenu() {
        mItems = new ArrayList<SwipeMenuItem>();
    }

    public void addMenuItem(SwipeMenuItem item) {
        mItems.add(item);
    }

    public void removeMenuItem(SwipeMenuItem item) {
        mItems.remove(item);
    }

    public List<SwipeMenuItem> getMenuItems() {
        return mItems;
    }

    public SwipeMenuItem getMenuItem(int index) {
        return mItems.get(index);
    }

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(int viewType) {
        this.mViewType = viewType;
    }

}
