package frank.basis.utils.recovery.tools;


import frank.basis.utils.recovery.exception.RecoveryException;

/**
 * Created by Frank on 2016/09/28.
 */
public class DefaultHandlerUtil {

    private DefaultHandlerUtil() {
        throw new RecoveryException("Stub!");
    }

    private static Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
        Object object = Reflect.on("com.android.internal.os.RuntimeInit$UncaughtHandler").constructor().newInstance();
        return object == null ? null : (Thread.UncaughtExceptionHandler) object;
    }

    public static boolean isSystemDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        Thread.UncaughtExceptionHandler defHandler = getDefaultUncaughtExceptionHandler();
        return defHandler != null && defHandler.getClass().isInstance(handler);
    }

}
