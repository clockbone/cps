package com.clockbone.cps.interceptor;

import com.google.common.base.Strings;
import com.clockbone.cps.clients.LogClient;
import com.clockbone.cps.util.Constant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by qinjun on 2016/11/15.
 */
public class CpsInterceptor implements HandlerInterceptor {

    private LogClient cpsLogClient;


    public LogClient getCpsLogClient() {
        return cpsLogClient;
    }

    public void setCpsLogClient(LogClient cpsLogClient) {
        this.cpsLogClient = cpsLogClient;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("posthandle...");

        String ip = "";
        try{
            String uname =request.getParameter("uname");
            if(Strings.isNullOrEmpty(uname)){
                return;
            }
            String addstr = getAddFromCookie(request);

            cpsLogClient.log(request, uname,  ip);
        }catch (Exception e){

        }
    }
    private String getAddFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constant.CLOCK_ADD_COOKIE)) {
                    try {
                        String addstr = cookie.getValue();
                        if (addstr == null) {
                            return null;
                        }
                        return addstr;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
