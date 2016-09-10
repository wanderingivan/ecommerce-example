package com.ecommerce.action.admin;

import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.action.user.AbstractUserAction;
import com.ecommerce.interceptor.AuthenticatedUserAware;

public abstract class AbstractUserAdminAction extends AbstractUserAction implements
		AuthenticatedUserAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2094168130755440928L;
	protected UserDetails actingUser;
	
	@Override
	public void setUserDetails(UserDetails userDetails) {
		actingUser = userDetails;
	}

}
