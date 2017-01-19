package cn.infrastructure.widget.listview.swipe;

/**
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public interface IOnSwipeListener {

    /**
     * called when user start to swipe
     * @param itemView
     */
    void onSwipeStart(SwipeMenuLayout itemView);

    /**
     * called when swipe animation end or user swiped to the end
     * @param itemView
     */
    void onSwipeEnd(SwipeMenuLayout itemView);
}
