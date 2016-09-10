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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

import com.ecommerce.dao.UserDao;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import com.ecommerce.test.dao.AbstractDBTest;
import com.ecommerce.util.CommonUtility;


@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations={"classpath*:testServiceContext.xml",
		                         "classpath*:application-security.xml"})
public class UserServiceSecurityTests extends AbstractDBTest {
	
	
	@Autowired
	private UserService service;
	
	@Autowired
	@Qualifier("mockUserDao")
	private UserDao mockDao;
	
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
		service.delete(2);
	}
	
	/*
	 * User has role but no permission for
	 * object
	 */
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="username1",password="password",authorities={"ROLE_USER"})
	public void testAccessDeniedExceptionFromAcl(){
		User testUser = CommonUtility.createUserBean(2, "username2",null,null,null,null,null);
		service.editUser(testUser);
	}
	
	/*
	 * User has role and permission for method and object
	 */
	@Test
	@WithMockUser(username="username2",password="password",authorities={"ROLE_USER"})
	public void testAccessGrantedFromAcl(){
		User testUser = CommonUtility.createUserBean(2, "username2",null,null,null,null,null);
		service.editUser(testUser);
		Mockito.verify(mockDao,Mockito.times(1)).editUser(testUser);
	}

	/*
	 * User has  role that can
	 * pass permission checks
	 */
	@Test
	@WithMockUser(username="username3",password="password",authorities={"ROLE_ADMIN"})
	public void testAccessGrantedToAdmin(){
		User testUser = CommonUtility.createUserBean(2, "username2",null,null,null,null,null);
		service.editUser(testUser);
		Mockito.verify(mockDao,Mockito.times(1)).editUser(testUser);
	}	

	
	@Test
	public void testAclCreation(){
		User testUser = CommonUtility.createUserBean(3, "username2",null,null,null,null,null);
		MutableAcl mockAcl = Mockito.mock(MutableAcl.class);
		Mockito.when(mockDao.saveUser(testUser))
		       .thenReturn(Long.valueOf(3));
		Mockito.when(mockService.createAcl(Mockito.any(ObjectIdentity.class))).thenReturn(mockAcl);
		service.saveUser(testUser);
		Mockito.verify(mockDao,Mockito.times(1)).saveUser(testUser);
		Mockito.verify(mockService,Mockito.times(1)).createAcl(new ObjectIdentityImpl(User.class,Long.valueOf(testUser.getId())));
		Mockito.verify(mockAcl,Mockito.times(1)).insertAce(Mockito.anyInt(), Mockito.eq(BasePermission.WRITE), Mockito.eq(new PrincipalSid(testUser.getUsername())), Mockito.eq(true));
		Mockito.verify(mockAcl,Mockito.times(1)).insertAce(Mockito.anyInt(), Mockito.eq(BasePermission.READ), Mockito.eq(new PrincipalSid(testUser.getUsername())), Mockito.eq(true));
	}



	public void setService(UserService service) {
		this.service = service;
	}


	public void setMockDao(UserDao mockDao) {
		this.mockDao = mockDao;
	}


	public void setMockService(MutableAclService mockService) {
		this.mockService = mockService;
	}

		
	@Override
	protected IDataSet getDataSet() throws MalformedURLException,
			DataSetException {
		return new FlatXmlDataSetBuilder().build(new File(defaultTestResourceFolder.concat("DbSecTestDataSet.xml")));
	}
	
}
