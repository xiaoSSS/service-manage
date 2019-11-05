package com.baizhitong.portal.manage.dao.platform.impl;

import com.baizhitong.boot.data.jdbc.base.BaseDao;
import com.baizhitong.portal.manage.dao.platform.IPlatformBranchDao;
import com.baizhitong.portal.manage.entity.platform.PlatformBranchEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlatformBranchDaoImpl extends BaseDao<PlatformBranchEntity,Integer> implements IPlatformBranchDao {

    @Override
    public List<PlatformBranchEntity> queryAll() {
        return super.getAll();
    }
}
