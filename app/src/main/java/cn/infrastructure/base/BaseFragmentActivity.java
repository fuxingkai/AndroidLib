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

import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cn.infrastructure.ui.LoadingDialog;
import cn.infrastructure.utils.SoftInputUtils;
import cn.infrastructure.utils.SystemBarTintManager;
import cn.infrastructure.utils.Utils;

/**
 * FragmentActivity基类，与业务逻辑无关
 *
 * @author Frank 2016-7-1
 */
public abstract class BaseFragmentActivity extends RxFragmentActivity {

    protected Context mContext;// 当前Activity的上下文
    private SystemBarTintManager tintManager;//沉浸式控制类
    protected LoadingDialog loadingDlg;//加载弹出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initBeforeCreate();

        super.onCreate(savedInstanceState);
        mContext = this;

        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource());
        }

        ButterKnife.bind(this);

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
     * 初始化加载弹窗
     */
    protected void initLoadDlg(){
        if(null == loadingDlg){
            loadingDlg = new LoadingDialog(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != loadingDlg){
            loadingDlg.dismiss();
        }
    }

}
