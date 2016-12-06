package com.clockbone.cps.util.test;

import java.util.*;

/**
 * Created by qinjun on 2016/11/28.
 */
public class Test {

    static{

        System.out.println("static...");
    }

    {
        System.out.println("other ...");
    }

    public Test(){
        System.out.println("test construct code...");
    }
    public static void hashsettest(){
        HashSet<String> set = new HashSet<String>();

        set.add("1");
        set.add("2");

        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("z");
        list.add("c");
        list.add("d");
        list.add("e");
       /* Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int flag = o2.compareTo(o1);
                System.out.print(o1);
                System.out.print(o2);
                System.out.println(flag);
                return flag;
            }
        });*/


        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(2);
        list1.add(4);
        list1.add(6);
        list1.add(1);
        list1.add(5);

        Collections.sort(list1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int flag = o2.compareTo(o1);
                System.out.print(o1);
                System.out.print(o2);
                System.out.println(flag);
                return flag;
            }
        });

        System.out.println(list1);
    }

    public  boolean testhuiwen(){
        String str = "abcddcba";
        int midle = str.length()/2;
        //str.
        for(int i=0,j=str.length()-1;i<midle;i++,j--){
            if(str.charAt(i)!=str.charAt(j)){
               return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        /*boolean flag = testhuiwen();
        System.out.println(flag);*/
        Test test = new Test();
        test.testhuiwen();
    }
}
