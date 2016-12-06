package com.clockbone.cps.clients;

import com.alibaba.fastjson.JSON;
import com.clockbone.cps.domain.LogRecord;
import com.github.kevinsawicki.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *  线程队列
 */
public class ClientThread extends Thread{
	
	private static final Logger log = LoggerFactory.getLogger(ClientThread.class);
	private static final boolean DEBUG=true;
	public static  String URL_APP_LOG_SERVICE =null;

    private static long SLEEP_TIME=1000*1;
    private static long RETRY_SLEEP_TIME=1000*2;
    
    private static  int MAX_QUEUE_SIZE=1000;
    private static  int FULL_CLEAR_SIZE=500;
    
    //private static HessianProxyFactory factory = null;
    
    public static ClientThread thread=null;

    private BlockingQueue<LogRecord> logQueue = new ArrayBlockingQueue<LogRecord>(MAX_QUEUE_SIZE);

	
    private boolean RUN=true;
    private   AppLogClient logClient=null;

    //启动线程
 	public synchronized static void load(String le_service_log_url,
                                         String le_sleep_time,
                                         String le_retry_sleep_time,
                                         String le_max_queue_size,
                                         String le_full_clear_size) throws ServiceConfigException{


		if(le_service_log_url==null){
			throw new ServiceConfigException("日志 异常服务URL 不可为空");
		}else{
			URL_APP_LOG_SERVICE=le_service_log_url;
		}

		SLEEP_TIME=Integer.parseInt(le_sleep_time);
		RETRY_SLEEP_TIME=Integer.parseInt(le_retry_sleep_time);
		MAX_QUEUE_SIZE=Integer.parseInt(le_max_queue_size);
		FULL_CLEAR_SIZE=Integer.parseInt(le_full_clear_size);

		ClientThread.init();
 	}
 	
 	public static class ServiceConfigException extends Exception{
 		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ServiceConfigException(String mess){
 			super(mess);
 		}
 		public ServiceConfigException(String mess,Exception exception){
 			super(mess,exception);
 		}
 	}

 	public synchronized static void init(){
 		if(thread!=null){
 			log.warn("队列线程已启动!");
 			return ;
 		}

 		if(URL_APP_LOG_SERVICE==null){
 			log.warn("启动线程失败！请设置服务URL。");
 			return ;
 		}
 		//可用于初始其它类型接口实例
 		/*if(factory==null){
 		    factory = new HessianProxyFactory();
 		}
 		factory.setConnectTimeout(10*1000);
		factory.setReadTimeout(10*1000);*/
 		thread=new ClientThread();
 		thread.start();
 	}

	private ClientThread(){
		  logClient=new AppLogClient(URL_APP_LOG_SERVICE);
		  Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(){
				@Override
				public void uncaughtException(Thread t, Throwable e) {

				}
			
			});
	}
	public void run(){
		while(RUN){
            sendLogQueue();
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
			}
		}
	}

	private void sendLogQueue() {
		LogRecord appLog = logQueue.poll();
		while(appLog!=null){
			try {

				logClient.log(appLog);
			 } catch (Exception e) {
				    int tryCount=0;
				    while(true){
                        tryCount++;
                        //如果重试次数超过10次则退出；
                        if(tryCount>=10){
                            break;
                        }
					   try {
						   logClient.log(appLog);
						  break;
					     } catch (Exception e1) {
					     }
					   try {
							Thread.sleep(RETRY_SLEEP_TIME);
						} catch (InterruptedException e2) {}
				}
			}
			appLog = logQueue.poll();
		}
	}
	
	public static synchronized void destory(){
		boolean retry = true;
        thread.RUN=false;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        thread=null;
	}
	public void addAppLog(LogRecord appLog){
		if(!logQueue.offer(appLog)){
            logQueue.drainTo(BLACK_HOLE_COLLECTION, FULL_CLEAR_SIZE);
            logQueue.offer(appLog);
		}
	}
	private static class AppLogClient {
		public AppLogClient(String url) {
			startService(url);
		}
		private void startService(String url) {

		}
		public void log(LogRecord record) throws Exception{
            try {
                String loginRecord = JSON.toJSONString(record);
                Map<String,String> pm = new HashMap<String,String>();
                URL url = new URL(URL_APP_LOG_SERVICE);
                String body = HttpRequest.post(URL_APP_LOG_SERVICE).body();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
	}

	private Collection<Serializable> BLACK_HOLE_COLLECTION=new Collection (){

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean contains(Object o) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Iterator iterator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object[] toArray() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object[] toArray(Object[] a) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean add(Object e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean remove(Object o) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean containsAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean addAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean removeAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean retainAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			
		}
	
	};
	

}
