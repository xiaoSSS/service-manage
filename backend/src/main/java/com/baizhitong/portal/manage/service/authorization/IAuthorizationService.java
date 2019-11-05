package com.baizhitong.portal.manage.service.authorization;

import com.baizhitong.boot.data.jdbc.page.Page;
import com.baizhitong.boot.web.context.Context;
import com.baizhitong.portal.manage.entity.authorization.AuthorizationEntity;
import com.baizhitong.boot.data.jdbc.page.Pageable;
import com.baizhitong.portal.manage.vo.authorization.AuthorizationVo;

import java.util.Date;

public interface IAuthorizationService {

    Page<AuthorizationEntity> queryAuthorizationPage(String appName,String appCode, String ip, Date validDate, Pageable pageable);

    Boolean saveOrUpdateAuthorization(AuthorizationVo authorizationVo, Context context);

    Boolean deleteAuthorization(Integer id);

    Boolean recoverAuthorization(Integer id);

    Boolean updateSecretKey(Integer id);
}

