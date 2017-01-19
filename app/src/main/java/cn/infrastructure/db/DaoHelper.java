package cn.infrastructure.db;

import android.content.Context;

import org.greenrobot.greendao.async.AsyncSession;

import cn.infrastructure.db.dao.CrashLogDao;

public class DaoHelper {

	private static DaoHelper instance = null;

	private static final String DB_NAME = "CrashLog.db";
	private DaoSession daoSession;
	private AsyncSession asyncSession;

	public static DaoHelper getInstance(Context context) {
		if (null == instance) {
			synchronized (DaoHelper.class) {
				if (null == instance) {
					instance = new DaoHelper(context);
				}
			}
		}
		return instance;
	}

	private DaoHelper(Context context) {
		DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,
				DB_NAME, null);

		DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());

		daoSession = daoMaster.newSession();
		asyncSession = daoSession.startAsyncSession();
	}

	/**
	 * 获取CrashLogDao
	 * @return
     */
	public CrashLogDao getCrashLogDao(){
		return daoSession.getCrashLogDao();
	}


}
