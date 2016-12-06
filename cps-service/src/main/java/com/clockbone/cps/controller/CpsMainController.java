package com.clockbone.cps.controller;

import com.clockbone.cps.pojo.AddLog;
import com.clockbone.cps.queue.AddThread;
import com.clockbone.cps.util.*;
import com.clockbone.cps.pojo.AddInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CPS入口页
 * 用于标记cps入口
 */
@Controller
public class CpsMainController {
    private static final Logger logger= LoggerFactory.getLogger(CpsMainController.class);

    private static String ERROR_VIEW="";

    /**
     * index
      * @param addStr
     * @param key
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{addStr}/{key}")
    public String index( @PathVariable() String addStr,@PathVariable() String key,HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        //todo 可判断addStr（可加密），key（防篡改）是否匹配
        String descKey="";//与加密key一致
        //解密
       //todo 一些解密
        String aId=""; //desc(addstr)
        AddInfo addInfo = new AddInfo();// 一些查找
        //获取广告引流 地址
        String landingUrl ="";
        String ip = IpUtil.getRequestIp(request);
        try{
            //记录cps广告点击数量
            AddLog log = new AddLog();
            log.setAddId(aId);
            log.setIp(ip);
            //加入到队列，减小数据库压力
            AddThread.thread.addAppLog(log);

        }catch (Exception e){
            return "";
        }
        //从广告表读取需要写入cookies的domain
        String cookiesdomain = addInfo.getUrlDomain();
        //记录cookie
        addCookies(addStr, response, cookiesdomain);
        //跳转到引流页面
        return "redirect:"+landingUrl;
    }

    private void addCookies(String values,HttpServletResponse response,String domain){
        Cookie cookie = new Cookie(Constant.ADD_COOKIE, values);
        cookie.setPath("/");
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }
}
