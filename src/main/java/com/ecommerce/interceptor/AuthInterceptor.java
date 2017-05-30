package com.ecommerce.interceptor;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor {

	/**
	 *
	 */
	private static final long serialVersionUID = 6165549166976305137L;
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if(invocation.getAction() instanceof AuthenticatedUserAware){
			if(logger.isDebugEnabled()){
				logger.debug("Auth Interceptor Intercepting action " + invocation.getAction().getClass().getSimpleName() + "\n Inserting user" + getUserFromSecurityContext());
			}
			((AuthenticatedUserAware) invocation.getAction()).setUserDetails(getUserFromSecurityContext());
		}
		return invocation.invoke();
	}

	private UserDetails getUserFromSecurityContext(){
	    Object auth  = SecurityContextHolder.getContext()
											.getAuthentication()
											.getPrincipal();
	    if(auth instanceof UserDetails){
	        return (UserDetails) auth;
	    }
	    return null;

	}

}
