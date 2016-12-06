package com.clockbone.cps.test;

/**
 * Created by qinjun on 2016/11/22.
 */
public interface UserManager {

    public void addUser(String userId,String userName);
    public void modifyUser(String userId,String userName);
    public void delUser(String userId);
    public String findUser(String userId);
}
