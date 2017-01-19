package cn.infrastructure.utils.recovery.tools;

import android.util.Log;

import cn.infrastructure.utils.recovery.core.Recovery;

/**
 * Created by Frank on 2016/09/28.
 */
public class RecoveryLog {

    private static final String TAG = "Recovery";

    public static void e(String message) {
        if (Recovery.getInstance().isDebug())
            Log.e(TAG, message);
    }
}
