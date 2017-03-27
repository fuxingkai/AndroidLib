package cn.infrastructure.http.factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Rxjava+Retrofit+OkHttp缓存具体工厂角色
 * Created by Frank on 2017/3/27 0024.
 */
public class CacheFactory implements RROFactory {

    @Override
    public OkHttpClient createOkHttpClient() {
        return null;
    }

    @Override
    public Retrofit createRetrofit() {
        return null;
    }

    @Override
    public Retrofit createRetrofit(String baseUrl) {
        return null;
    }

    @Override
    public Retrofit createRetrofit(OkHttpClient okHttpClient, String baseUrl) {
        return null;
    }
}