package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.util.ThreadLocalUtil;
import com.jt.utils.CookieUtil;
import com.jt.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = CookieUtil.getCookieValue(request, "JT_TICKET");
        if(!StringUtils.isEmpty(ticket)){
            String userJson = jedisCluster.get(ticket);
            if(!StringUtils.isEmpty(userJson)){
                User user = ObjectMapperUtil.toObj(userJson, User.class);
                //存入本地线程
                ThreadLocalUtil.setUser(user);
                return true;
            }else{
                CookieUtil.deleteCookie(response,"JT_TICKET","jt.com","/");
            }
        }
        response.sendRedirect("/user/login.html");
        return false;//false表示拦截
    }

    //防止内存泄露，在程序执行完毕后删除threadlocal中存的内容
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
