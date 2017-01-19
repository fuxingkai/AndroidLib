package cn.infrastructure.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftInputUtils {

    public static void hideSoftForWindow(Context context) {
        if (((Activity) context).getCurrentFocus() != null) {
            InputMethodManager inputMethod = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(((Activity) context)
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void hideSoftForWindow(Context context, View view) {
        InputMethodManager inputMethod = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

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

    public static void showSoftForWindow(Context context, View view) {
        showSoftForWindow(context, view, 100);
    }

}
