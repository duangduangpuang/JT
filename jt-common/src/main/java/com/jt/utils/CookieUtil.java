package com.jt.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void deleteCookie(HttpServletResponse response,String cookieName,String domian,String path){
        Cookie cookie = new Cookie(cookieName,"");
        cookie.setDomain(domian);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for (Cookie cookie : cookies) {
                if(cookieName.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request,String cookieName){

        Cookie cookie = getCookie(request, cookieName);
        if(cookie!=null){
            return cookie.getValue();
        }else{
            return null;
        }

    }

}
