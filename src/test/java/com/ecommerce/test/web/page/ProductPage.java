package com.ecommerce.test.web.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage {
    
    @FindBy(id="productName")
    private WebElement productName;
    
    @FindBy(id="description")
    private WebElement description;
    
    @FindBy(id="author")
    private WebElement author;
    
    @FindBy(id="category")
    private WebElement category;

    @FindBy(id="price")
    private WebElement price;

    public String getProductName() {
        return productName.getText().trim();
    }

    public String getDescription() {
        return description.getText().trim();
    }

    public String getAuthor() {
        return author.getText().trim();
    }

    public String getPrice() {
        return price.getText().trim();
    }

    public String getCategory() {
        return category.getText().trim().toLowerCase();
    }

}
