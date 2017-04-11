package com.basis.lib.example.data.source.entity;

import java.io.Serializable;

/**
 * 登录置业顾问信息类
 *
 * @author Frank 2016-11-23
 */
public class OperInfoResp implements Serializable {

    public static final long serialVersionUID = 1L;

    public String operId; // 置业顾问工号
    public String operName; // 置业顾问姓名
    public String orgCode; // 所属组织
    public String orgName; // 组织名称
    public String token; // token信息
    public String estateCode; // 项目编号
    public String telno; // 手机号码
    public String callnumMask; // 来电号码  0:全部显示,1:全部掩码显示
    public String outTaskMask;//外呼号码掩码
    public String myCustomerMask;//我的客户 0:全部显示,1:全部掩码显示

    public String isCustomization;//是否定制机

}
