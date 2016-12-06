package com.clockbone.cps.clients;

import com.google.common.base.Strings;
import com.clockbone.cps.domain.LogRecord;
import com.clockbone.cps.util.Constant;
import com.clockbone.cps.util.ThreeDES;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by qinjun on 2016/11/11.
 */
public class LogClientDefaultImpl implements LogClient {
    private Logger logger = Logger.getLogger(LogClientDefaultImpl.class);

    public void log(String addId,String userId, String ip) {
        if(Strings.isNullOrEmpty(addId)){
            return;
        }
        LogRecord record = new LogRecord();

        record.setIp(ip);

        log(record);
    }


    @Override
    public void log(HttpServletRequest request,String userId ,String ip) {
        try{
            String cookiesStr = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(Constant.CLOCK_ADD_COOKIE)) {
                        cookiesStr = c.getValue();
                    }
                }
            }
            if(Strings.isNullOrEmpty(cookiesStr)){
                return;
            }
            String addId = ThreeDES.decryptWithCBC("","");
            log( addId,  userId,   ip);
        }catch (Exception e){
            logger.error("log EXCEPTION ，e="+e);
        }
    }

    public void log(LogRecord record) {
        //加入到队列
        ClientThread.thread.addAppLog(record);;
    }

}
