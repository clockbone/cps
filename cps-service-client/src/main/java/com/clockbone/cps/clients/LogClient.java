package com.clockbone.cps.clients;

import javax.servlet.http.HttpServletRequest;

/**
 * client 客户端方法
 */
public interface LogClient {

    public void log(HttpServletRequest request,String userId,String ip);

}
