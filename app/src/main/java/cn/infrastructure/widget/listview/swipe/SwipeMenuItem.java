package cn.infrastructure.widget.listview.swipe;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * the java bean object of the swipe menu
 * 
 * Created by Frank on 2016/7/13.
 *
 */
public class SwipeMenuItem {

    private int mId;
    private Context mContext;
    private String mTitle;
    private Drawable mDrawableIcon;
    private Drawable mDrawableBackground;
    private int mColorTitle;
    private int mSizeTitle;
    private int mWidth;

    public SwipeMenuItem(Context context) {
        mContext = context;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getTitleColor() {
        return mColorTitle;
    }

    public int getTitleSize() {
        return mSizeTitle;
    }

    public void setTitleSize(int titleSize) {
        this.mSizeTitle = titleSize;
    }

    public void setTitleColor(int titleColor) {
        this.mColorTitle = titleColor;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setTitle(int resId) {
        setTitle(mContext.getString(resId));
    }

    public Drawable getIcon() {
        return mDrawableIcon;
    }

    public void setIcon(Drawable icon) {
        this.mDrawableIcon = icon;
    }

    public void setIcon(int resId) {
        this.mDrawableIcon = mContext.getResources().getDrawable(resId);
    }

    public Drawable getBackground() {
        return mDrawableBackground;
    }

    public void setBackground(Drawable background) {
        this.mDrawableBackground = background;
    }

    public void setBackground(int resId) {
        this.mDrawableBackground = mContext.getResources().getDrawable(resId);
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

}
