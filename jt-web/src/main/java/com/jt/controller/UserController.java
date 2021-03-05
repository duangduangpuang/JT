package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.utils.CookieUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    @Reference
    private DubboUserService userService;
    @Autowired
    private JedisCluster jedisCluster;

    @RequestMapping("/{moduleName}")
    public String module(@PathVariable String moduleName){
        return moduleName;
    }

    /**
     * 注册
     * url:http://www.jt.com/user/doRegister
     * 参数：user
     *
     */
    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult saveUser(User user){
        userService.saveUser(user);
        return SysResult.success();
    }

    /**
     * 登录
     * url:http://www.jt.com/user/doLogin?r=0.3418664843083459
     * 参数：username password
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult findUserByUP(User user, HttpServletResponse response){
        String ticket = userService.findUserByUP(user);
        if(StringUtils.isEmpty(ticket)){
            return SysResult.fail();
        }
        Cookie cookie = new Cookie("JT_TICKET", ticket);
        cookie.setDomain("jt.com");
        cookie.setPath("/");
        cookie.setMaxAge(3600*24*7);
        response.addCookie(cookie);
        return SysResult.success();
    }

    /**
     * 退出登录
     * url:http://www.jt.com/user/logout.html
     * 清除缓存，清除cookie
     * 返回：重定向首页
     */
    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        String ticket = CookieUtil.getCookieValue(request, "JT_TICKET");
        if(!StringUtils.isEmpty(ticket)){
            jedisCluster.del(ticket);
            CookieUtil.deleteCookie(response,"JT_TICKET","jt.com","/");
        }
        return "redirect:/";
    }
}
