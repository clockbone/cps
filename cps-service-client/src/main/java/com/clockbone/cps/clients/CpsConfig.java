package com.clockbone.cps.clients;

import org.apache.log4j.Logger;

/**

 */
public class CpsConfig {
    private Logger logger = Logger.getLogger(CpsConfig.class);


    public CpsConfig(String le_service_log_url, String le_sleep_time,
                     String le_retry_sleep_time, String le_max_queue_size, String le_full_clear_size){
        //加载配置文件//启动线程队列
        try{
            ClientThread.load(le_service_log_url, le_sleep_time, le_retry_sleep_time, le_max_queue_size, le_full_clear_size);
        }catch (Exception e){
        }

    }
}
