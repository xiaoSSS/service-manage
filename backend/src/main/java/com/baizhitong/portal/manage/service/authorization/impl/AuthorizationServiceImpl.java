package com.baizhitong.portal.manage.service.authorization.impl;

import com.baizhitong.boot.data.jdbc.page.Page;
import com.baizhitong.boot.data.jdbc.page.Pageable;
import com.baizhitong.boot.util.codec.Md5Utils;
import com.baizhitong.boot.util.lang.DateUtils;
import com.baizhitong.boot.util.lang.RandomNumUtils;
import com.baizhitong.boot.web.context.Context;
import com.baizhitong.portal.manage.dao.authorization.IAuthorizationDao;
import com.baizhitong.portal.manage.entity.authorization.AuthorizationEntity;
import com.baizhitong.portal.manage.service.authorization.IAuthorizationService;
import com.baizhitong.portal.manage.vo.authorization.AuthorizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
public class AuthorizationServiceImpl implements IAuthorizationService {

    @Autowired
    IAuthorizationDao authorizationDao;

    @Override
    public Page<AuthorizationEntity> queryAuthorizationPage(String appName,String appCode,String ip, Date validDate, Pageable pageable) {
        return authorizationDao.findByNameOrIpOrValidTime(appName,appCode,ip,validDate,pageable);
    }

    @Override
    public Boolean saveOrUpdateAuthorization(AuthorizationVo authorizationVo, Context context) {
        AuthorizationEntity authorization = new AuthorizationEntity();
        authorization.setAppName(authorizationVo.getAppName());
        authorization.setIp(authorizationVo.getIp());
        authorization.setValidDate(authorizationVo.getValidDate());

        if(ObjectUtils.isEmpty(authorizationVo.getId())){

            String appCode = authorizationVo.getAppCode();
            String appKey = RandomNumUtils.instance(12).getString();
            String validDate = DateUtils.formatDate(authorizationVo.getValidDate());
            String appSecret= Md5Utils.md5(appCode+appKey+validDate);
            authorization.setAppCode(appCode);
            authorization.setAppKey(appKey);
            authorization.setAppSecret(appSecret);
            authorization.setCreatePgm("saveOrUpdate");
            authorization.setStatus(1);
            authorization.setCreateTime(new Date());
            authorization.setCreatorIP(context.getSystem().getRemoteAddr());
            return authorizationDao.saveOne(authorization);
        }else{
            authorization.setId(authorizationVo.getId());
            return authorizationDao.update(authorization)>0;
        }
    }

    @Override
    public Boolean deleteAuthorization(Integer id) {
        AuthorizationEntity authorization = new AuthorizationEntity();
        authorization.setId(id);
        authorization.setStatus(0);
        return authorizationDao.update(authorization)>0;
    }

    @Override
    public Boolean recoverAuthorization(Integer id) {
        AuthorizationEntity authorization = new AuthorizationEntity();
        authorization.setId(id);
        authorization.setStatus(1);
        return authorizationDao.update(authorization)>0;
    }

    @Override
    public Boolean updateSecretKey(Integer id) {
        AuthorizationEntity authorization = authorizationDao.get(id);
        updateSecret(authorization);
        return authorizationDao.update(authorization)>0;
    }

    private void updateSecret(AuthorizationEntity authorization){
        String appCode = authorization.getAppCode();
        String appKey = RandomNumUtils.instance(12).getString();
        String validDate = DateUtils.formatDate(authorization.getValidDate());
        String appSecret= Md5Utils.md5(appCode+appKey+validDate);
        authorization.setAppKey(appKey);
        authorization.setAppSecret(appSecret);
    }

}
