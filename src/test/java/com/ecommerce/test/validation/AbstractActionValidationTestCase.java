package com.ecommerce.test.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.StrutsSpringTestCase;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionProxy;

/**
 * Abstract class that handles interceptor configuration 
 * and proxy creation before tests are ran 
 */
public abstract class AbstractActionValidationTestCase extends StrutsSpringTestCase {
	
	// Required setup of AuthInterceptor
	static {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	    SecurityContextHolder.getContext().setAuthentication(
	    		new TestingAuthenticationToken(new TestingUserDetails("username2","password",authorities),authorities));
	}
	
	private static final String  TEST_CONTEXT_LOCATION = 
			                        "classpath*:validationsTestContext.xml";
	
    protected String[] getContextLocations() {
        return new String[] {TEST_CONTEXT_LOCATION};
    }
    
    /**
     * Return an ActionProxy of the supplied action configured not to execute the result and with preset parameters 
     * @param action the action to proxy
     * @param params parameters to set on the action
     * @return ActionProxy
     */
    protected final ActionProxy getProxy(String action,String [] params){
    	setUpRequestTestParams(params);
		ActionProxy actionProxy = getActionProxy(action);
		assertNotNull("Action proxy shouldn't be null", actionProxy);
		actionProxy.setExecuteResult(false);
		return actionProxy;	    	
    }
    /**
     * 
     * @param param the parameters the action will be initialized with
     * 
     */
    protected abstract void setUpRequestTestParams(String [] param);
}
