package com.clockbone.cps.thread;

import com.clockbone.cps.domain.LogRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinjun on 2016/11/24.
 */
public class Start {

    public static void main(String[] args){

        for(int i=0;i<100;i++){
            LogRecord logRecord = new LogRecord();
            logRecord.setAddId(i + "");
            Cum.put(logRecord);
        }
        Cum cum = new Cum();

        ExecutorService service = Executors.newFixedThreadPool(3);
        service.execute(cum);
        service.shutdown();

        for(int i=0;i<100;i++){
            LogRecord logRecord = new LogRecord();
            logRecord.setAddId(i + "");
            Cum.put(logRecord);
        }


    }
}
