package frank.basis.http.factory;

import java.util.concurrent.TimeUnit;

import frank.basis.http.HttpAuthInterceptor;
import frank.basis.http.HttpLogInterceptor;
import frank.basis.http.retrofit.gson.GsonConverterFactory;
import frank.basis.http.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import frank.basis.log.QLog;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Rxjava+Retrofit+OkHttp鉴权具体工厂角色
 * Created by Frank on 2017/3/27 0024.
 */
public class AuthFactory implements RROFactory {

    OkHttpClient.Builder okHttpBuilder;//okHttp的构建者
    Retrofit.Builder retrofitBuilder;//Retrofit的构建者

    public AuthFactory(){}

    public AuthFactory(OkHttpClient.Builder okHttpBuilder, Retrofit.Builder retrofitBuilder) {
        this.okHttpBuilder = okHttpBuilder;
        this.retrofitBuilder = retrofitBuilder;
    }

    /**
     * 获得OkHttpClient实例
     * @return
     */
    @Override
    public OkHttpClient createOkHttpClient() {
        /**
         * 实例一个请求日志拦截器
         */
        HttpLogInterceptor loggingInterceptor = new HttpLogInterceptor(new HttpLogInterceptor.Logger() {
            @Override
            public void log(String message) {
                QLog.d(message);
            }
        });

        /**
         * 实例一个鉴权拦截器
         */
        HttpAuthInterceptor httpAuthInterceptor = new HttpAuthInterceptor();

        /**
         * 实例话OkHttpClient
         */
        OkHttpClient client = null == okHttpBuilder ? new OkHttpClient.Builder()
                .readTimeout(20000, TimeUnit.MILLISECONDS)//读取数据超时时间
                .connectTimeout(20000, TimeUnit.MILLISECONDS)//请求链接超时时间
                .addInterceptor(loggingInterceptor)//log拦截器
                .addNetworkInterceptor(httpAuthInterceptor)//鉴权拦截器
                .build() : okHttpBuilder.build();
        return client;
    }

    /**
     * 创建默认的一个 Retrofit 实例
     * @return
     */
    @Override
    public Retrofit createRetrofit() {
        return null;
    }

    /**
     * 创建默认的一个 Retrofit 实例
     * @see DefaultFactory#createOkHttpClient
     * 实现这个方法可以调用 createOkHttpClient
     * @param baseUrl 主机地址，HttpUrl{@link okhttp3.HttpUrl}的字符串形式
     * @return
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
     * @param okHttpClient OkHttpClient实例{@link OkHttpClient}
     * @param baseUrl 主机地址，HttpUrl{@link okhttp3.HttpUrl}的字符串形式
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