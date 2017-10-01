package com.walmart.ticketService.VenuePOS.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.walmart.ticketService.VenuePOS.controller.entity.ExceptionMessages;


/**
 * This will handle exception from the controller class and respond accordingly
 * to the front end based on kind of exception occurred.
 * @author praveen
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
	
	static Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	 /**
     * Catch all for any exceptions 
     */
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
    	logger.error("Exception Stack",ex);
    	
    	ExceptionMessages emsg =new ExceptionMessages();
    	   emsg.setExceptionMsg(ex.getMessage());
    	   return new ResponseEntity<Object>(emsg, HttpStatus.BAD_REQUEST);
	 }    
}