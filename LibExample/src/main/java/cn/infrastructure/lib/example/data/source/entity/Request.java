package cn.infrastructure.lib.example.data.source.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cn.infrastructure.base.BaseApplication;

/**
 * Created by linpei on 2016/8/14.
 */
public class Request<T> {

    public String serial_number = "2345234523453"; //交易号/序列号 序列号：yyyymmddhh24missSSS+发起方标识+7 位随机数
    public String timestamp = ""; //请求时间戳
    public String service_name = "saas.jufuns.cn"; //服务名称
    public String service_ver = "V1.0"; //请求的版本
    public String sign = BaseApplication.mBaseApplication.getPackageName(); //签名
    public String equid = ""; //设备id
    public String equtype = "1"; //设备类型 0:话机1：手机
    public String token = ""; //令牌
    public T request_data; //请求体

    public Request(T request_data) {
        this.request_data = request_data;
        token = "";
        equid = "dsfdf324";
        timestamp = String.valueOf(System.currentTimeMillis());
        serial_number = getSerialNumber();
    }

    /**
     * 生成随机数
     *
     * @return
     */
    public static String getSerialNumber() {
        StringBuffer sb = new StringBuffer();
        sb.append(new SimpleDateFormat("yyyyMMddhh24mmssSSS").format(new Date()));
        sb.append("mobile");
        for (int i = 0; i < 7; i++) {
            Random r = new Random();
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

}

