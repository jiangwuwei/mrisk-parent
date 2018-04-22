package com.zoom.risk.platform.kafka.consumer.vo;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiangyulin on 2015/3/7.
 */
public class DelayMessage implements Delayed {
    private long delayTime;//执行时间
    private long createTime;
    private UpdateMode updateMode;

    public DelayMessage(UpdateMode updateMode){
        createTime = System.nanoTime();
        this.delayTime = TimeUnit.NANOSECONDS.convert(1000, TimeUnit.MILLISECONDS) + System.nanoTime();
        this.updateMode = updateMode;
    }

    public DelayMessage(UpdateMode updateMode, long delayTime){
        createTime = System.nanoTime();
        this.delayTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
        this.updateMode = updateMode;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return  unit.convert(this.delayTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        DelayMessage msg = (DelayMessage) delayed;
        return this.createTime > msg.createTime ? 1 : -1;
    }

    public UpdateMode getUpdateMode() {
        return updateMode;
    }
}
