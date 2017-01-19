package cn.infrastructure.utils.recovery.callback;

/**
 * Created by Frank on 2016/09/28.
 */
public interface RecoveryCallback {

    void stackTrace(String stackTrace);

    void cause(String cause);

    void exception(String throwExceptionType, String throwClassName, String throwMethodName, int throwLineNumber);
}
