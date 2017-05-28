package com.ecommerce.test.web;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ecommerce.test.web.page.CreateUserPage;
import com.ecommerce.test.web.page.LoginPage;
import com.ecommerce.test.web.page.UserPage;

import static org.junit.Assert.*;

@RunAsClient
@RunWith(Arquillian.class)
public class CreateUserPageTests extends AbstractWebPageTest {

    
    @Page
    private CreateUserPage cPage;
    
    @Page
    private UserPage uPage;
    

    
    @Test
    public void testCreateUserAcessDeniedException(@InitialPage LoginPage login){
        login.loginIfNotAuthenticated("user1", "password");
        loadPage("/user/loadCreate");
        cPage.createUser("username6", "password", "email@email6.com", "description","address");
        assertEquals("404 Page",driver.getTitle().trim());
    }
    
    
    @Test
    public void testCreateUser(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/user/loadCreate");
        cPage.createUser("username6", "password", "email@email6.com", "description","address");
        assertEquals("username6's Profile", uPage.getUsername());
        assertEquals("description", uPage.getDescription());
    }

    @Test
    public void testCreateUserInputErrors(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/user/loadCreate");
        cPage.createUser("", "", "email@email6", "description","address");
        
        assertFalse(cPage.getUsernameError().isEmpty());
        assertFalse(cPage.getEmailError().isEmpty());
    }
    
}
