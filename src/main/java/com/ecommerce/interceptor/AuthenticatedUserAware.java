package com.ecommerce.interceptor;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface that marks an action requires details of the current
 * active user.
 */
public interface AuthenticatedUserAware {

	public void setUserDetails(UserDetails userDetails);
}
