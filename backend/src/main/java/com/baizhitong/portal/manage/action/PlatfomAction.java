package com.baizhitong.portal.manage.action;

import com.baizhitong.boot.web.http.ResultVo;
import com.baizhitong.portal.manage.service.platform.IPlatformBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal/manage")
public class PlatfomAction {

    @Autowired
    private IPlatformBranchService platformBranchService;

    @RequestMapping(value = "/platform/branch/list",method = RequestMethod.GET)
    public ResultVo getPlatformBranchInfo(){
        return ResultVo.success(platformBranchService.getPlatformBranchList());
    }
}
