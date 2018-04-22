package com.zoom.risk.operating.roster.utils;

/**
 * Created by jiangyulin on 2015/4/11.
 */
public class RetryUtils {

    public static <T> T retryTimes(RetryExecutor<T> executor, Class<?> classException, int retryTime) throws Exception {
        T result = null;
        Exception ex = null;
        while ( retryTime > 0 && ( ex == null ||  ex.getClass().isAssignableFrom(classException)) ){
            retryTime--;
            try{
                result = executor.execute();
                break;
            }catch(Exception e){
                ex = e;
                if ( !ex.getClass().isAssignableFrom(classException))
                    throw ex;
            }
        }
        return result;
    }

    public static interface RetryExecutor<T> {
        public T execute() throws Exception;
    }

}
