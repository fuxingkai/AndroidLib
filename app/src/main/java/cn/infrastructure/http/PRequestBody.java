package cn.infrastructure.http;

import java.io.IOException;

import cn.infrastructure.http.listener.UlPListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 自定义上传进度类RequestBody
 * Created by Frank on 2017/3/31.
 */
public class PRequestBody extends RequestBody {
    /**
     * 实际的待包装请求体
     */
    private final RequestBody requestBody;

    /**
     * 进度回调接口
     */
    private final UlPListener progressListener;

    /**
     * 包装完成的BufferedSink
     */
    private BufferedSink bufferedSink;

    public PRequestBody(RequestBody requestBody, UlPListener progressListener) {
        this.requestBody = requestBody;
        this.progressListener = progressListener;
    }
    /**
     * 重写调用实际的响应体的contentType
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    /**
     * 重写进行写入
     * @param sink BufferedSink
     * @throws IOException 异常
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        /**
         * 根据当前执行的是上传数据还是计算请求体的md5来判读是否要开始上传进度的监听
         * 当是计算md5的时候传进来的sink对象是Buffer对象的实例
         *  @see cn.infrastructure.http.encryp.AuthUtil#get128MD5ToBase64(RequestBody)
         * 当是真正的上传数据的时候读取请求体的是RealBufferedSink
         * @see okio.RealBufferedSink
         */
        String sinkClassName = sink.getClass().getName();

        if(sinkClassName.equals("okio.RealBufferedSink")){
            if (null == bufferedSink) {
                bufferedSink = Okio.buffer(sink(sink));
            }
            requestBody.writeTo(bufferedSink);

            //必须调用flush，否则最后一部分数据可能不会被写入
            bufferedSink.flush();
            return;
        }
        requestBody.writeTo(sink);
    }

    /**
     * 写入，回调进度接口
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long writtenBytesCount = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long totalBytesCount = 0L;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);

                //增加当前写入的字节数
                writtenBytesCount += byteCount;

                //获得contentLength的值，后续不再调用
                if (totalBytesCount == 0) {
                    totalBytesCount = contentLength();
                }

                Observable.just(writtenBytesCount)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                progressListener.onProgress(writtenBytesCount, totalBytesCount);
                            }
                        });
            }
        };
    }
}