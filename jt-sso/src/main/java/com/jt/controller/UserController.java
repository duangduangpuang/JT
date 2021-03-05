package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * url:http://sso.jt.com/user/check/admin123/1
     * 参数：param要校验的数据
     *       type为类型：1是username，2是phone，3是email
     *  返回值：SysResult
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject check(@PathVariable String param,@PathVariable Integer type,String callback){
        boolean flag = userService.checkUser(param,type);
        return new JSONPObject(callback, SysResult.success(flag));
    }

    /**
     * url:http://sso.jt.com/user/query/
     *      8d9da349-2736-45dd-bf51-04a55a954643?callback=jsonp1582727335630&_=1582727335754
     * 参数：ticket
     * 返回值：用jsonp包装过的SysResult
     */
    @RequestMapping("/query/{ticket}")
    public JSONPObject queryUser(@PathVariable String ticket,String callback){
        String user = userService.queryUser(ticket);
        return new JSONPObject(callback,SysResult.success(user));
    }

}
