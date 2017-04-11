package frank.basis.http.factory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import frank.basis.http.HttpCacheInterceptor;
import frank.basis.http.HttpLogInterceptor;
import frank.basis.http.retrofit.gson.GsonConverterFactory;
import frank.basis.http.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import frank.basis.log.QLog;

import frank.basis.http.factory.RROFactory;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Rxjava+Retrofit+OkHttp缓存具体工厂角色
 * Created by Frank on 2017/3/27 0024.
 */
public class CacheFactory implements RROFactory {

    private static final String DEFAULT_CACHE_DIR = "/mnt/sdcard/okCache";
    private static final long DEFAULT_CACHE_SIZE = 1024 * 1024 * 100l;

    OkHttpClient.Builder okHttpBuilder;//okHttp的构建者
    Retrofit.Builder retrofitBuilder;//Retrofit的构建者

    public CacheFactory(){}

    public CacheFactory(OkHttpClient.Builder okHttpBuilder, Retrofit.Builder retrofitBuilder) {
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
         * 实例一个缓存拦截器
         */
        HttpCacheInterceptor cacheInterceptor = new HttpCacheInterceptor();

        File cacheDir = new File(DEFAULT_CACHE_DIR);

        if(!cacheDir.exists()){
            cacheDir.mkdirs();
        }

        Cache cache = new Cache(cacheDir,DEFAULT_CACHE_SIZE); //100Mb

        /**
         * 实例话OkHttpClient
         */
        OkHttpClient client = null == okHttpBuilder ? new OkHttpClient.Builder()
                .readTimeout(20000, TimeUnit.MILLISECONDS)//读取数据超时时间
                .connectTimeout(20000, TimeUnit.MILLISECONDS)//请求链接超时时间
                .cache(cache)
                .addInterceptor(loggingInterceptor)//log拦截器
                .addInterceptor(cacheInterceptor)//log拦截器
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
     * @see CacheFactory#createOkHttpClient
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