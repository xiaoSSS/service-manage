package com.baizhitong.portal.manage.common;

import com.baizhitong.boot.pojo.consts.enums.ServerCode;
import com.baizhitong.boot.util.lang.ObjectUtils;
import com.baizhitong.boot.util.lang.StringUtils;
import com.baizhitong.boot.util.serialization.JsonUtils;
import com.baizhitong.boot.web.context.IUser;
import com.baizhitong.boot.web.http.ResultVo;
import com.baizhitong.boot.web.interceptor.BztHandlerInterceptorAdapter;
import com.baizhitong.portal.manage.utils.UserUtils;
import org.apache.catalina.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MvcInterceptor extends BztHandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(MvcInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = UserUtils.generateKey(request);
        if(StringUtils.isEmpty(key)){
            String sessionId = UUID.randomUUID().toString();
            response.setHeader(UserUtils.BZT_CITY_SESSION_KEY,sessionId);
            request.setAttribute(UserUtils.BZT_CITY_SESSION_KEY,sessionId);
        }
        //无需登录就能访问的接口
        String uri = request.getRequestURI();
        if(uri.contains("/portal/manage/passport/login")) {
            return true;
        }
        if(uri.contains("/portal/manage")) {
            return true;
        }
        IUser user = UserUtils.getUser(request);
        if(ObjectUtils.isNotNull(user)){   //是否登录了
            return true;
        }
        String isAjax=request.getParameter("isAjax");
        if("true".equals(isAjax)){    //ajax请求
            PrintWriter out = null;
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=utf-8");
                out = response.getWriter();
                Map data=new HashMap();
                data.put("loginUrl", "");
                ResultVo result = ResultVo.error(ServerCode.ERROR.code(),"未登录，被拦截。无数据返回",data);
                out.print(JsonUtils.toJson(result));
                out.flush();

                logger.info("未登录");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }else{
            //response.sendRedirect("");
        }
        return false;
    }




    @Autowired
    ApplicationContext applicationContext;

}
