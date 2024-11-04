package com.task.employee.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler{
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		
		Map<String, String> response = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((errors)->{
		String fieldName = ((FieldError)errors).getField();
		String message = errors.getDefaultMessage();
        	response.put(fieldName, message);
		});
		
	return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);

	}
}

//	private String FieldError(ObjectError errors) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//
//}
