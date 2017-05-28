package com.ecommerce.test.web;

import static org.junit.Assert.*;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ecommerce.test.web.page.CreateProductPage;
import com.ecommerce.test.web.page.LoginPage;
import com.ecommerce.test.web.page.ProductPage;

@RunAsClient
@RunWith(Arquillian.class)
public class CreateProductPageTests extends AbstractWebPageTest {

    @Page
    private CreateProductPage cPage;
    
    @Page
    private ProductPage productPage;
    
    @Test
    public void testCreateProductPage(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/user/login");
        login.login("user1", "password");
        
        loadPage("/product/loadCreate");
        
        cPage.createProduct("product1", "description", "10.30", "fighters");

        assertEquals("product1",productPage.getProductName());
        assertEquals("description",productPage.getDescription());
        assertEquals("10.30",productPage.getPrice());
        assertEquals("fighters",productPage.getCategory());
        assertEquals("user1",productPage.getAuthor());
        
    }
    
    @Test
    public void testCreateProductInputError(@InitialPage LoginPage login){
        login.loginIfNotAuthenticated("user1","password");
        loadPage("/product/loadCreate");
        
        cPage.createProduct("pr", "de", "abgt", "fighters");
        assertFalse(cPage.getProductNameError().isEmpty());
        assertFalse(cPage.getDescriptionError().isEmpty());
        assertFalse(cPage.getPriceError().isEmpty());
    }
    
    @Test
    public void testCreateProductPageAccessDenied(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("/product/loadCreate");
        
        cPage.createProduct("product1", "description", "10.30", "fighters");
        assertTrue(login.assertOnLoginPage());
    }
    
}
