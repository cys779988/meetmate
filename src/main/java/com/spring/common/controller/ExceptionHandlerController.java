package com.spring.common.controller;

import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ExceptionHandlerController implements ErrorController{
	private final String PATH = "errors/"; 
	
	private final int ERRORCODE_401 = HttpStatus.UNAUTHORIZED.value();
	private final int ERRORCODE_404 = HttpStatus.NOT_FOUND.value();
	private final int ERRORCODE_500 = HttpStatus.INTERNAL_SERVER_ERROR.value();
	
	@GetMapping(value = "/error")
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(status != null) {
			int statusCode = Integer.valueOf(status.toString());
			
			log.info("httpStatus {}",statusCode);
			if(statusCode == ERRORCODE_500) {
				return PATH+"500";
			} else if (statusCode == ERRORCODE_404) {
				return PATH+"404";
			} else if (statusCode == ERRORCODE_401) {
				return PATH+"401";
			}
		}
		return PATH+"error";
	}
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
