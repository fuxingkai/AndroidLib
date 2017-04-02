package cn.infrastructure.http.listener;


/**
 * 上传进度回调类
 * Rxjava+Retrofit+OkHttp抽象工厂角色
 * Created by Frank on 2017/3/31.
 */


public interface UploadProgressListener {
    /**
     * 上传进度
     * @param currentBytesCount
     * @param totalBytesCount
     */
    void onProgress(long currentBytesCount, long totalBytesCount);
}