package cn.infrastructure.http;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * get网络缓存拦截器
 *
 * @author Frank
 * @date: 2016-07-12
 * 
 */
public class HttpCacheInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();

		Response originalResponse = chain.proceed(request);

		if (HttpURLConnection.HTTP_CLIENT_TIMEOUT!=originalResponse.code()) {
			// 有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
			String cacheControl = request.cacheControl().toString();
			return originalResponse.newBuilder()
					.header("Cache-Control", cacheControl)
					.removeHeader("Pragma").build();
		} else {
			return originalResponse
					.newBuilder()
					.header("Cache-Control",
							"public, only-if-cached, max-stale=2419200")
					.removeHeader("Pragma").build();
		}
	}
}
