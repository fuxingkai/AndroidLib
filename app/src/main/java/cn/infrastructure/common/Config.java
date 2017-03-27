package cn.infrastructure.common;

/**
 * Created by linpei on 2016/8/23.
 */
public class Config {

    public static boolean debug = false;

    public static int HTTP_RETRY_COUNT = 2;

    public static String equtype = "0";

    public static void setDebug(boolean debug) {
        Config.debug = debug;
    }

    /**
     * 设置设备类型 0 话机 1 手机
     * 默认值为话机
     *
     * @param equtype
     */
    public static void setEqutype(String equtype) {
        Config.equtype = equtype;
    }

}
