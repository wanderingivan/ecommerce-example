package com.ecommerce.test.service;

import java.io.File;
import java.net.MalformedURLException;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.security.test.context.support.WithMockUser;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.ProductService;
import com.ecommerce.test.dao.AbstractDBTest;
import com.ecommerce.util.CommonUtility;


@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations={"classpath*:testServiceContext.xml",
		                         "classpath*:application-security.xml"})
public class ProductServiceSecurityTests extends AbstractDBTest {

	
	@Autowired
	private ProductService service;
	
	@Autowired
	@Qualifier("mockProductDao")
	private ProductDao mockDao;
	
	@Autowired
	@Qualifier("mockAclService")
	private MutableAclService mockService;
	
	/* 
	 * User doesn't have role
	 * required for method
	 */
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="username2",password="password",authorities={"ROLE_USER"})
	public void testAccessDenied(){
	    Product p = new Product();
	    p.setId(2);
		service.delete(p);
	}
	
	/*
	 * User has role but no permission for
	 * object
	 */
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="username1",password="password",authorities={"ROLE_USER"})
	public void testAccessDeniedExceptionFromAcl(){
		Product testProduct = CommonUtility.createProductBean(2,"productname2");
		service.editProduct(testProduct);
	}
	
	/*
	 * User has role and permission for method and object
	 */
	@Test
	@WithMockUser(username="username2",password="password",authorities={"ROLE_USER"})
	public void testAccessGrantedFromAcl(){
		Product testProduct = CommonUtility.createProductBean(2,"productname2");
		service.editProduct(testProduct);
		Mockito.verify(mockDao,Mockito.times(1)).editProduct(testProduct);
	}

	/*
	 * User has  role that can
	 * pass permission checks
	 */
	@Test
	@WithMockUser(username="username3",password="password",authorities={"ROLE_ADMIN"})
	public void testAccessGrantedToAdmin(){
		Product testProduct = CommonUtility.createProductBean(2,"productname2");
		service.editProduct(testProduct);
		Mockito.verify(mockDao,Mockito.times(1)).editProduct(testProduct);
	}	

	
	@Test
	@WithMockUser(username="username3",password="password",authorities={"ROLE_USER"})
	public void testProductAclCreation(){
		Product testProduct = CommonUtility.createProductBean(3,"productname2");
		User testUser = new User();
		testUser.setId(2);
		testUser.setUsername("username3");
		MutableAcl mockAcl = Mockito.mock(MutableAcl.class);
		Mockito.when(mockService.createAcl(Mockito.any(ObjectIdentity.class))).thenReturn(mockAcl);
		Mockito.when(mockDao.createProduct(testProduct,2))
		       .thenReturn(Long.valueOf(3));
		service.createProduct(testProduct,testUser);
		Mockito.verify(mockDao,Mockito.times(1)).createProduct(testProduct,2);
		Mockito.verify(mockService,Mockito.times(1)).createAcl(new ObjectIdentityImpl(Product.class,Long.valueOf(testProduct.getId())));
		Mockito.verify(mockAcl,Mockito.times(1)).insertAce(Mockito.anyInt(), Mockito.eq(BasePermission.WRITE), Mockito.eq(new PrincipalSid(testUser.getUsername())), Mockito.eq(true));
		Mockito.verify(mockAcl,Mockito.times(1)).insertAce(Mockito.anyInt(), Mockito.eq(BasePermission.DELETE), Mockito.eq(new PrincipalSid(testUser.getUsername())), Mockito.eq(true));
	}
	
	
	@Override
	protected IDataSet getDataSet() throws MalformedURLException,
			DataSetException {
		return new FlatXmlDataSetBuilder().build(new File(defaultTestResourceFolder.concat("DbSecTestDataSet.xml")));
	}


	public ProductService getService() {
		return service;
	}

	public void setService(ProductService service) {
		this.service = service;
	}

	public ProductDao getMockDao() {
		return mockDao;
	}


	public void setMockDao(ProductDao mockDao) {
		this.mockDao = mockDao;
	}


	public MutableAclService getMockService() {
		return mockService;
	}


	public void setMockService(MutableAclService mockService) {
		this.mockService = mockService;
	}
	
}
