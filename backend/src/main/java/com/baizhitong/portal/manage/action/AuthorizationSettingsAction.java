package com.baizhitong.portal.manage.action;

import com.baizhitong.boot.data.jdbc.page.Page;
import com.baizhitong.boot.data.jdbc.page.Pageable;
import com.baizhitong.boot.util.lang.ObjectUtils;
import com.baizhitong.boot.web.context.Context;
import com.baizhitong.boot.web.http.ResultVo;
import com.baizhitong.portal.manage.service.authorization.IAuthorizationService;
import com.baizhitong.portal.manage.vo.authorization.AuthorizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/portal/manage")
public class AuthorizationSettingsAction {

    @Autowired
    IAuthorizationService authorizationService;

    @RequestMapping(value = "/authorization/settings",method = RequestMethod.POST)
    public ResultVo authorizationSettings(@RequestBody AuthorizationVo authorizationVo){

        String appName = authorizationVo.getAppName();
        String appCode = authorizationVo.getAppCode();
        String ip = authorizationVo.getIp();
        Date validDate = authorizationVo.getValidDate();
        if(ObjectUtils.isNull(validDate)){
            validDate = new Date();
        }
        Long pageNo = authorizationVo.getPageNo();
        Long pageSize = authorizationVo.getPageSize();
        if(ObjectUtils.isNull(pageNo)){
            pageNo = 1l ;
        }
        if(ObjectUtils.isNull(pageSize)){
            pageSize = 10l;
        }
        Pageable pageable = Pageable.pageable(pageNo,pageSize);
        Page res =  authorizationService.queryAuthorizationPage(appName,appCode,ip,validDate,pageable);
        return ResultVo.success(res);
    }

    @RequestMapping(value = "/authorization/save",method = RequestMethod.POST)
    public ResultVo saveAuthorizationSetting(Context context , @RequestBody AuthorizationVo authorizationVo){
        if(authorizationService.saveOrUpdateAuthorization(authorizationVo,context)){
            return ResultVo.success();
        }else{
            return ResultVo.error("操作失败");
        }
    }

    @RequestMapping(value = "/authorization/delete",method = RequestMethod.POST)
    public ResultVo deleteAuthorization(Integer id){
        if(authorizationService.deleteAuthorization(id)){
            return ResultVo.success();
        }else{
            return ResultVo.error("操作失败");
        }
    }

    @RequestMapping(value = "/authorization/recover",method = RequestMethod.POST)
    public ResultVo recoverAuthorization(Integer id){
        if(authorizationService.recoverAuthorization(id)){
            return ResultVo.success();
        }else{
            return ResultVo.error("操作失败");
        }
    }

    @RequestMapping(value = "/authorization/update/secret",method = RequestMethod.POST)
    public ResultVo updateSecretKey(Integer id){
        if(authorizationService.updateSecretKey(id)){
            return ResultVo.success();
        }else{
            return ResultVo.error("操作失败");
        }
    }

}
