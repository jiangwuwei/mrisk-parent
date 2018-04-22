package com.zoom.risk.jade.exception;

public class DynamicSqlException extends Exception {
	private static final long serialVersionUID = 3473926131491622413L;

	public DynamicSqlException() {
    	super();
    }
    
    public DynamicSqlException(String message) {
    	super(message);
    }
    
    public DynamicSqlException(String message, Exception cause) {
    	super(message, cause);
    }
}
