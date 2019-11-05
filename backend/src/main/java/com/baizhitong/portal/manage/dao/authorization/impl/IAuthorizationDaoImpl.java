package com.baizhitong.portal.manage.dao.authorization.impl;

import com.baizhitong.boot.data.jdbc.base.BaseDao;
import com.baizhitong.boot.data.jdbc.page.Page;
import com.baizhitong.boot.data.jdbc.page.Pageable;
import com.baizhitong.boot.util.lang.ObjectUtils;
import com.baizhitong.boot.util.lang.StringUtils;
import com.baizhitong.portal.manage.dao.authorization.IAuthorizationDao;
import com.baizhitong.portal.manage.entity.authorization.AuthorizationEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class IAuthorizationDaoImpl extends BaseDao<AuthorizationEntity, Integer> implements IAuthorizationDao {


    @Override
    public Page<AuthorizationEntity> findByNameOrIpOrValidTime(String appName,String appCode,String ip, Date validTime, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        Map<String,Object> param = new HashMap<>();

        sql.append("  SELECT  ");
        sql.append("  	id,  ");
        sql.append("  	appCode,  ");
        sql.append("  	appName,  ");
        sql.append("  	appKey,  ");
        sql.append("  	appSecret,  ");
        sql.append("  	ip,  ");
        sql.append("  	authority,  ");
        sql.append("  	validDate,  ");
        sql.append("  	status  ");
        sql.append("  FROM  ");
        sql.append("  	share_authorization  ");
        sql.append("  WHERE  ");
        sql.append("    1 = 1  ");
        if(StringUtils.isNotEmpty(appName)){
            sql.append("  AND appName LIKE :appName ");
            param.put("appName",appName);
        }
        if(StringUtils.isNotEmpty(appCode)){
            sql.append("  AND appCode = :appCode ");
            param.put("appCode",appCode);
        }
        if(ObjectUtils.isNotNull(validTime)) {
            sql.append("  AND validDate > :validTime ");
            param.put("validTime",validTime);
        }
        if(StringUtils.isNotEmpty(ip)) {
            sql.append("  AND ip = :ip ");
            param.put("ip",ip);
        }
        if(ObjectUtils.isNotNull(validTime)) {
            sql.append("  AND validDate > :validTime ");
            param.put("validTime",validTime);
        }

        return super.queryPageBySql(sql.toString(),param,pageable);
    }

    public boolean saveOne(AuthorizationEntity entity){
        return super.saveOne(entity);
    }

    public int update(AuthorizationEntity entity){
        return super.update(entity);
    }
}
