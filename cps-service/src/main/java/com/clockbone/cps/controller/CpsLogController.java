package com.clockbone.cps.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.clockbone.cps.pojo.LogRecord;
import com.clockbone.cps.service.AddService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * cps服务类，用于引流客户 调用，调用时需要引入cps客户端
 */
@Controller
public class CpsLogController {

    private Logger logger = LoggerFactory.getLogger(CpsMainController.class);

    @Autowired
    private AddService addService;

    private static String msg_ok="ok";
    private static String msg_error="error";

    /**
     * cps记录,setRecordType 根据recordtype区分不同类型
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logcps",method = RequestMethod.POST)
    @ResponseBody
    public String logCps( HttpServletRequest request, HttpServletResponse response){
        String loginRecord = request.getParameter("loginRecord");
        LogRecord cpsLoginRecord = null;
        try{
            cpsLoginRecord = JSON.parseObject(loginRecord, LogRecord.class);
        }catch (Exception e){
            return msg_error;
        }
        String userid = cpsLoginRecord.getUserAccount();
        try{
            //记录引流成功的信息，用于统计 这里也可以设计 一个队列存放记录，再别启线程消费
            addService.cpsRecord(cpsLoginRecord);
        }catch (Exception e){
            return msg_error;
        }
        return msg_ok;
    }

}
