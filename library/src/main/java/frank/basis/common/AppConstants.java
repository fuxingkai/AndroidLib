package frank.basis.common;

import java.io.File;

import frank.basis.app.BaseApplication;

/**
 * Created by Frank on 2016/7/14.
 */
public class AppConstants {
    /**
     * 成功
     */
    public static final String OK = "1";

    public static final String CACHEDIR = BaseApplication.mBaseApplication.getCacheDir().getAbsolutePath() + File.separator;

}
