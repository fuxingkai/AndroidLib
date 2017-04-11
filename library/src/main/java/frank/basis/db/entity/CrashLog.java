//package frank.basis.db.entity;
//
//import org.greenrobot.greendao.annotation.*;
//
//// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
//
///**
// * 崩溃日志类
// * @author Frank 2016-08-25
// */
//@Entity
//public class CrashLog implements java.io.Serializable {
//
//    /**
//     * 自增id
//     */
//    @Id
//    private Long id;
//    /**
//     * 标记Crash发生在手机还是话机
//     */
//    private String clientType;
//    /**
//     * 标记Crash发生在哪个App，这里是JuMobile
//     */
//    private String clientApp;
//    /**
//     * 应用包名
//     */
//    private String packageName;
//    /**
//     * 异常类型，如：crash表示崩溃了，catch表示被try-catch捕获到了
//     */
//    private String exceptionType;
//    /**
//     * 网络类型是否为wifi，如：is wifi，not wifi两种取值
//     */
//    private String netWorkType;
//    /**
//     * 手机或话机设备id
//     */
//    private String deviceId;
//    /**
//     * 手机型号
//     */
//    private String deviceModel;
//    /**
//     * 操作系统版本
//     */
//    private String osVersion;
//    /**
//     * app的versionCode
//     */
//    private String appVersionCode;
//    /**
//     * app的versionName
//     */
//    private String appVersionName;
//    /**
//     * Crash在客户端发生的时间
//     */
//    private String crashTime;
//    /**
//     * 该条记录插入数据库表的时间，改字段由后台生成
//     */
//    private String time;
//    /**
//     * Crash名称
//     */
//    private String exceptionName;
//    /**
//     * Crash详细信息
//     */
//    private String exceptionStack;
//    /**
//     * 复制exceptionStack的值，用正则替换为另外的值用于去重统计
//     */
//    private String disInfo;
//    /**
//     * 用来表明Crash是哪个类别
//     */
//    private String crashDesc;
//    /**
//     * Crash发生时的内存使用情况
//     */
//    private String memoryInfo;
//    /**
//     * 该条记录是否已经上传到服务器，1代表已上传，0代表未上传
//     */
//    private String isUpload;
//
//    @Generated(hash = 363358745)
//    public CrashLog() {
//    }
//
//    public CrashLog(Long id) {
//        this.id = id;
//    }
//
//    @Generated(hash = 400247661)
//    public CrashLog(Long id, String clientType, String clientApp, String packageName, String exceptionType, String netWorkType, String deviceId, String deviceModel, String osVersion, String appVersionCode, String appVersionName, String crashTime, String time, String exceptionName, String exceptionStack, String disInfo, String crashDesc, String memoryInfo, String isUpload) {
//        this.id = id;
//        this.clientType = clientType;
//        this.clientApp = clientApp;
//        this.packageName = packageName;
//        this.exceptionType = exceptionType;
//        this.netWorkType = netWorkType;
//        this.deviceId = deviceId;
//        this.deviceModel = deviceModel;
//        this.osVersion = osVersion;
//        this.appVersionCode = appVersionCode;
//        this.appVersionName = appVersionName;
//        this.crashTime = crashTime;
//        this.time = time;
//        this.exceptionName = exceptionName;
//        this.exceptionStack = exceptionStack;
//        this.disInfo = disInfo;
//        this.crashDesc = crashDesc;
//        this.memoryInfo = memoryInfo;
//        this.isUpload = isUpload;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getClientType() {
//        return clientType;
//    }
//
//    public void setClientType(String clientType) {
//        this.clientType = clientType;
//    }
//
//    public String getClientApp() {
//        return clientApp;
//    }
//
//    public void setClientApp(String clientApp) {
//        this.clientApp = clientApp;
//    }
//
//    public String getPackageName() {
//        return packageName;
//    }
//
//    public void setPackageName(String packageName) {
//        this.packageName = packageName;
//    }
//
//    public String getExceptionType() {
//        return exceptionType;
//    }
//
//    public void setExceptionType(String exceptionType) {
//        this.exceptionType = exceptionType;
//    }
//
//    public String getNetWorkType() {
//        return netWorkType;
//    }
//
//    public void setNetWorkType(String netWorkType) {
//        this.netWorkType = netWorkType;
//    }
//
//    public String getDeviceId() {
//        return deviceId;
//    }
//
//    public void setDeviceId(String deviceId) {
//        this.deviceId = deviceId;
//    }
//
//    public String getDeviceModel() {
//        return deviceModel;
//    }
//
//    public void setDeviceModel(String deviceModel) {
//        this.deviceModel = deviceModel;
//    }
//
//    public String getOsVersion() {
//        return osVersion;
//    }
//
//    public void setOsVersion(String osVersion) {
//        this.osVersion = osVersion;
//    }
//
//    public String getAppVersionCode() {
//        return appVersionCode;
//    }
//
//    public void setAppVersionCode(String appVersionCode) {
//        this.appVersionCode = appVersionCode;
//    }
//
//    public String getAppVersionName() {
//        return appVersionName;
//    }
//
//    public void setAppVersionName(String appVersionName) {
//        this.appVersionName = appVersionName;
//    }
//
//    public String getCrashTime() {
//        return crashTime;
//    }
//
//    public void setCrashTime(String crashTime) {
//        this.crashTime = crashTime;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getExceptionName() {
//        return exceptionName;
//    }
//
//    public void setExceptionName(String exceptionName) {
//        this.exceptionName = exceptionName;
//    }
//
//    public String getExceptionStack() {
//        return exceptionStack;
//    }
//
//    public void setExceptionStack(String exceptionStack) {
//        this.exceptionStack = exceptionStack;
//    }
//
//    public String getDisInfo() {
//        return disInfo;
//    }
//
//    public void setDisInfo(String disInfo) {
//        this.disInfo = disInfo;
//    }
//
//    public String getCrashDesc() {
//        return crashDesc;
//    }
//
//    public void setCrashDesc(String crashDesc) {
//        this.crashDesc = crashDesc;
//    }
//
//    public String getMemoryInfo() {
//        return memoryInfo;
//    }
//
//    public void setMemoryInfo(String memoryInfo) {
//        this.memoryInfo = memoryInfo;
//    }
//
//    public String getIsUpload() {
//        return isUpload;
//    }
//
//    public void setIsUpload(String isUpload) {
//        this.isUpload = isUpload;
//    }
//
//}