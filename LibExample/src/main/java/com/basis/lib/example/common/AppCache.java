package com.basis.lib.example.common;

import frank.basis.app.BaseCache;
import frank.basis.common.AppConstants;
import frank.basis.utils.Utils;

/**
 * Created by linpei on 2016/8/25.
 */
public class AppCache extends BaseCache {

    public String a;
    public static String TAG = "AppCache";
    protected static AppCache instance;


    public AppCache() {
        cache_key = this.getClass().getSimpleName();
    }

    /**
     * 获取cache对象，单例模式
     *
     * @return
     */
    public static AppCache getInstance() {
        if (null == instance) {
            synchronized (AppCache.class) {
                if (null == instance) {
                    Object object = Utils.restoreObject(
                            AppConstants.CACHEDIR + TAG);
                    if (object == null) {
                        object = new AppCache();
                        Utils.saveObject(
                                AppConstants.CACHEDIR + TAG, object);
                    }
                    instance = (AppCache) object;
                }
            }
        }
        return instance;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
        save(this);
    }

}
