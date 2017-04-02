package cn.infrastructure.http.factory;

import java.util.concurrent.TimeUnit;

import cn.infrastructure.http.HttpDlInterceptor;
import cn.infrastructure.http.HttpLogInterceptor;
import cn.infrastructure.http.listener.DlPListener;
import cn.infrastructure.http.retrofit.gson.GsonConverterFactory;
import cn.infrastructure.http.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import cn.infrastructure.log.QLog;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Rxjava+Retrofit+OkHttp下载具体工厂角色
 * Created by Frank on 2017/3/27 0024.
 */
public class DlFactory implements RROFactory {

    OkHttpClient.Builder okHttpBuilder;//okHttp的构建者
    Retrofit.Builder retrofitBuilder;//Retrofit的构建者
    DlPListener dlPListener;//下载监听

    public DlFactory(){}

    public DlFactory(OkHttpClient.Builder okHttpBuilder, Retrofit.Builder retrofitBuilder) {
        this.okHttpBuilder = okHttpBuilder;
        this.retrofitBuilder = retrofitBuilder;
    }

    public DlFactory(OkHttpClient.Builder okHttpBuilder, Retrofit.Builder retrofitBuilder, DlPListener dlPListener) {
        this.okHttpBuilder = okHttpBuilder;
        this.retrofitBuilder = retrofitBuilder;
        this.dlPListener = dlPListener;
    }

    /**
     * 获得OkHttpClient实例
     *
     * @return
     */
    @Override
    public OkHttpClient createOkHttpClient() {
        /**
         * 实例一个请求日志拦截器
         */
        HttpLogInterceptor interceptor = new HttpLogInterceptor(new HttpLogInterceptor.Logger() {
            @Override
            public void log(String message) {
                QLog.d(message);
            }
        });

        /**
         * 下载拦截器
         */
        HttpDlInterceptor httpDlInterceptor = new HttpDlInterceptor(dlPListener);

        /**
         * 实例话OkHttpClient
         */
        OkHttpClient client = null == okHttpBuilder ? new OkHttpClient.Builder()
                .readTimeout(20000, TimeUnit.MILLISECONDS)//读取数据超时时间
                .connectTimeout(20000, TimeUnit.MILLISECONDS)//请求链接超时时间
                .addInterceptor(interceptor)//log拦截器
                .addInterceptor(httpDlInterceptor)//下载拦截器
                .build() : okHttpBuilder.build();

        return client;
    }

    /**
     * 创建默认的一个 Retrofit 实例
     *
     * @return
     */
    @Override
    public Retrofit createRetrofit() {
        return null;
    }

    /**
     * 创建默认的一个 Retrofit 实例
     *
     * @param baseUrl 主机地址，HttpUrl{@link okhttp3.HttpUrl}的字符串形式
     * @return
     * @see DlFactory#createOkHttpClient
     * 实现这个方法可以调用 createOkHttpClient
     */
    @Override
    public Retrofit createRetrofit(String baseUrl) {
        return null == retrofitBuilder ? new Retrofit.Builder()
                .client(createOkHttpClient())//设置请求
                .baseUrl(baseUrl)//设置请求的域名
                .addConverterFactory(GsonConverterFactory.create())//设置类型强转
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build() : retrofitBuilder.baseUrl(baseUrl).build();
    }

    /**
     * 创建一个 Retrofit 实例
     *
     * @param okHttpClient OkHttpClient实例{@link OkHttpClient}
     * @param baseUrl      主机地址，HttpUrl{@link okhttp3.HttpUrl}的字符串形式
     * @return
     */
    @Override
    public Retrofit createRetrofit(OkHttpClient okHttpClient, String baseUrl) {
        return null == retrofitBuilder ? new Retrofit.Builder()
                .client(okHttpClient)//设置请求
                .baseUrl(baseUrl)//设置请求的域名
                .addConverterFactory(GsonConverterFactory.create())//设置类型强转
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build() : retrofitBuilder.baseUrl(baseUrl).build();
    }
}