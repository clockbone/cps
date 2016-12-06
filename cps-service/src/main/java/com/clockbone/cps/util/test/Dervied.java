package com.clockbone.cps.util.test;

/**
 * Created by qinjun on 2016/11/28.
 */
public class Dervied extends Base {

    private String name = "dervied";

    public Dervied() {
        super();
        tellName();
        printName();
    }

    public void tellName() {
        System.out.println("Dervied tell name: " + name);
    }

    public void printName() {
        System.out.println("Dervied print name: " + name);
    }

    public static void main(String[] args){

        new Dervied();
    }
}
