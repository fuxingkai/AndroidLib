package cn.infrastructure.http.exception;

import cn.infrastructure.common.AppConstants;
import cn.infrastructure.http.entity.Response;

/**
 *
 * 自定义异常，当接口返回的{@link Response#ret_code}不为{@link AppConstants#OK}时，需要跑出此异常
 * eg：登陆时验证码错误；参数为传递等
 * Created by Frank on 2017/3/24 0024.
 */

public class HttpApiException extends Exception{

    public String code;
    public String message;

    public HttpApiException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
