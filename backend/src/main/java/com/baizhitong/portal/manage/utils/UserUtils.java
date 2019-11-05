package com.baizhitong.portal.manage.utils;

import com.baizhitong.boot.util.lang.StringUtils;
import com.baizhitong.boot.util.serialization.JsonUtils;
import com.baizhitong.boot.web.context.IUser;
import com.baizhitong.boot.web.util.http.CookiesUtil;
import com.baizhitong.portal.manage.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Component
public class UserUtils {

    public static String BZT_CITY_SESSION_KEY = "_portalmanageid4city_";

    public static final String REDIS_KEY_PREFIX = "manage:user4portal:info:";

    public static IUser getUser(HttpServletRequest request){
        String key = REDIS_KEY_PREFIX+generateKey(request);
        String userInfo = redisTemplate.opsForValue().get(key);
        UserVo userVo = JsonUtils.fromJson(userInfo, UserVo.class);
        return userVo;
    }

    public static String generateKey(HttpServletRequest request){
        String key = request.getHeader(BZT_CITY_SESSION_KEY);
        if(StringUtils.isEmpty(key)){
            key = CookiesUtil.loadCookie(BZT_CITY_SESSION_KEY,request);
        }
        if(StringUtils.isEmpty(key)){
            key = (String)request.getAttribute(BZT_CITY_SESSION_KEY);
        }
        return key;
    }

    public static void saveUser(HttpServletRequest request, IUser user){
        String key = generateKey(request);
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX+key, JsonUtils.toJson(user));
    }

    public static void removeUser(HttpServletRequest request){
        String key = generateKey(request);
        redisTemplate.delete(REDIS_KEY_PREFIX+key);
    }


    @Autowired
    StringRedisTemplate _redisTemplate;

    private static StringRedisTemplate redisTemplate;

    @PostConstruct
    void postConstruct() {
        redisTemplate = _redisTemplate;
    }

}
