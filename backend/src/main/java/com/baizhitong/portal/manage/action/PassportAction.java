package com.baizhitong.portal.manage.action;

import com.baizhitong.boot.pojo.base.BaseAction;
import com.baizhitong.boot.web.http.ResultVo;
import com.baizhitong.portal.manage.common.PortalManageProperties;
import com.baizhitong.portal.manage.utils.UserUtils;
import com.baizhitong.portal.manage.vo.UserVo;
import com.baizhitong.portal.manage.vo.passport.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/portal/manage")
public class PassportAction extends BaseAction {


    @Autowired
    PortalManageProperties properties;

    @RequestMapping(value = "/passport/login",method = RequestMethod.POST)
    public ResultVo login(HttpServletRequest request, @RequestBody LoginVo loginVo){

        String _username = properties.getUsername();
        String _password = properties.getPassword();
        if(_username.equals(loginVo.getUsername())&&_password.equals(loginVo.getPassword())){
            UserVo userVo = new UserVo();
            userVo.setUserName("后台管理员");
            userVo.setRoles(Arrays.asList("C","R","U","D"));
            UserUtils.saveUser(request,userVo);
            Map<String,Object> res = new HashMap<>();
            res.put("token",UserUtils.generateKey(request));
            return ResultVo.success(res);
        }else{
            return ResultVo.error("登录失败！");
        }
    }

    @RequestMapping(value = "/user/info",method = RequestMethod.GET)
    public ResultVo userInfo(HttpServletRequest request){
        UserVo userVo = (UserVo)UserUtils.getUser(request);
        return ResultVo.success(userVo);
    }


    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResultVo logout(HttpServletRequest request){
        UserUtils.removeUser(request);
        request.getSession().invalidate();
        return ResultVo.success();
    }

}
