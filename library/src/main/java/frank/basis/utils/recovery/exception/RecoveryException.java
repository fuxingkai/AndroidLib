package frank.basis.utils.recovery.exception;

/**
 * Created by Frank on 2016/09/28.
 */
public class RecoveryException extends RuntimeException {
    public RecoveryException(String message) {
        super(message);
    }

    public RecoveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
