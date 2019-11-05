package com.baizhitong.portal.manage.entity.authorization;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "share_authorization")
public class AuthorizationEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String appCode;

    private String appName;

    private String appKey;

    private String appSecret;

    private String ip;

    private String authority;

    private Integer status;

    private Date validDate;

    private Date createTime;

    private String creatorIP;

    private String createPgm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorIP() {
        return creatorIP;
    }

    public void setCreatorIP(String creatorIP) {
        this.creatorIP = creatorIP;
    }

    public String getCreatePgm() {
        return createPgm;
    }

    public void setCreatePgm(String createPgm) {
        this.createPgm = createPgm;
    }
}
