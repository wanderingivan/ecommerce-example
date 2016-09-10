package com.ecommerce.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ecommerce.test.dao.*;

import junit.framework.TestCase;


@RunWith(Suite.class)
@Suite.SuiteClasses(value = {UserDaoTests.class,
							 ProductDaoTests.class,
							 MessageDaoTests.class})
public class DaoSuiteTest extends TestCase {

}
