package frank.basis.utils.recovery.core;

/**
 * 崩溃信息类
 *
 * @author Frank 2016-8-6
 */
public class CrashLogVo {

    public String logId;//自增id，主键
    public int fixStatus;//是否已经解决了.1，解决了；0，未解决
    public String clientType;//标记Crash发生在手机还是话机
    public String clientApp;//标记Crash发生在哪个App，这里是JuMobile
    public String packageName;//应用包名
    public String exceptionType;//异常类型，如：crash表示崩溃了，catch表示被try-catch捕获到了
    public String netWorkType;//网络类型是否为wifi，如：is wifi，not wifi两种取值
    public String deviceId;//手机或话机设备id，如：869634020848194（手机）；P2K1512180002345（话机）
    public String deviceModel;//手机型号，如：Mi-4c
    public String osVersion;//操作系统版本，如：5.1.1
    public String appVersionCode;//app的versionCode，如：1
    public String appVersionName;//app的versionName，如：1.0
    public String crashTime;//Crash在客户端发生的时间
    public String time;//该条记录插入数据库表的时间，改字段由后台生成.
    public String exceptionName;//Crash名称
    public String exceptionStack;//Crash详细信息
    public String disInfo;//复制exceptionStack的值，用正则替换为另外的值用于去重统计
    public String crashDesc;//用来表明Crash是哪个类别
    public String memoryInfo;//Crash发生时的内存使用情况，如：Memory info:657203200,app holds:230400000,Low Memory:false

    public int isUpload;//该条记录是否已经上传到服务器，1代表已上传，0代表未上传.
}

/**
 * SET FOREIGN_KEY_CHECKS = 0;
 * <p>
 * DROP TABLE IF EXISTS crash_log;
 * CREATE TABLE crash_log(
 * logId BIGINT(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
 * fixStatus INT DEFAULT 0 COMMENT '是否已经解决了.1，解决了；0，未解决',
 * clientType VARCHAR(16) DEFAULT NULL COMMENT '标记Crash发生在手机还是话机',
 * clientApp VARCHAR(16) DEFAULT NULL COMMENT '标记Crash发生在哪个App',
 * packageName VARCHAR(32) DEFAULT NULL COMMENT '应用包名',
 * exceptionType VARCHAR(16) DEFAULT 'crash' COMMENT '异常类型，如：crash表示崩溃了，catch表示被try-catch捕获到了',
 * netWorkType VARCHAR(16) DEFAULT NULL COMMENT '网络类型是否为wifi，如：is wifi，not wifi两种取值',
 * deviceId VARCHAR(32) DEFAULT NULL COMMENT '手机或话机设备id',
 * deviceModel VARCHAR(32) DEFAULT NULL COMMENT '手机型号，如：Mi-4c',
 * osVersion VARCHAR(16) COMMENT '操作系统版本，如：5.1.1',
 * appVersionCode VARCHAR(16) DEFAULT NULL COMMENT 'app的versionCode',
 * appVersionName VARCHAR(16) DEFAULT NULL COMMENT 'app的versionName',
 * crashTime VARCHAR(32) DEFAULT NULL COMMENT 'Crash发生时间',
 * time VARCHAR(32) DEFAULT NULL COMMENT '该条记录插入数据库表的时间，改字段由后台生成',
 * exceptionName VARCHAR(32) DEFAULT NULL COMMENT 'Crash名称',
 * exceptionStack TEXT DEFAULT NULL COMMENT 'Crash详细信息',
 * disInfo TEXT DEFAULT NULL COMMENT '复制exceptionStack的值，用正则替换为另外的值用于去重统计',
 * crashDesc VARCHAR(100) DEFAULT NULL COMMENT '用来表明Crash是哪个类别',
 * memoryInfo TEXT DEFAULT NULL COMMENT 'Crash发生时的内存使用情况',
 * PRIMARY KEY (logId)
 * )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 * <p>
 * SET FOREIGN_KEY_CHECKS = 1;
 */