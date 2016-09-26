package com.ecommerce.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class SimpleAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	Logger logger = Logger.getLogger(getClass());

	

	public SimpleAuthenticationFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}


	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
				HttpServletResponse response, AuthenticationException exception) 
							throws ServletException,IOException{
		logger.error(String.format("Failed Authenticating user\n Request was: %s Exception was: %s",request,exception));
		super.onAuthenticationFailure(request, response, exception);
	}

}
