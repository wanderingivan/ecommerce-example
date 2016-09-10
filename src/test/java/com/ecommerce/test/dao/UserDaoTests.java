package com.ecommerce.test.dao;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import javax.transaction.Transactional;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ecommerce.dao.UserDao;
import com.ecommerce.exception.DuplicateEmailException;
import com.ecommerce.exception.DuplicateUsernameException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

public class UserDaoTests extends AbstractDBTest {

	@Autowired
	UserDao dao;
	
	@Test
	@Transactional
	public void testUserRetrieve(){
		User u = getUser("username1");
		assertNotNull(u);
		assertEquals("username1",u.getUsername());
		assertEquals("test description1",u.getDetails());
		assertEquals("test address",u.getAddress());
	}


	
	@Test
	@Transactional
	public void testUserEdit(){
		User test = new User(2,"username2", "new pass", "new details","", "new address", "new@email.com");
		dao.editUser(test);
		User u = getUser("username2");
		assertNotNull(u);
		assertEquals("Incorrect user retrieved","username2",u.getUsername());
		assertEquals("Incorrect details saved","new details",u.getDetails());
		assertEquals("Incorrect addresses saved","new address",u.getAddress());
	}
	
	@Test
	@Transactional
	public void testSearchUser(){
		assertEquals("incorrect number of users retrieved",5,dao.searchUser("username").size());
	}
	
	@Test
	@Transactional
	public void testDeleteUser(){
		assertNotNull(getUser("username3"));
		
		dao.deleteUser(3);
		assertNull(getUser("username3"));
		User u = new User(0,"username6", "pass", "det", "/home/shadow1207/test/a8a8a8.png", "ad", "email@email.com");
		dao.saveUser(u);
	}
	
	@Test
	@Transactional
	public void testUserCreate(){
		User u = new User(0,"username6", "pass", "det", "/home/shadow1207/test/a8a8a8.png", "ad", "email@email.com");
		dao.saveUser(u);
		assertNotNull(getUser("username6"));
	}

	@Test(expected=DuplicateUsernameException.class)
	@Transactional
	public void testUserCreateDuplicateUsername(){
		User u = new User(0,"username1", "pass", "det", "/home/shadow1207/test/a8a8a8.png", "ad", "email@email.com");
		dao.saveUser(u);
	}

	@Test(expected=DuplicateEmailException.class)
	@Transactional
	public void testUserCreateDuplicateEmail(){
		assertNull(getUser("username6"));
		User u = new User(0,"username6", "pass", "det", "/home/shadow1207/test/a8a8a8.png", "ad", "some@email");
		dao.saveUser(u);
	}
	
	@Test
	@Transactional
	public void testGetCart(){
		Cart c = dao.getCart(1);
		assertNotNull("Cart is null",c);
		assertEquals(2,c.getItems().size());
	}
	
	@Test
	@Transactional
	public void testGetOrder(){
		Order o = dao.getOrder(2);
		assertEquals(1,o.getItems().size());
		assertEquals(4, o.getItems().get(0).getQuantity());
	}
	@Test
	@Transactional
	public void testGetOrders(){
		List<Order> orders = dao.getOrders(1);
		assertNotNull(orders);
		assertEquals(2,orders.size());
		assertEquals(2,orders.get(0).getItems().size());
		assertEquals(1,orders.get(1).getItems().size());
	}
	
	@Test
	@Transactional
	public void testAddCartItem(){
		dao.addCartItem(1,1);
		Cart c = dao.getCart(1);
		assertNotNull("Cart is null",c);
		assertEquals(3,c.getItems().size());
	}

	@Test
	@Transactional
	public void testRemoveCartItem(){
		dao.removeCartItem(1, 3);
		Cart c = dao.getCart(1);
		assertNotNull("Cart is null",c);
		assertEquals(c.getItems().size(), 1);
	}
	
	@Test
	@Transactional
	public void testAddOrder(){
		User test = new User();
		test.setId(1);
		test.setAddress("test address");
		dao.addOrder(test);
		List<Order> orders = dao.getOrders(1);
		assertNotNull(orders);
		assertEquals(3,orders.size());
		assertEquals(2,orders.get(0).getItems().size());
	}

	@Test
	@Transactional
	public void testToggleEnabled(){
		dao.toggleEnabled(true,"username1");
		User test = dao.getUser("username1");
		assertEquals(true,test.isEnabled());
	}
	
	@Test
	@Transactional
	public void testToggleDisabled(){
		dao.toggleEnabled(false,"username1");
		User test = dao.getUser("username1");
		assertEquals(false,test.isEnabled());
	}
	
	@Test
	@Transactional
	public void testChangeRole(){
		dao.changeRole("username1", "admin");
		UserDetails test = ((UserDetailsService) dao).loadUserByUsername("username1");
		assertEquals("ROLE_ADMIN",((List<? extends GrantedAuthority>) test.getAuthorities()).get(0).toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	@Transactional
	public void testChangeRoleInvalidParam(){
		dao.changeRole("username1", "unused_role");
		fail("Expected IllegalArgumentException");
	}
	
	@Test
	@Transactional
	public void testLoadUserByUsername(){
		UserDetails user = ((UserDetailsService) dao).loadUserByUsername("username1");
		assertNotNull(user);
		assertEquals(1, user.getAuthorities().size());
		assertEquals("ROLE_USER",((List<? extends GrantedAuthority>)user.getAuthorities()).get(0).toString());
		assertEquals((long) 1,((User) user).getId());
	}
	
	@Override
	protected IDataSet getDataSet() throws MalformedURLException,
			DataSetException {
		return new FlatXmlDataSetBuilder().build(new File(defaultTestResourceFolder.concat("DbTestDataSet.xml")));
	}
	
	public void setDao(UserDao dao) {
		this.dao = dao;
	}
	private User getUser(String username){
		return dao.getUser(username);
	}


	

	
}
