package frank.basis.app;

import android.support.multidex.MultiDexApplication;

import com.alipay.euler.andfix.patch.PatchManager;

import frank.basis.log.QLog;
import frank.basis.utils.DeviceUtil;
import frank.basis.utils.ToastUtil;

/**
 * 所有应用启动入口（Application类）的基类，与业务逻辑无关
 *
 * @author Frank 2016-7-2
 */
public class BaseApplication extends MultiDexApplication {

    public static BaseApplication mBaseApplication;
    protected PatchManager patchManager;
    protected String APATCH_DIR = "/mnt/sdcar";

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;

        ToastUtil.setContext(this);
        QLog.init();
    }

    private void initPatchManager() {
        patchManager = new PatchManager(this);
        patchManager.init(DeviceUtil.getVersionName(this));
        patchManager.loadPatch();
    }

}
