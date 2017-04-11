package frank.basis.http.listener;


/**
 * 下载进去回调
 * Created by Frank on 2017/4/2.
 */
public interface DlPListener {
    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    void update(long read, long count, boolean done);
}
