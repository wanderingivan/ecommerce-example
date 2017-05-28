package com.ecommerce.test.web;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
    CreateProductPageTests.class,
    CreateUserPageTests.class,
    EditUserPageTests.class,
    EditProductPageTests.class
})
public class WebPageSuiteITTests {

}
