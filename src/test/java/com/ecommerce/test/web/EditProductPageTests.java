package com.ecommerce.test.web;

import static org.junit.Assert.*;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ecommerce.test.web.page.EditProductPage;
import com.ecommerce.test.web.page.LoginPage;
import com.ecommerce.test.web.page.ProductPage;

@RunAsClient
@RunWith(Arquillian.class)
public class EditProductPageTests extends AbstractWebPageTest {

    @Page
    private EditProductPage ePage;
    
    @Page
    private ProductPage productPage;
    
    @Test
    public void testEditProductPage(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/user/login");
        login.login("user1", "password");
        
        loadPage("/product/loadEdit?productName=IAI+Kfir");
        
        ePage.editProduct("product1", "description", "10.30", "fighters");

        assertEquals("product1",productPage.getProductName());
        assertEquals("description",productPage.getDescription());
        assertEquals("$10.30",productPage.getPrice());
        assertEquals("fighters",productPage.getCategory());
        assertEquals("user1",productPage.getAuthor());
        
    }
    
    @Test
    public void testEditProductInputError(@InitialPage LoginPage login){
        login.loginIfNotAuthenticated("user1","password");
        loadPage("/product/loadEdit?productName=IAI+Kfir");
        
        ePage.editProduct("pr", "de", "abgt", "fighters");
        assertFalse(ePage.getProductNameError().isEmpty());
        assertFalse(ePage.getDescriptionError().isEmpty());
        assertFalse(ePage.getPriceError().isEmpty());
    }
    
    @Test
    public void testEditProductPageAccessDenied(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/product/loadEdit?productName=IAI+Kfir");
        
        ePage.editProduct("product1", "description", "10.30", "fighters");
        assertEquals("404 Page",driver.getTitle().trim());
    }
    
    @Test
    public void testEditProductPageAccessDeniedFromACL(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/user/login");
        login.login("user2", "password");
        
        loadPage("/product/loadEdit?productName=IAI+Kfir");
        
        ePage.editProduct("product1", "description", "10.30", "fighters");

        assertEquals("404 Page",driver.getTitle().trim());
    }    
    
}
