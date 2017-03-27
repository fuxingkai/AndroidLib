package cn.infrastructure.http;

import android.text.TextUtils;

import cn.infrastructure.entity.Response;
import cn.infrastructure.http.exception.HttpApiException;
import cn.infrastructure.http.factory.RROFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static cn.infrastructure.http.Utils.checkNotNull;

/**
 * Retrofit请求发起门面
 * Created by Frank on 2016/07/15.
 */
public class RetrofitClient {

    private String baseUrl = "";//默认服务器地址

    private Object service;//请求接口定义类
    private Retrofit retrofit;//Retrofit对象
    private OkHttpClient okHttpClient;//OkHttpClient
    private RROFactory rroFactory;//Rxjava+Retrofit+OkHttp工厂类

    private RetrofitClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.rroFactory = builder.rroFactory;
        okHttpClient = rroFactory.createOkHttpClient();
        retrofit = rroFactory.createRetrofit(okHttpClient,baseUrl);
    }

    /**
     * 获得builder实例
     * @return
     */
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * 重设baseUrl
     * @param baseUrl
     */
    private void resetUrl(String baseUrl){
        this.baseUrl = baseUrl;
    }

    /**
     * 重设Retrofit
     * @param okHttpClient
     * @param baseUrl
     */
    private void resetRetrofit(OkHttpClient okHttpClient,String baseUrl){
        retrofit = rroFactory.createRetrofit(okHttpClient,baseUrl);
    }

    /**
     * 获得Retrofit中Service实例
     * @param clz
     * @param <A>
     * @return
     */
    public <A> A getService(Class<A> clz) {
        if (service == null) {
            synchronized (Object.class) {
                if (service == null) {
                    service = retrofit.create(clz);
                }
            }
        }
        return (A) service;
    }

    /**
     * 获得Retrofit中Service实例
     * @param clz
     * @param host {@link RetrofitClient#baseUrl}
     * @param <A>
     * @return
     */
    public <A> A getService(Class<A> clz, String host) {

        if(!TextUtils.isEmpty(host)&&!host.equals(baseUrl)){
            service = null;
            resetUrl(baseUrl);
            resetRetrofit(okHttpClient,baseUrl);
        }

        if (service == null) {
            synchronized (Object.class) {
                if (service == null) {
                    service = retrofit.create(clz);
                }
            }
        }

        return (A) service;
    }

    /**
     *
     * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>，
     * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable，
     * 和调用一系列的内联操作符是一模一样的。
     *
     * @param <T>
     * @return
     */
    public <T> Observable.Transformer<Response<T>, T> applySchedulers() {
        return (Observable.Transformer<Response<T>, T>) transformer;
    }

    final Observable.Transformer transformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1() {
                        @Override
                        public Object call(Object response) {
                            return flatResponse((Response<Object>) response);
                        }
                    })
                    ;
        }
    };

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final Response<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response.isSuccess()) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(response.response_data);
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(new HttpApiException(response.ret_code, response.ret_msg));
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }

            }
        });
    }

    /**
     * RetrofitClient创造者
     */
    public static class Builder{
        private String baseUrl = "";//默认服务器地址
        private RROFactory rroFactory;//Rxjava+Retrofit+OkHttp工厂类

        public Builder(){

        }

        private Builder(RetrofitClient retrofitClient) {
            this.baseUrl = retrofitClient.baseUrl;
            this.rroFactory = retrofitClient.rroFactory;
        }

        /**
         * Set the API base baseUrl.
         * @see Builder#baseUrl
         */
        public Builder url(String baseUrl) {
            checkNotNull(baseUrl, "baseUrl == null");
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 设置RROFactory工厂
         * @param rroFactory
         * @return
         */
        public Builder addRROFactory(RROFactory rroFactory){
            checkNotNull(rroFactory, "rroFactory == null");
            this.rroFactory = rroFactory;
            return this;
        }

        /**
         * 获得RetrofitClient实例
         * @return
         */
        public RetrofitClient build() {
            if (baseUrl == null) throw new IllegalStateException("baseUrl == null");
            if (rroFactory == null) throw new IllegalStateException("rroFactory == null");
            return new RetrofitClient(this);
        }
    }

}
