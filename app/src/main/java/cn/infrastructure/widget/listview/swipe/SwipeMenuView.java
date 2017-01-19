package cn.infrastructure.widget.listview.swipe;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * the parent of the swipe menu item
 * Created by Frank on 2016/7/13.
 *
 */
public class SwipeMenuView extends LinearLayout {

    private int mPosition;
    protected SwipeMenu mSwipeMenu;

    public SwipeMenuView(SwipeMenu menu, Context context) {
        super(context);

        mSwipeMenu = menu;
        if (mSwipeMenu == null) {
            return;
        }

        List<SwipeMenuItem> items = menu.getMenuItems();
        int id = 0;
        for (SwipeMenuItem item : items) {
            View view = createMenuItemView(item, id);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT));
            }

            if (view != null) {
                //clear click listener as it will affect scroll
                view.setOnClickListener(null);
                view.setClickable(false);

                if (view.getId() == View.NO_ID) {
                    view.setId(id);
                }

                super.addView(view);
            }

            id++;
        }
    }

    /**
     * override this method if you want to create a custom style, you should set
     * the layout params in the view by the method
     * {@link View#setLayoutParams(android.view.ViewGroup.LayoutParams)}
     * 
     * @param item
     * @param index the index of the menu item in the parent, if you not set, it will set automatically
     */
    protected View createMenuItemView(SwipeMenuItem item, int index) {
        LayoutParams params = new LayoutParams(item.getWidth(), LayoutParams.MATCH_PARENT);
        LinearLayout parent = new LinearLayout(getContext());
        parent.setId(index);
        parent.setGravity(Gravity.CENTER);
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setLayoutParams(params);
        parent.setBackgroundDrawable(item.getBackground());

        if (item.getIcon() != null) {
            parent.addView(createIcon(item));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            parent.addView(createTitle(item));
        }

        return parent;
    }

    private ImageView createIcon(SwipeMenuItem item) {
        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(item.getIcon());
        return iv;
    }

    private TextView createTitle(SwipeMenuItem item) {
        TextView tv = new TextView(getContext());
        tv.setText(item.getTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(item.getTitleSize());
        tv.setTextColor(item.getTitleColor());
        return tv;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

}
