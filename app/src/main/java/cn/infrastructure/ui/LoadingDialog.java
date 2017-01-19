package cn.infrastructure.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.infrastructure.lib.R;

/**
 * loading弹窗
 *
 * Created by Frank on 2016/9/2.
 */
public class LoadingDialog extends Dialog {

    private ImageView ivLoading;
    private TextView tvMsg;
    private LinearLayout llContent;
    private String msg = null;
    private boolean showLoading = true;
    private int resId = 0;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_loading);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_loading);
        ivLoading = (ImageView) findViewById(R.id.loadingDlg_iv_loading);
        tvMsg = (TextView) findViewById(R.id.loadingDlg_tv_msg);
        llContent= (LinearLayout) findViewById(R.id.loadingDlg_ll_content);
        if (msg != null) {
            tvMsg.setText(msg);
        }
        if (showLoading) {
            if (resId == 0) {
                setProgressView();
            } else {
                ivLoading.setImageResource(resId);
            }
        } else {
            ivLoading.setVisibility(View.GONE);
        }
        setOnKeyListener(keylistener);
        setCanceledOnTouchOutside(false);
    }

    /**
     * 初始化loading控件
     */
    public void setProgressView() {
        ivLoading.setImageResource(R.drawable.loading_icon_white);
        //创建动画
        Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator interpolator = new LinearInterpolator(); //匀速旋转
        animation.setInterpolator(interpolator);
        animation.setDuration(2000); //一次动画耗时2000ms
        animation.setRepeatCount(-1); //重复播放动画
        //显示动画
        ivLoading.startAnimation(animation);
    }

    /**
     * 设置loading提示语
     *
     * @param message
     */
    public void show(String message) {
        show(message, true);
    }

    /**
     * 设置loading提示语，同时设置是否显示加载旋转菊花
     *
     * @param message
     * @param showLoading
     */
    public void show(String message, boolean showLoading) {
        if (tvMsg == null) {
            msg = message;
            this.showLoading = showLoading;
        } else {
            tvMsg.setText(message);
            if (showLoading) {
                setProgressView();
                ivLoading.setVisibility(View.VISIBLE);
            } else {
                ivLoading.clearAnimation();
                ivLoading.setVisibility(View.GONE);
            }
        }
        if (!this.isShowing()) {
            this.show();
        }
    }

    /**
     * 设置提示语以及提示语上方的图片
     *
     * @param message
     * @param resId
     */
    public void show(String message, int resId) {
        if (tvMsg == null) {
            msg = message;
            this.resId = resId;
        } else {
            tvMsg.setText(message);
            ivLoading.clearAnimation();
            ivLoading.setImageResource(resId);
        }
        if (!this.isShowing()) {
            this.show();
        }
    }

    /**
     * 弹出框显示时，拦截返回键
     */
    private OnKeyListener keylistener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

}
