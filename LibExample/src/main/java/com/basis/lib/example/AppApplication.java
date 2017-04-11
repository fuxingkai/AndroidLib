package com.basis.lib.example;

import android.util.Log;

import com.basis.lib.example.activity.MainActivity;

import frank.basis.app.BaseApplication;
import frank.basis.common.Config;
import frank.basis.http.RROClient;
import frank.basis.http.factory.CacheFactory;
import frank.basis.utils.recovery.callback.RecoveryCallback;
import frank.basis.utils.recovery.core.Recovery;
/**
 * Created by linpei on 2016/8/25.
 */
public class AppApplication extends BaseApplication {

    public static AppApplication instance;

    private RROClient RROClient;

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

        String url = "http://172.16.1.9:8280";

        RROClient = new RROClient.
                Builder().
                url(url).
                addRROFactory(new CacheFactory()).
                build();
    }

    /**
     * 获得RetrofitClient
     * @return
     */
    public RROClient getRROClient(){
        return RROClient;
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
