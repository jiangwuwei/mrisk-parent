package com.zoom.risk.jade.exception;

public class LackValueForUpdateException extends DynamicSqlException {
	private static final long serialVersionUID = 2670270217856596308L;

	public LackValueForUpdateException() {
    	super("The root cause lies in lack input values in where condition");
    }
    
    public LackValueForUpdateException(String message) {
    	super(message);
    }
    
    public LackValueForUpdateException(String message, Exception cause) {
    	super(message, cause);
    }
}
