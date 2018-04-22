package com.zoom.risk.gateway.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class HttpErrorHandler {
	private static final Logger logger = LogManager.getLogger(HttpErrorHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public void errorResponse(Exception ex) {
		logger.error("",ex);
	}
}
