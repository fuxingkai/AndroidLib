package cn.infrastructure.lib.example;

import android.util.Log;

import cn.infrastructure.base.BaseApplication;
import cn.infrastructure.common.Config;
import cn.infrastructure.lib.example.activity.MainActivity;
import cn.infrastructure.utils.data.JsonUtils;
import cn.infrastructure.utils.recovery.callback.RecoveryCallback;
import cn.infrastructure.utils.recovery.core.Recovery;
/**
 * Created by linpei on 2016/8/25.
 */
public class AppApplication extends BaseApplication {

    public static AppApplication instance;

    @Override
    public void onCreate() {
        Config.setDebug(true);
        super.onCreate();
        instance = this;

        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .callback(new MyCrashCallback())
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .init(this);
//
//        AptPreferencesManager.init(this, new AptParser() {
//
//            @Override
//            public Object deserialize(Class clazz, String text) {
//                return JsonUtils.fromJson(text, clazz);
//            }
//
//            @Override
//            public String serialize(Object object) {
//                return JsonUtils.toJson(object);
//            }
//        });

    }

    static final class MyCrashCallback implements RecoveryCallback {
        @Override
        public void stackTrace(String exceptionMessage) {
            Log.e("zxy", "exceptionMessage:" + exceptionMessage);
        }

        @Override
        public void cause(String cause) {
            Log.e("zxy", "cause:" + cause);
        }

        @Override
        public void exception(String exceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
            Log.e("zxy", "exceptionClassName:" + exceptionType);
            Log.e("zxy", "throwClassName:" + throwClassName);
            Log.e("zxy", "throwMethodName:" + throwMethodName);
            Log.e("zxy", "throwLineNumber:" + throwLineNumber);
        }
    }

}
