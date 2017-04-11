package frank.basis.http.entity;

import frank.basis.app.BaseApplication;
import frank.basis.common.Config;

/**
 * 请求参数共同部分
 * Created by Frank on 2016/8/14.
 */
public class Request<T> {

    public String serial_number = "2345234523453"; //交易号/序列号 序列号：yyyymmddhh24missSSS+发起方标识+7 位随机数
    public String timestamp = "3334353452"; //请求时间戳
    public String service_name = "saas.jufuns.cn"; //服务名称
    public String service_ver = "V1.0"; //请求的版本
    public String sign = BaseApplication.mBaseApplication.getPackageName(); //签名
    public String equid = "ddddd"; //设备id
    public String equtype = Config.equtype; //设备类型 0:话机1：手机
    public String token = "ddddddd"; //令牌
    public T request_data; //请求体

    public Request(T request_data) {
        this.request_data = request_data;
    }

    public Request(T request_data, String equtype) {
        this.request_data = request_data;
        this.equtype = equtype;
    }

}

