package com.zoom.risk.gateway.core.monitor.impl;

import com.zoom.risk.gateway.core.monitor.ThreadPoolMonitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by jiangyulin on 2015/12/21.
 */
@Service("threadPoolMonitor")
public class ThreadPoolMonitorImpl implements ThreadPoolMonitor {
    private static final Logger logger = LogManager.getLogger(ThreadPoolMonitorImpl.class);

    @Resource(name="riskPoolExecutor")
    private ThreadPoolTaskExecutor riskPoolExecutor;

    private ThreadPoolExecutor  riskEventKafkaExecutor;

    private StringBuilder builder;

    @PostConstruct
    public void init(){
        builder = new StringBuilder();
        riskEventKafkaExecutor = riskPoolExecutor.getThreadPoolExecutor();
    }


    public void monitorThreadPool(){
        builder.append("**************** RiskGateway:: riskEventKafkaExecutor monitor, detail info : ")
                .append(" number of executing threads : ")
                .append(riskEventKafkaExecutor.getActiveCount())
                .append(" number of current thread in pool: ")
                .append( riskEventKafkaExecutor.getPoolSize())
                .append(" maximum number of thread ever in pool: ")
                .append( riskEventKafkaExecutor.getLargestPoolSize())
                .append(" number of submitted tasks : ")
                .append( riskEventKafkaExecutor.getTaskCount() )
                .append(" number of pending tasks : ")
                .append( riskEventKafkaExecutor.getQueue().size() )
                .append(" number of completed tasks : ")
                .append( riskEventKafkaExecutor.getCompletedTaskCount());
        logger.info(builder.toString());
        builder.delete(0, builder.length());
    }


}
