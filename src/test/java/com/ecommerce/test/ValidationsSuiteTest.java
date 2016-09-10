package com.ecommerce.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ecommerce.test.validation.*;

import junit.framework.TestCase;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = {UserActionValidationTests.class,
							 ProductActionValidationTests.class,
							 OrderActionValidationTests.class})

public class ValidationsSuiteTest extends TestCase {

}
