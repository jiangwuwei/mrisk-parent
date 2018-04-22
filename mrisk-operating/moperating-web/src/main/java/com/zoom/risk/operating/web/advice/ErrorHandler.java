package com.zoom.risk.operating.web.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class ErrorHandler {
	private static final Logger logger = LogManager.getLogger(ErrorHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public void errorResponse(Exception ex) {
		logger.error("",ex);
	}
}
