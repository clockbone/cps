package com.clockbone.cps.thread;

import com.clockbone.cps.domain.LogRecord;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by qinjun on 2016/11/24.
 */
public class Cum implements  Runnable{

    private static BlockingQueue<LogRecord> blockingQueue = new ArrayBlockingQueue<LogRecord>(1000);

    private volatile Boolean run = true;


    public static void put(LogRecord log){
        blockingQueue.offer(log);
    }


    @Override
    public void run() {
        while (run){
            LogRecord log = blockingQueue.poll();
            while(null!=log){

                System.out.println(log);

                log=blockingQueue.poll();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
