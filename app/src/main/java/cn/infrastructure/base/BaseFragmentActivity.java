package cn.infrastructure.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

import cn.infrastructure.ui.LoadingDialog;
import cn.infrastructure.utils.SoftInputUtils;
import cn.infrastructure.utils.SystemBarTintManager;
import cn.infrastructure.utils.Utils;

/**
 * FragmentActivity基类，与业务逻辑无关
 *
 * @author Frank 2016-7-1
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    protected Context mContext;// 当前Activity的上下文
    private SystemBarTintManager tintManager;
    protected LoadingDialog loadingDlg;
    //本页面中所使用的Presenter 当页面被销毁时，取消所有请求，以防内存泄漏
    protected ArrayList<BasePresenter> mPresenters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initBeforeCreate();

        super.onCreate(savedInstanceState);
        mContext = this;

        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource());
        }

        bindView();

        loadingDlg = new LoadingDialog(this);
        init();
    }

    /**
     * 在设置ContentView或者Activity的OnCreate之前的初始化
     */
    protected void initBeforeCreate() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置手机屏幕的旋转不触发
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
    }

    protected abstract int getLayoutResource();

    protected abstract void bindView();

    protected abstract void init();

    /**
     * 开启沉浸式设置
     */
    public void openTitleBarSetting() {
        ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0)
                .setFitsSystemWindows(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(this, true);
            // 创建状态栏的管理实例
            tintManager = new SystemBarTintManager(this);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);
            // 激活导航栏设置
            tintManager.setNavigationBarTintEnabled(true);
        }
    }

    /**
     * 设置系统顶部状态栏背景色
     *
     * @param color
     */
    @TargetApi(19)
    public void setStatusBarBackgroundColor(int color) {
        if (tintManager != null) {
            tintManager.setStatusBarTintColor(color);
        }
    }

    /**
     * 设置顶部状态栏背景图
     *
     * @param drawable
     */
    @TargetApi(19)
    public void setStatusBarBackgroundDrabable(Drawable drawable) {
        if (tintManager != null) {
            tintManager.setStatusBarTintDrawable(drawable);
        }
    }

    /**
     * 设置顶部状态栏背景，使用resource id
     *
     * @param res
     */
    @TargetApi(19)
    public void setStatusBarBackgroundRes(int res) {
        if (tintManager != null) {
            tintManager.setStatusBarTintResource(res);
        }
    }

    /**
     * 通过Class跳转界面
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     *
     * @param cls
     * @param bundle
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class跳转界面
     *
     * @param intent
     * @param cls
     */
    public void startActivity(Intent intent, Class<?> cls) {
        intent.setClass(this, cls);
        startActivity(intent);
    }

    /**
     * 通过Class跳转界面
     *
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 通过Class跳转界面
     *
     * @param intent
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Intent intent, Class<?> cls, int requestCode) {
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDlg.dismiss();
        for (int i = 0, size = mPresenters.size(); i < size; i++) {
            mPresenters.get(i).cancelAllRequest();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                SoftInputUtils.hideSoftForWindow(this);
            }
        }
        return super.onTouchEvent(event);
    }

}
