package com.zoom.risk.platform.engine.monitor.impl;

import com.zoom.risk.platform.engine.monitor.QuotaThreadPoolMonitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by jiangyulin on 2015/12/21.
 */
@Service("quotaThreadPoolMonitor")
public class QuotaThreadPoolMonitorImpl implements QuotaThreadPoolMonitor {
    private static final Logger logger = LogManager.getLogger(QuotaThreadPoolMonitorImpl.class);

    @Resource(name="quotaCollectorThreadPoolExecutor")
    private ThreadPoolTaskExecutor quotaCollectorThreadPoolExecutor;

    private ThreadPoolExecutor  executor;

    public void monitorThreadPool(){
        if  ( executor == null ){
            executor = quotaCollectorThreadPoolExecutor.getThreadPoolExecutor();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("**************** QuotaCollectorThreadPoolExecutor monitor, detail info : ")
                .append(" number of executing threads : ")
                .append(executor.getActiveCount() +" , ")
                .append(" number of current thread in pool: ")
                .append( executor.getPoolSize()+" , ")
                .append(" maximum number of thread ever in pool: ")
                .append( executor.getLargestPoolSize()+" , ")
                .append(" number of submitted tasks : ")
                .append( executor.getTaskCount() +" , ")
                .append(" number of pending tasks : ")
                .append( executor.getQueue().size() +" , ")
                .append(" number of completed tasks : ")
                .append( executor.getCompletedTaskCount());
        logger.info(builder.toString());
    }

    public void setQuotaCollectorThreadPoolExecutor(ThreadPoolTaskExecutor quotaCollectorThreadPoolExecutor) {
        this.quotaCollectorThreadPoolExecutor = quotaCollectorThreadPoolExecutor;
    }
}
