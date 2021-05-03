package cn.repeatlink.framework.util;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

/**
 * 异步任务管理器
 * 
 * @author tangliqiang
 */
public class AsyncUtil {
	
	   /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
   private ScheduledExecutorService executor =SpringBeanFactory.getBean("scheduledExecutorService", ScheduledExecutorService.class);


    private AsyncUtil(){}

    private static AsyncUtil instance = new AsyncUtil();

    public static AsyncUtil getInstance()
    {	
    	
        return instance;
    }

    /**
     * 执行任务
     * 
     * @param task 任务
     */
    @PreDestroy
    public void execute(TimerTask task)
    {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown()
    {
        Threads.shutdownAndAwaitTermination(executor);
    }
}
