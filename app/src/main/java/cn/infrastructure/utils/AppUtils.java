package cn.infrastructure.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Created by Frank on 2015/07/21.
 */
public class AppUtils {

    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int i = -1;
        try {
            i = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 获取应用版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String str = "";
        try {
            str = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "未知版本名";
        }
        return str;
    }
}
