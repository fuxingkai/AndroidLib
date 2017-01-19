package cn.infrastructure.utils.recovery.exception;

/**
 * Created by Frank on 2016/09/28.
 */
public class ReflectException extends RuntimeException {
    public ReflectException() {
        super();
    }

    public ReflectException(String detailMessage) {
        super(detailMessage);
    }

    public ReflectException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ReflectException(Throwable throwable) {
        super(throwable);
    }
}
