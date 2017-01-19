package cn.infrastructure.widget.listview.swipe;

/**
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public interface IOnSwipeItemClickListener {

    /**
     * indicate which swipe menu is clicked
     * 
     * @param position
     *            the list view item position according to user click
     * 
     * @param index
     *            this value is according to your setting, refer to
     *            {@link SwipeMenuView#createMenuItemView(SwipeMenuItem, int)}
     */
    void onMenuItemClick(int position, int index);

    /**
     * called when user clicked the content part
     * 
     * @param position
     *            the list view item position according to user click
     */
    void onContentItemClick(int position);
}
