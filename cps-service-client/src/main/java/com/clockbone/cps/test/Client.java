package com.clockbone.cps.test;

import java.lang.reflect.Proxy;

/**
 * Created by qinjun on 2016/11/22.
 */
public class Client {

    /**
     * @param args
     */
    public static void main(String[] args) {
        LogHandler logHandler = new LogHandler();
        UserManagerImpl userManager = new UserManagerImpl();

        Class cls = userManager.getClass();


        UserManager proxy = (UserManager)logHandler.newProxyInstance(userManager);

        Class procls = proxy.getClass();

        System.out.println(proxy instanceof Proxy);

        System.out.println(userManager.getClass().getName());

        System.out.println(proxy.getClass().getName());



        proxy.findUser("0001");
    }
}
