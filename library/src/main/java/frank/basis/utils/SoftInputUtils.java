package frank.basis.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * 输入法操作工具类
 * Created by Frank on 2017/3/27 0027.
 */
public class SoftInputUtils {

    /**
     * 隐藏输入法
     * @param context
     */
    public static void hideSoftForWindow(Context context) {
        if (((Activity) context).getCurrentFocus() != null) {
            InputMethodManager inputMethod = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(((Activity) context)
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 隐藏输入法
     * @param context
     * @param view
     */
    public static void hideSoftForWindow(Context context, View view) {
        InputMethodManager inputMethod = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示输入法
     * @param context
     * @param view
     * @param delay
     */
    public static void showSoftForWindow(final Context context, final View view, int delay) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.requestFocus();
                InputMethodManager inputMethod = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethod.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, delay);
    }

    /**
     * 显示输入法
     * @param context
     * @param view
     */
    public static void showSoftForWindow(Context context, View view) {
        showSoftForWindow(context, view, 100);
    }

}
