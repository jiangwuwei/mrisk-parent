package com.zoom.risk.jade.exception;

public class LackConditionForUpdateException extends DynamicSqlException {
	private static final long serialVersionUID = -6636683806877275276L;
	
	public LackConditionForUpdateException() {
    	super("The root cause lies in no where condition");
    }
    
    public LackConditionForUpdateException(String message) {
    	super(message);
    }
    
    public LackConditionForUpdateException(String message, Exception cause) {
    	super(message, cause);
    }
    
}
