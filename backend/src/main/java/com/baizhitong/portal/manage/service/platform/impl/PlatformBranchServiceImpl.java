package com.baizhitong.portal.manage.service.platform.impl;

import com.baizhitong.portal.manage.dao.platform.IPlatformBranchDao;
import com.baizhitong.portal.manage.entity.platform.PlatformBranchEntity;
import com.baizhitong.portal.manage.service.platform.IPlatformBranchService;
import com.baizhitong.portal.manage.vo.platform.PlatformBranchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatformBranchServiceImpl implements IPlatformBranchService {

    @Autowired
    IPlatformBranchDao platformBranchDao;

    public List<PlatformBranchVo> getPlatformBranchList(){
        List<PlatformBranchVo> list = new ArrayList<>();
        List<PlatformBranchEntity> entities = platformBranchDao.queryAll();
        list = entities.stream().map(platformBranchEntity->{
            PlatformBranchVo vo = new PlatformBranchVo();
            vo.setBranchCode(platformBranchEntity.getBranchCode());
            vo.setPlatformCode(platformBranchEntity.getPlatformCode());
            vo.setPlatformName(platformBranchEntity.getPlatformName());
            return vo;
        }).collect(Collectors.toList());

        return list;
    }


}
