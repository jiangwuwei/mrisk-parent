package com.zoom.risk.platform.sharding.dbsharding;

/**
 * Created by jiangyulin on 2017/3/1.
 */
public interface SessionManager {

    public <T> T runWithSession(ServiceExecutor<T> executor, String dsKey);

    public void runWithSession(ServiceExecutorWithoutResult executor, String dsKey);
}
