package cn.infrastructure.widget.listview.swipe;

/**
 * the interface of swipe menu creator
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public interface ISwipeMenuCreator {

    /**
     * create the swipe menu for the list view item, if you do not want to
     * create the swipe menu, just return null instead
     * 
     * @param itemViewType
     *            the type of the list view item
     * @param position
     *            the position in the list view
     * @return
     */
    public SwipeMenu createSwipeMenu(int itemViewType, int position);
}
