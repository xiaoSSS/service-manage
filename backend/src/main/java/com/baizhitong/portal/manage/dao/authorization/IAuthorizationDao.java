package com.baizhitong.portal.manage.dao.authorization;

import com.baizhitong.boot.data.jdbc.page.Page;
import com.baizhitong.portal.manage.entity.authorization.AuthorizationEntity;
import com.baizhitong.boot.data.jdbc.page.Pageable;

import java.util.Date;

public interface IAuthorizationDao {

    /**
     *
     * @param appName
     * @param appCode
     * @param ip
     * @param validTime
     * @param pageable
     * @return
     */
    Page<AuthorizationEntity> findByNameOrIpOrValidTime(String appName,String appCode, String ip, Date validTime, Pageable pageable);

    boolean saveOne(AuthorizationEntity entity);

    int update(AuthorizationEntity entity);

    AuthorizationEntity get(Integer id);


}
