package com.baizhitong.portal.manage.vo;

import com.baizhitong.boot.web.context.IUser;

import java.util.List;

public class UserVo implements IUser {

    /** 用户名称 */
    private String userName;
    /** 角色 */
    private List<String> roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
