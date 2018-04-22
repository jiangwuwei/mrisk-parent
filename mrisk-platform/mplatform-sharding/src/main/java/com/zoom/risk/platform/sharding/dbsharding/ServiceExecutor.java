package com.zoom.risk.platform.sharding.dbsharding;

/**
 * @author jiangyulin
 *         March 27, 2017
 */
@FunctionalInterface
public interface ServiceExecutor<T> {

    public T execute();

}
