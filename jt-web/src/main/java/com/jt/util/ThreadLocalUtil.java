package com.jt.util;

import com.jt.pojo.User;

public class ThreadLocalUtil {

    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void setUser(User user){
        threadLocal.set(user);
    }

    public static User getUser(){
        return threadLocal.get();
    }

    public static void  remove(){
        threadLocal.remove();
    }

}
