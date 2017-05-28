package com.ecommerce.test.web.page;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("Ecommerce/user/loadCreate")
public class CreateUserPage {
    
    @FindBy(id="username")
    private WebElement usernameInput;

    @FindBy(id="password")
    private WebElement passwordInput;
    
    @FindBy(id="email")
    private WebElement emailInput;
        
    @FindBy(id="description")
    private WebElement descriptionInput;
    
    @FindBy(id="address")
    private WebElement addressInput;
    

    @FindBy(id="createSubmit")
    private WebElement createSubmit;
    
    @FindBy(id="usernameError")
    private WebElement usernameError;
    
    @FindBy(id="passwordError")
    private WebElement passwordError;
    
    @FindBy(id="emailError")
    private WebElement emailError;
    
    @FindBy(id="descriptionError")
    private WebElement descriptionError;
    
    public void createUser(String username,String password,String email,String description,String address){
        
        this.usernameInput.clear();
        this.usernameInput.sendKeys(username);
        this.passwordInput.clear();
        this.passwordInput.sendKeys(password);
        this.emailInput.clear();
        this.emailInput.sendKeys(email);
        this.descriptionInput.clear();
        this.descriptionInput.sendKeys(description);
        this.addressInput.clear();
        this.addressInput.sendKeys(address);
        
        Graphene.guardHttp(createSubmit)
                .click();
    }
    
    public String getUsernameError(){
        return usernameError.getText().trim();
    }
    
    public String getPasswordError() {
        return passwordError.getText().trim();
    }

    public String getEmailError() {
        return emailError.getText().trim();
    }

    public String getDescriptionError() {
        return descriptionError.getText().trim();
    }
    
}
