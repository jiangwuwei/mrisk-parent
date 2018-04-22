package com.zoom.risk.platform.kafka.consumer.util;

import com.zoom.risk.platform.kafka.consumer.vo.DelayMessage;
import com.zoom.risk.platform.kafka.consumer.vo.UpdateMode;

import java.util.concurrent.DelayQueue;

/**
 * Created by jiangyulin on 2015/3/7.
 */
public class DelayQueueHolder {
    private DelayQueue<DelayMessage> queues = null;

    public DelayQueueHolder(){
        queues = new DelayQueue<DelayMessage>();
    }

    public void offer(String riskId, String serialNumber, String jsonSource, int retryTimes){
        UpdateMode updateMode = new UpdateMode(riskId, serialNumber, jsonSource, retryTimes);
        this.offer(updateMode);
    }


    public void offer(UpdateMode updateMode) {
        DelayMessage message = new DelayMessage(updateMode);
        queues.offer(message);
    }

    public DelayMessage  take() throws InterruptedException{
        return queues.take();
    }

    public int getSize(){
        return queues.size();
    }
}
