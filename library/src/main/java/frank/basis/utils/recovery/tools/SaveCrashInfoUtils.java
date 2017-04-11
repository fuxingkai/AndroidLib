package frank.basis.utils.recovery.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import frank.basis.utils.DateUtils;
import frank.basis.utils.NetWorkUtils;
import frank.basis.utils.Utils;
import frank.basis.utils.data.JsonUtils;
import frank.basis.log.QLog;

/**
 * Created by Frank on 2016/9/28.
 */

public class SaveCrashInfoUtils {

    /**
     * 保存异常信息到本地数据库
     *
     * @param ctx
     */
    public static void saveErrorInfoToDB(Context ctx, Throwable ex) {
//
//        CrashLog crashLog = new CrashLog();
//
//        try {
//            ActivityManager am = (ActivityManager) ctx
//                    .getSystemService(Context.ACTIVITY_SERVICE);
//            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
//            am.getMemoryInfo(mi);
//            String memoryInfo = "Memory info:" + mi.availMem + ",app holds:"
//                    + mi.threshold + ",Low Memory:" + mi.lowMemory;
//
//            crashLog.setClientType("phone");
//            crashLog.setClientApp("JuMobile");
//            crashLog.setPackageName(ctx.getPackageName());
//            crashLog.setExceptionType("crash");
//            crashLog.setExceptionName(ex.getClass().getName());
//            crashLog.setNetWorkType(NetWorkUtils.isWifiConnected(ctx) ? "is wifi" : "no wifi");
//            crashLog.setDeviceId(Utils.getDeviceID(ctx));
//            crashLog.setDeviceModel(Build.MODEL);
//            crashLog.setOsVersion(Build.VERSION.RELEASE);
//            crashLog.setAppVersionCode(ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode + "");
//            crashLog.setAppVersionName(ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName);
//            crashLog.setCrashTime(DateUtils.getNormalTime(System.currentTimeMillis()));
//            crashLog.setExceptionName(ex.getClass().getName());
//            crashLog.setExceptionStack(Utils.getStackTraceString(ex));
//
//            crashLog.setMemoryInfo(memoryInfo);
//
//            DaoHelper.getInstance(ctx).getCrashLogDao().insert(crashLog);
//
//            QLog.json(JsonUtils.toJson(DaoHelper.getInstance(ctx).getCrashLogDao().queryBuilder().build().unique()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
