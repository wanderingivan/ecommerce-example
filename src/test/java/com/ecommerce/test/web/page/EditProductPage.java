package com.ecommerce.test.web.page;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class EditProductPage {

    @FindBy(id="product")
    private WebElement productNameInput;

    @FindBy(id="description")
    private WebElement descriptionInput;

    @FindBy(id="price")
    private WebElement priceInput;

    @FindBy(id="editSubmit")
    private WebElement submit;
    
    @FindBy(id="category")
    private Select categorySelect;

    @FindBy(id="productNameError")
    private WebElement productNameError;
    
    @FindBy(id="descriptionError")
    private WebElement descriptionError;

    @FindBy(id="priceError")
    private WebElement priceError;
    
    public void editProduct(String productName,String description,String price,String category){
        productNameInput.clear();
        productNameInput.sendKeys(productName);
        
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
        
        priceInput.clear();
        priceInput.sendKeys(price);
        
        categorySelect.selectByValue(category);
        
        Graphene.guardHttp(submit).click();
    }
    
    public String getPriceError(){
        return priceError.getText().trim();
    }
    
    public String getDescriptionError(){
        return descriptionError.getText().trim();
    }
    
    public String getProductNameError(){
        return productNameError.getText().trim();
    }
    
}
