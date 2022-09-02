package com.xpress.auth.test.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.xpress.auth.test.models.responses.ErrorMessage;
/*
 * Interception et Personnalisation des exeptions
 */
@ControllerAdvice
public class AppExceptionHandler {
	
	@ExceptionHandler(value = { UserServiceException.class })
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex,
			WebRequest request)
	{
		System.out.println("--Personnalisation-des-Exceptions--");
		ex.printStackTrace();

		ErrorMessage errorMessage = new ErrorMessage(402, ex.toString(), new Date());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request)
	{
		System.out.println("--Personnalisation-des-Exceptions--");

		ex.printStackTrace();

		ErrorMessage errorMessage = new ErrorMessage(502, ex.toString(), new Date());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}