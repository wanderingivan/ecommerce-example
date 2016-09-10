package com.ecommerce.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ecommerce.test.service.*;

import junit.framework.TestCase;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = {ProductServiceSecurityTests.class,
					         UserServiceSecurityTests.class})
public class ServiceSuiteTest extends TestCase {

}
