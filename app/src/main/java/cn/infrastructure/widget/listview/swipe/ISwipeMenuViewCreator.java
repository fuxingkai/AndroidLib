package cn.infrastructure.widget.listview.swipe;

import android.content.Context;

/**
 * the interface of swipe menu view creator
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public interface ISwipeMenuViewCreator {

    /**
     * create the swipe menu view according to the swipe menu <br>
     * <b> if the param swipe menu is null, this method will not called </b>
     * 
     * @param context
     *            the context according to the current view
     * @param swipeMenu
     *            you should create swipe menu view according this object, if
     *            null, the method will not call
     * @param position
     *            the position of the swipe menu view in the list view
     * @return
     */
    public SwipeMenuView createSwipeMenuView(Context context, SwipeMenu swipeMenu, int position);

}
