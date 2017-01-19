package cn.infrastructure.db.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CRASH_LOG".
*/
public class CrashLogDao extends AbstractDao<CrashLog, Long> {

    public static final String TABLENAME = "CRASH_LOG";

    /**
     * Properties of entity CrashLog.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ClientType = new Property(1, String.class, "clientType", false, "CLIENT_TYPE");
        public final static Property ClientApp = new Property(2, String.class, "clientApp", false, "CLIENT_APP");
        public final static Property PackageName = new Property(3, String.class, "packageName", false, "PACKAGE_NAME");
        public final static Property ExceptionType = new Property(4, String.class, "exceptionType", false, "EXCEPTION_TYPE");
        public final static Property NetWorkType = new Property(5, String.class, "netWorkType", false, "NET_WORK_TYPE");
        public final static Property DeviceId = new Property(6, String.class, "deviceId", false, "DEVICE_ID");
        public final static Property DeviceModel = new Property(7, String.class, "deviceModel", false, "DEVICE_MODEL");
        public final static Property OsVersion = new Property(8, String.class, "osVersion", false, "OS_VERSION");
        public final static Property AppVersionCode = new Property(9, String.class, "appVersionCode", false, "APP_VERSION_CODE");
        public final static Property AppVersionName = new Property(10, String.class, "appVersionName", false, "APP_VERSION_NAME");
        public final static Property CrashTime = new Property(11, String.class, "crashTime", false, "CRASH_TIME");
        public final static Property Time = new Property(12, String.class, "time", false, "TIME");
        public final static Property ExceptionName = new Property(13, String.class, "exceptionName", false, "EXCEPTION_NAME");
        public final static Property ExceptionStack = new Property(14, String.class, "exceptionStack", false, "EXCEPTION_STACK");
        public final static Property DisInfo = new Property(15, String.class, "disInfo", false, "DIS_INFO");
        public final static Property CrashDesc = new Property(16, String.class, "crashDesc", false, "CRASH_DESC");
        public final static Property MemoryInfo = new Property(17, String.class, "memoryInfo", false, "MEMORY_INFO");
        public final static Property IsUpload = new Property(18, String.class, "isUpload", false, "IS_UPLOAD");
    }


    public CrashLogDao(DaoConfig config) {
        super(config);
    }
    
    public CrashLogDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CRASH_LOG\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CLIENT_TYPE\" TEXT," + // 1: clientType
                "\"CLIENT_APP\" TEXT," + // 2: clientApp
                "\"PACKAGE_NAME\" TEXT," + // 3: packageName
                "\"EXCEPTION_TYPE\" TEXT," + // 4: exceptionType
                "\"NET_WORK_TYPE\" TEXT," + // 5: netWorkType
                "\"DEVICE_ID\" TEXT," + // 6: deviceId
                "\"DEVICE_MODEL\" TEXT," + // 7: deviceModel
                "\"OS_VERSION\" TEXT," + // 8: osVersion
                "\"APP_VERSION_CODE\" TEXT," + // 9: appVersionCode
                "\"APP_VERSION_NAME\" TEXT," + // 10: appVersionName
                "\"CRASH_TIME\" TEXT," + // 11: crashTime
                "\"TIME\" TEXT," + // 12: time
                "\"EXCEPTION_NAME\" TEXT," + // 13: exceptionName
                "\"EXCEPTION_STACK\" TEXT," + // 14: exceptionStack
                "\"DIS_INFO\" TEXT," + // 15: disInfo
                "\"CRASH_DESC\" TEXT," + // 16: crashDesc
                "\"MEMORY_INFO\" TEXT," + // 17: memoryInfo
                "\"IS_UPLOAD\" TEXT);"); // 18: isUpload
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CRASH_LOG\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CrashLog entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String clientType = entity.getClientType();
        if (clientType != null) {
            stmt.bindString(2, clientType);
        }
 
        String clientApp = entity.getClientApp();
        if (clientApp != null) {
            stmt.bindString(3, clientApp);
        }
 
        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(4, packageName);
        }
 
        String exceptionType = entity.getExceptionType();
        if (exceptionType != null) {
            stmt.bindString(5, exceptionType);
        }
 
        String netWorkType = entity.getNetWorkType();
        if (netWorkType != null) {
            stmt.bindString(6, netWorkType);
        }
 
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(7, deviceId);
        }
 
        String deviceModel = entity.getDeviceModel();
        if (deviceModel != null) {
            stmt.bindString(8, deviceModel);
        }
 
        String osVersion = entity.getOsVersion();
        if (osVersion != null) {
            stmt.bindString(9, osVersion);
        }
 
        String appVersionCode = entity.getAppVersionCode();
        if (appVersionCode != null) {
            stmt.bindString(10, appVersionCode);
        }
 
        String appVersionName = entity.getAppVersionName();
        if (appVersionName != null) {
            stmt.bindString(11, appVersionName);
        }
 
        String crashTime = entity.getCrashTime();
        if (crashTime != null) {
            stmt.bindString(12, crashTime);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(13, time);
        }
 
        String exceptionName = entity.getExceptionName();
        if (exceptionName != null) {
            stmt.bindString(14, exceptionName);
        }
 
        String exceptionStack = entity.getExceptionStack();
        if (exceptionStack != null) {
            stmt.bindString(15, exceptionStack);
        }
 
        String disInfo = entity.getDisInfo();
        if (disInfo != null) {
            stmt.bindString(16, disInfo);
        }
 
        String crashDesc = entity.getCrashDesc();
        if (crashDesc != null) {
            stmt.bindString(17, crashDesc);
        }
 
        String memoryInfo = entity.getMemoryInfo();
        if (memoryInfo != null) {
            stmt.bindString(18, memoryInfo);
        }
 
        String isUpload = entity.getIsUpload();
        if (isUpload != null) {
            stmt.bindString(19, isUpload);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CrashLog entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String clientType = entity.getClientType();
        if (clientType != null) {
            stmt.bindString(2, clientType);
        }
 
        String clientApp = entity.getClientApp();
        if (clientApp != null) {
            stmt.bindString(3, clientApp);
        }
 
        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(4, packageName);
        }
 
        String exceptionType = entity.getExceptionType();
        if (exceptionType != null) {
            stmt.bindString(5, exceptionType);
        }
 
        String netWorkType = entity.getNetWorkType();
        if (netWorkType != null) {
            stmt.bindString(6, netWorkType);
        }
 
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(7, deviceId);
        }
 
        String deviceModel = entity.getDeviceModel();
        if (deviceModel != null) {
            stmt.bindString(8, deviceModel);
        }
 
        String osVersion = entity.getOsVersion();
        if (osVersion != null) {
            stmt.bindString(9, osVersion);
        }
 
        String appVersionCode = entity.getAppVersionCode();
        if (appVersionCode != null) {
            stmt.bindString(10, appVersionCode);
        }
 
        String appVersionName = entity.getAppVersionName();
        if (appVersionName != null) {
            stmt.bindString(11, appVersionName);
        }
 
        String crashTime = entity.getCrashTime();
        if (crashTime != null) {
            stmt.bindString(12, crashTime);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(13, time);
        }
 
        String exceptionName = entity.getExceptionName();
        if (exceptionName != null) {
            stmt.bindString(14, exceptionName);
        }
 
        String exceptionStack = entity.getExceptionStack();
        if (exceptionStack != null) {
            stmt.bindString(15, exceptionStack);
        }
 
        String disInfo = entity.getDisInfo();
        if (disInfo != null) {
            stmt.bindString(16, disInfo);
        }
 
        String crashDesc = entity.getCrashDesc();
        if (crashDesc != null) {
            stmt.bindString(17, crashDesc);
        }
 
        String memoryInfo = entity.getMemoryInfo();
        if (memoryInfo != null) {
            stmt.bindString(18, memoryInfo);
        }
 
        String isUpload = entity.getIsUpload();
        if (isUpload != null) {
            stmt.bindString(19, isUpload);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CrashLog readEntity(Cursor cursor, int offset) {
        CrashLog entity = new CrashLog( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // clientType
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // clientApp
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // packageName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // exceptionType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // netWorkType
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // deviceId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // deviceModel
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // osVersion
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // appVersionCode
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // appVersionName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // crashTime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // time
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // exceptionName
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // exceptionStack
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // disInfo
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // crashDesc
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // memoryInfo
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18) // isUpload
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CrashLog entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setClientType(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setClientApp(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPackageName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setExceptionType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNetWorkType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDeviceId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDeviceModel(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setOsVersion(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAppVersionCode(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setAppVersionName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCrashTime(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setTime(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setExceptionName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setExceptionStack(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setDisInfo(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setCrashDesc(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setMemoryInfo(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setIsUpload(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CrashLog entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CrashLog entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CrashLog entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
