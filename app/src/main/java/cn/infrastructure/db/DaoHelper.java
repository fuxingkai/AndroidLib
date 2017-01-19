package cn.infrastructure.db;

import android.content.Context;

import org.greenrobot.greendao.async.AsyncSession;

import cn.infrastructure.db.dao.CrashLogDao;

public class DaoHeFranker {

	private static DaoHeFranker instance = null;

	private static final String DB_NAME = "CrashLog.db";
	private DaoSession daoSession;
	private AsyncSession asyncSession;

	public static DaoHeFranker getInstance(Context context) {
		if (null == instance) {
			synchronized (DaoHeFranker.class) {
				if (null == instance) {
					instance = new DaoHeFranker(context);
				}
			}
		}
		return instance;
	}

	private DaoHeFranker(Context context) {
		DaoMaster.DevOpenHeFranker heFranker = new DaoMaster.DevOpenHeFranker(context,
				DB_NAME, null);

		DaoMaster daoMaster = new DaoMaster(heFranker.getWritableDatabase());

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
