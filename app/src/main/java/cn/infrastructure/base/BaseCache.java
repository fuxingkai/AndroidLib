package cn.infrastructure.base;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

import cn.infrastructure.common.AppConstants;
import cn.infrastructure.utils.ToastUtil;
import cn.infrastructure.utils.Utils;
import cn.infrastructure.utils.data.StringUtils;

/**
 * 缓存基类，与业务逻辑无关
 * public AppCache getInstance() {
 * if (null == instance) {
 * synchronized (this) {
 * if (null == instance) {
 * Object object = Utils.restoreObject(
 * AppConstants.CACHEDIR + this.getClass().getSimpleName());
 * if (object == null) {
 * Utils.saveObject(
 * AppConstants.CACHEDIR + this.getClass().getSimpleName(), object);
 * }
 * instance = (AppCache) object;
 * }
 * }
 * }
 * return instance;
 * }
 *
 * @author Frank 2016-7-2
 */
public abstract class BaseCache implements Serializable, Cloneable {

    /**
     * 反序列化时唯一标识.若不填，当类结构发生变化时反序列化就会出错.
     */
    protected static final long serialVersionUID = 1L;


    /**
     * 缓存对象序列化后存储在SharedPreferences中的key
     */
    protected String cache_key;

    /**
     * 应用内传递数据，Object需支持序列化（常用数据类型基本只有JSONObject和JSONArray不支持序列化）
     * 注意deliverMap中的数据只提供一次性消费，用完就移除了.
     */
    protected HashMap<String, Object> deliverMap;

    /**
     * 判断deliverMap是否有某个值，有的话顺便移除
     *
     * @param key
     * @return
     */
    public boolean isHasValueThenRemove(String key) {
        if (null != deliverMap && deliverMap.containsKey(key)) {
            deliverMap.remove(key);
            return true;
        }
        return false;
    }

    /**
     * 向deliverMap存放键值对
     *
     * @param key
     * @param value
     */
    public void putValue(String key, Object value) {
        if (null != key && null != deliverMap && !deliverMap.containsKey(key)) {
            deliverMap.put(key, value);
        }
    }

    /**
     * 获取deliverMap中的某个值，有的话顺便移除
     *
     * @param key
     * @return
     */
    public Object getValueThenRemove(String key) {
        if (null != deliverMap && deliverMap.containsKey(key)) {
            Object obj = deliverMap.get(key);
            deliverMap.remove(key);
            return obj;
        }
        return null;
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    /**
     * @param cache
     */
    public void save(BaseCache cache) {
        if (StringUtils.isEmpty(cache_key)) {
            ToastUtil.show("BaseCache's cache can not be null");
            return;
        }
        Utils.saveObject(
                AppConstants.CACHEDIR + cache_key, cache);
    }

    /**
     * @param cache
     * @param fileName
     */
    public void save(BaseCache cache, String fileName) {
        if (StringUtils.isEmpty(cache_key)) {
            ToastUtil.show("filename can not be null");
            return;
        }
        Utils.saveObject(
                AppConstants.CACHEDIR + fileName, cache);
    }

}
