package cn.infrastructure.http;

/**
 * RRO相关工具类
 * Created by Frank on 2017/3/27 0027.
 */

public class Utils {
    static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
