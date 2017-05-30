package com.ecommerce.test.web;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ecommerce.test.web.page.EditUserPage;
import com.ecommerce.test.web.page.LoginPage;
import com.ecommerce.test.web.page.UserPage;

import static org.junit.Assert.*;

@RunAsClient
@RunWith(Arquillian.class)
public class EditUserPageTests extends AbstractWebPageTest {

    
    @Page
    private EditUserPage ePage;
    
    @Page
    private UserPage uPage;

    
    @Test
    public void testEditUserAcessDeniedExceptionFromAcl(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/user/login");
        login.login("user2", "password");
        loadPage("/user/loadEdit?username=user3");
        ePage.editUser("username6","email@email6.com", "description","address");
        assertEquals("404 Page",driver.getTitle().trim());
    }
    
    @Test
    public void testEditUser(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/user/login");
        login.login("user2", "password");
        loadPage("/user/loadEdit?username=user2");
        ePage.editUser("username7", "email@email7.com", "new description","address");
        assertEquals("username7's Profile", uPage.getUsername());
        assertEquals("new description", uPage.getDescription());
    }

    @Test
    public void testEditUserInputErrors(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/user/login");
        login.login("user2", "password");
        loadPage("/user/loadEdit?username=user2");
        ePage.editUser("", "email@email6", "new description","address");
        
        assertFalse(ePage.getUsernameError().isEmpty());
        assertFalse(ePage.getEmailError().isEmpty());
    }
    
    @Test
    public void testEditUserAcessDeniedException(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/user/loadEdit?username=user2");
        ePage.editUser("username6","email@email6.com", "description","address");
        assertEquals("404 Page",driver.getTitle().trim());
    }   
    
}
