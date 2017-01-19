package cn.infrastructure.lib.example.data.source.entity;


import cn.jufuns.aptpreferences.AptField;
import cn.jufuns.aptpreferences.AptPreferences;

/**
 * Created by linpei on 2016/8/31.
 */
@AptPreferences
public class User {

    private String userName;

    private String userId;

    @AptField(global = false)
    private String autoLogin;

    @AptField(save = false)
    private String userPwd;

    private SettingInfo settingInfo;

    @AptField(preferences = true,commit = true)
    private PhoneINfo phoneINfo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(String autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public SettingInfo getSettingInfo() {
        return settingInfo;
    }

    public void setSettingInfo(SettingInfo settingInfo) {
        this.settingInfo = settingInfo;
    }

    public PhoneINfo getPhoneINfo() {
        return phoneINfo;
    }

    public void setPhoneINfo(PhoneINfo phoneINfo) {
        this.phoneINfo = phoneINfo;
    }
}
