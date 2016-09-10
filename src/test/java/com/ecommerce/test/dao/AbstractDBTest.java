package com.ecommerce.test.dao;

import java.net.MalformedURLException;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;

/**
 * AbstractTest class that handles DBunit initialization and 
 * db population before each test 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/basicTestContext.xml"})
public abstract class AbstractDBTest extends TestCase {
	
	protected boolean init;
	
	private  IDatabaseTester databaseTester;

	protected static final String defaultTestResourceFolder = "src/test/resources/";
	
	protected void initialSetUp() throws Exception{
		databaseTester.setDataSet(getDataSet());
		//config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
		init = true;
	}

	@Before
	public void setUp() throws Exception{//FIXME ugly hack to avoid static methods
		if(!init){						// Spring throws a hissy fit if you try to				
			initialSetUp();             // use static methods with its runner 
										// and @BeforeClass seems to work only with static methods
		}                               // so this will have to work for the moment
		databaseTester.onSetup();
	}

	@After
	public void tearDown() throws Exception{
		//databaseTester.onTearDown();
	}
	
	
	@Autowired
	public void setDatabaseTester(IDatabaseTester databaseTester) {
		this.databaseTester = databaseTester;
	}

	/**
	 * Each subclass must provide a data set to populate the database before init
	 * @return IDataSet
	 * @throws MalformedURLException
	 * @throws DataSetException
	 */
	protected abstract IDataSet getDataSet() throws MalformedURLException, DataSetException;
	
}
