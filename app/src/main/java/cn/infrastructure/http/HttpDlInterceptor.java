package cn.infrastructure.http;

import java.io.IOException;

import cn.infrastructure.http.listener.DlPListener;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 下载拦截器
 * @author Frank
 * @date: 2017/4/2
 */

public class HttpDlInterceptor implements Interceptor {

    private DlPListener listener; //下载进度监听

    public HttpDlInterceptor(DlPListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DlResponseBody(originalResponse.body(), listener))
                .build();
    }
}
