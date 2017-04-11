package frank.basis.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import frank.basis.R;


/**
 * Created by Frank on 2015/06/21.
 */
public class ToastUtil {

    private static Toast mToast = null;
    private static Toast mMidleToast = null;
    private static Context mContext = null;

    public static void show(int resId) {
        show(mContext.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        show(mContext.getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(int resId, Object... args) {
        show(String.format(mContext.getResources().getString(resId), args));
    }

    public static void show(String format, Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    public static void show(CharSequence text, int duration) {
        if (mContext == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();

        }
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static void showMidleToast(String msg) {
        showMidleToast(msg, 1);
    }

    public static void showMidleToast(String msg, int duration) {
        if (mMidleToast == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View layout = inflater.inflate(R.layout.view_customer_toast, null);
            mMidleToast = new Toast(mContext);
            mMidleToast.setGravity(Gravity.CENTER, 0, 0);
            mMidleToast.setView(layout);
            mMidleToast.setDuration(duration);
        }
        ((TextView) mMidleToast.getView().findViewById(R.id.toast_tv_content)).setText(msg);
        mMidleToast.show();
    }

}
