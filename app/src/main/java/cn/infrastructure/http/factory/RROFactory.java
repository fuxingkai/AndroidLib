package cn.infrastructure.http.factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Rxjava+Retrofit+OkHttp抽象工厂角色
 * Created by Frank on 2017/3/27 0024.
 */

public interface RROFactory{
    /**
     * 获得OkHttpClient 实例
     * @return
     */
    public OkHttpClient createOkHttpClient();

    /**
     * 获得Retrofit实例
     * @see RROFactory#createOkHttpClient
     * 实现这个方法可以调用 createOkHttpClient
     * 创建一个OkHttpClient{@link OkHttpClient}实例也可以自己创建
     * @return
     */
    public Retrofit createRetrofit();

    /**
     * 获得Retrofit实例
     * @see RROFactory#createOkHttpClient
     * 实现这个方法可以调用 createOkHttpClient
     * 创建一个OkHttpClient{@link OkHttpClient}实例也可以自己创建
     * @param baseUrl 主机地址，HttpUrl{@link okhttp3.HttpUrl}的字符串形式
     * @return
     */
    public Retrofit createRetrofit(String baseUrl);

    /**
     * 获得Retrofit实例
     * @param baseUrl 主机地址，HttpUrl{@link okhttp3.HttpUrl}的字符串形式
     * @param okHttpClient OkHttpClient实例{@link OkHttpClient}
     * @return
     */
    public Retrofit createRetrofit(OkHttpClient okHttpClient,String baseUrl);
}
