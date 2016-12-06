package com.clockbone.cps.util.test;

/**
 * Created by qinjun on 2016/11/28.
 */
public class Base {

    private String name = "base";

    public Base() {
        tellName();
        printName();
    }

    public void tellName() {
        System.out.println("Base tell name: " + name);
    }

    public void printName() {
        System.out.println("Base print name: " + name);
    }
}
