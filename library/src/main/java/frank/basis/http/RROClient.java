package frank.basis.http;

import android.text.TextUtils;

import frank.basis.http.entity.Response;
import frank.basis.http.exception.HttpApiException;
import frank.basis.http.factory.DlFactory;
import frank.basis.http.factory.RROFactory;
import frank.basis.http.listener.DlPListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static frank.basis.http.RROUtil.checkNotNull;

/**
 * Retrofit请求发起门面
 * Created by Frank on 2016/07/15.
 */
public class RROClient {

    private String baseUrl = "";//默认服务器地址
    private String dlBaseUrl = "";//下载服务器地址

    private Object service;//请求接口定义类
    private Retrofit retrofit;//Retrofit对象
    private OkHttpClient okHttpClient;//下载OkHttpClient
    private RROFactory rroFactory;//Rxjava+Retrofit+OkHttp工厂类

    private RROClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.dlBaseUrl = builder.dlBaseUrl;
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
     * 重设下载dlBaseUrl
     * @param dlBaseUrl
     */
    private void resetDlUrl(String dlBaseUrl){
        this.dlBaseUrl = dlBaseUrl;
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
     * @param host {@link RROClient#baseUrl}
     * @param <A>
     * @return
     */
    public <A> A getService(Class<A> clz, String host) {

        if(!TextUtils.isEmpty(host)&&!host.equals(baseUrl)){
            service = null;
            resetUrl(host);
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
     * 获得Retrofit中Service实例
     * @param clz
     * @param <A>
     * @return
     */
    public synchronized <A> A getDlService(Class<A> clz, DlPListener dlPListener) {
        if (dlBaseUrl == null) throw new IllegalStateException("dlBaseUrl == null");
        return (A) new DlFactory(dlPListener).createRetrofit(dlBaseUrl).create(clz);
    }


    /**
     * 获得Retrofit中Service实例
     * @param clz
     * @param host {@link RROClient#dlBaseUrl}
     * @param <A>
     * @return
     */
    public <A> A getDlService(Class<A> clz, String host,DlPListener dlPListener) {

        if (dlBaseUrl == null) throw new IllegalStateException("dlBaseUrl == null");

        if(!TextUtils.isEmpty(host)&&!host.equals(dlBaseUrl)){
            resetDlUrl(host);
        }

        return (A) new DlFactory(dlPListener).createRetrofit(dlBaseUrl).create(clz);
    }

    /**
     *
     * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable，
     * 和调用一系列的内联操作符是一模一样的。
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<Response<T>, T> applySchedulers() {
        return (ObservableTransformer<Response<T>, T>) transformer;
    }

    final ObservableTransformer transformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Function() {
                        @Override
                        public Object apply(Object response) throws Exception {
                            return flatResponse((Response<Object>) response);
                        }
                    });
        }
    };

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final Response<T> response){
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                if (response.isSuccess()) {
                    if (!subscriber.isDisposed()) {
                        subscriber.onNext(response.response_data);
                    }
                } else {
                    if (!subscriber.isDisposed()) {
                        subscriber.onError(new HttpApiException(response.ret_code, response.ret_msg));
                    }
                    return;
                }

                if (!subscriber.isDisposed()) {
                    subscriber.onComplete();
                }
            }
        });
    }


    /**
     * RetrofitClient创造者
     */
    public static class Builder{
        private String baseUrl = "";//默认服务器地址
        private String dlBaseUrl = "";//下载服务器地址
        private RROFactory rroFactory;//Rxjava+Retrofit+OkHttp工厂类

        public Builder(){

        }

        private Builder(RROClient RROClient) {
            this.baseUrl = RROClient.baseUrl;
            this.dlBaseUrl = RROClient.dlBaseUrl;
            this.rroFactory = RROClient.rroFactory;
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
         * Set the API base baseUrl.
         * @see Builder#dlBaseUrl
         */
        public Builder dlUrl(String dlBaseUrl) {
            checkNotNull(dlBaseUrl, "baseUrl == null");
            this.dlBaseUrl = dlBaseUrl;
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
        public RROClient build() {
            if (baseUrl == null) throw new IllegalStateException("baseUrl == null");
            if (rroFactory == null) throw new IllegalStateException("rroFactory == null");
            return new RROClient(this);
        }
    }

}
