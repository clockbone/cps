package com.clockbone.cps.queue;

import com.clockbone.cps.pojo.AddLog;
import com.clockbone.cps.service.AddService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class AddThread extends Thread{
	
	private static final Logger log = LoggerFactory.getLogger(AddThread.class);
	private static final boolean DEBUG=false;

    private static long SLEEP_TIME=1000*1;
    private static long RETRY_SLEEP_TIME=1000*2;
    
    private static  int MAX_QUEUE_SIZE=1000;
    private static  int FULL_CLEAR_SIZE=500;

    private  static ApplicationContext applicationContext ;

    private static AddService addService;
    

    public static AddThread thread=null;
    

    
    private BlockingQueue<AddLog> mLogQueue = new ArrayBlockingQueue<AddLog>(MAX_QUEUE_SIZE);

	
    private boolean RUN=true;
    private   AppLogClient logClient=null;

    static{
        applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
        //检查一些初始是否成功
        addService = (AddService) applicationContext.getBean("addService");
    }

 	public synchronized static void load(String le_sleep_time,
                                         String le_retry_sleep_time,String le_max_queue_size,String le_full_clear_size) throws ServiceConfigException{

		SLEEP_TIME=Integer.parseInt(le_sleep_time);
		RETRY_SLEEP_TIME=Integer.parseInt(le_retry_sleep_time);
		MAX_QUEUE_SIZE=Integer.parseInt(le_max_queue_size);
		FULL_CLEAR_SIZE=Integer.parseInt(le_full_clear_size);
		AddThread.init();
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
 		thread=new AddThread();
 		thread.start();
 		
 	}

	private AddThread(){
		  logClient=new AppLogClient();
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
        AddLog appLog = mLogQueue.poll();
        if(DEBUG){
            log.info("appLog={}",appLog);
        }
		while(appLog!=null){
			try {
				if(DEBUG){
				}
				logClient.log(appLog);
			 } catch (Exception e) {
				    int tryCount=0;
				    while(true){
                        ++tryCount;
                        if(tryCount>5){
                            break;
                        }
					   try {
						  //add
						  break;
					     } catch (Exception e1) {
					     }
					   try {
							Thread.sleep(RETRY_SLEEP_TIME);
						} catch (InterruptedException e2) {}
				    }
			}
			appLog = mLogQueue.poll();
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
	public void addAppLog(AddLog appLog){
		if(!mLogQueue.offer(appLog)){
			mLogQueue.drainTo(BLACK_HOLE_COLLECTION, FULL_CLEAR_SIZE);
			mLogQueue.offer(appLog);
		}
	}
	private static class AppLogClient {
		public AppLogClient() {
			startService();
		}
		private void startService() {
		}
		public void log(AddLog record) throws Exception{
            try {
                String aid=record.getAddId();
                String ip = record.getIp();
                addService.saveLog(aid, ip);
            } catch (Exception e) {
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
