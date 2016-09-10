package com.ecommerce.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommerce.dao.UserDao;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import com.ecommerce.util.XLSXUtil;

/**
 * Implementation of UserService that delegates
 * transaction management to Spring <br/>
 * database access to UserDao <br/>
 * and creates ACLs for new users.
 * @see UserService
 * @see UserDao
 * 
 */
@Component
public class UserServiceImpl implements UserService {
	
	private UserDao dao;
	private MutableAclService aclService;
	private PasswordEncoder encoder;
	
	@Resource
	private Map<String,String> defaultPaths;
	

	@Autowired
	public UserServiceImpl(UserDao dao,MutableAclService aclService,PasswordEncoder encoder) {
		super();
		this.dao = dao;
		this.aclService = aclService;
		this.encoder = encoder;
	}

	@Override
	@Transactional
	public List<User> searchUser(String username) {
		return dao.searchUser(username);
	}

	@Override
	@Transactional
	public User getUser(String username) {
		return dao.getUser(username);
	}

	/**
	 * Creates a new user trough the dao.
	 * If the user has no picture a default will be set.
	 */
	@Override
	@Transactional
	public void saveUser(User user) {
		if(user.getImagePath() == null){
			user.setImagePath(defaultPaths.get("profilePic"));
		}
		user.setPassword(encoder.encode(user.getPassword()));
		long id = dao.saveUser(user);
		createAcl(new PrincipalSid(user.getUsername()),id);
	}

	@Override
	@Transactional
	public void editUser(User user) {
		dao.editUser(user);
	}

	@Override
	@Transactional
	public void delete(long user_id) {
		dao.deleteUser(user_id);
		deleteAcl(user_id);
	}

	@Override
	@Transactional
	public Cart getCart(User user) {
		return dao.getCart(user.getId());
	}

	@Override
	@Transactional
	public List<Order> getOrders(User user) {
		return dao.getOrders(user.getId());
	}

	@Override
	@Transactional
	public void addCartItem(User user, long product_id) {
		dao.addCartItem(user.getId(), product_id);
	}

	@Override
	@Transactional
	public void removeCartItem(User user, long product_id) {
		dao.removeCartItem(user.getId(), product_id);
		
	}

	@Override
	@Transactional
	public void addOrder(User user) {
		dao.addOrder(user);
		
	}
	@Override
	@Transactional
	public void enableUser(String username) {
		dao.toggleEnabled(true, username);
	}

	@Override
	@Transactional
	public void disableUser(String username) {
		dao.toggleEnabled(false, username);
		
	}
	
	@Override
	@Transactional
	public void changeRole(String username, String role) {
		dao.changeRole(username, role);
	}
	
	
	@Override
	@Transactional
	public List<Order> latestOrders() {
		return dao.latestOrders();
	}
	
	@Override
	@Transactional
	public List<User> getLatest() {
		return dao.getLatest();
	}
	
	@Override
	@Transactional
	public Workbook  getOrderAsXLSX(long orderId, User user) {
		Order o = dao.getOrder(orderId);
		return XLSXUtil.getWorkBook(o); 
	}
	
	@Override
	@Transactional
	public Workbook getOrdersAsXLSX(User user) {
		List<Order> orders = getOrders(user); 
		System.out.println(orders);
		return XLSXUtil.getWorkBook(orders);
	}
	
	/** 
	 * Create an Access Control List that allows the user to edit his profile and check their cart and orders 
	 * <p>Profile deletion is handled only by ROLE_ADMIN</p>
	 * @param sid the acting security id
	 * @param the newly created user's id 
	 */
	private void createAcl(Sid sid,long id){
		MutableAcl acl= aclService.createAcl(new ObjectIdentityImpl(User.class,id));
		acl.setOwner(sid);// If this is not set the default MutableAclImpl will set the owner from the security context holder
		                  // which at this point will be an anonymous user
		acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sid, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, true);
		aclService.updateAcl(acl);
	}
	
	/**
	 * Deletes and Access Control List by it's id
	 * @param id
	 */
	private void deleteAcl(long id){
		aclService.deleteAcl(new ObjectIdentityImpl(User.class,id), true);
	}

}
