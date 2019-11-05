package com.baizhitong.portal.manage.common;

import com.baizhitong.boot.web.context.IUser;
import com.baizhitong.boot.web.context.IUserConfig;
import com.baizhitong.portal.manage.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserConfigImpl implements IUserConfig {

    public IUser getUser(HttpServletRequest request) {
        return UserUtils.getUser(request);
    }
}
