package com.zoom.risk.platform.kafka.consumer.service.impl;

import com.zoom.risk.platform.es.service.EsActionService;
import com.zoom.risk.platform.kafka.consumer.service.DelayHolderService;
import com.zoom.risk.platform.kafka.consumer.util.DelayQueueHolder;
import com.zoom.risk.platform.kafka.consumer.vo.DelayMessage;
import com.zoom.risk.platform.kafka.consumer.vo.UpdateMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * local delay quues for those update messages who can not find related doc
 * Created by jiangyulin on 2015/3/7.
 */
public class DelayHolderServceImpl implements DelayHolderService, Runnable {
    private static final Logger logger = LogManager.getLogger(DelayHolderServceImpl.class);
    private ThreadPoolTaskExecutor consumerThreadPoolExecutor;
    private EsActionService esActionService;
    private DelayQueueHolder delayQueueHolder;
    private boolean exitFlag = false;

    public DelayHolderServceImpl(ThreadPoolTaskExecutor consumerThreadPoolExecutor, DelayQueueHolder delayQueueHolder, EsActionService esActionService){
        this.consumerThreadPoolExecutor = consumerThreadPoolExecutor;
        this.delayQueueHolder = delayQueueHolder;
        this.esActionService = esActionService;
    }

    public void startup(){
        Thread thread = new Thread(this);
        thread.setName("DelayHolderService-Thread");
        thread.start();
    }

    public void shutdown(){
        this.exitFlag = true;
    }

    @Override
    public void run() {
        while ( !exitFlag ){
            try{
                logger.info("DelayQueue size: {} ", delayQueueHolder.getSize() );
                DelayMessage message = delayQueueHolder.take();
                consumerThreadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        UpdateMode updateMode = message.getUpdateMode();
                        try {
                            //esActionService.findAndUpdateIndex(updateMode.getRiskId(), updateMode.getSerialNumber(), updateMode.getJsonSource(), updateMode.getRetryTimes());
                        }catch (Exception e){
                            logger.info("", e);
                        }
                    }
                });
            }catch(Exception e){
                logger.error("",e);
            }
        }
    }


    public ThreadPoolTaskExecutor getConsumerThreadPoolExecutor() {
        return consumerThreadPoolExecutor;
    }

    public EsActionService getEsActionService() {
        return esActionService;
    }

    public DelayQueueHolder getDelayQueueHolder() {
        return delayQueueHolder;
    }
}
