package cn.infrastructure.entity;

import cn.infrastructure.common.AppConstants;

/**
 * Created by Frank on 2016/7/14.
 */
public class Response<T> {

    public String ret_code; //请求代码 1：成功 非1：异常
    public String ret_msg; //请求描述语  当ret_code非1时，存放异常信息
    public T response_data; //返回结果体
    public String serial_number; //交易号/序列号
    public String timestamp; //生成结果的时间戳


    /**
     * 请求是否成功
     * @return
     */
    public boolean isSuccess() {
        return ret_code.equals(AppConstants.OK);
    }
}
