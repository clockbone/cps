package com.clockbone.cps.util.test;

/**
 * Created by qinjun on 2016/11/28.
 */
public class HelloA {
    public HelloA() {
        System.out.println("HelloA");
    }

    { System.out.println("I'm A class"); }

    static { System.out.println("static A"); }
}


