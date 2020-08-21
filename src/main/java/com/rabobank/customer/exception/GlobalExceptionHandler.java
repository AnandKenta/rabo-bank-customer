package com.rabobank.customer.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rabobank.customer.constants.Constants;
import com.rabobank.customer.record.RecordResponse;

/**
 * Global Exception Handler
 *
 * @author Anand Pandiyan
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseEntity<RecordResponse> handleException(FormatException formatException) {
		return new ResponseEntity<>(new RecordResponse(Constants.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseEntity<RecordResponse> handleException(Exception exception) {
		return new ResponseEntity<>(new RecordResponse(Constants.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
