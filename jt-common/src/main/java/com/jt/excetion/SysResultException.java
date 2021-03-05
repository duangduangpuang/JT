package com.jt.excetion;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class SysResultException {

    @ExceptionHandler(RuntimeException.class)
    public Object sysResult(Exception e, HttpServletRequest request){
        e.printStackTrace();
        String callback = request.getParameter("callback");
        if(StringUtils.isEmpty(callback)){
            return SysResult.fail();
        }else{
            return new JSONPObject(callback,SysResult.fail());
        }
    }

}
