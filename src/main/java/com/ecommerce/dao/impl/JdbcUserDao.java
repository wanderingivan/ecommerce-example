package com.ecommerce.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.ecommerce.dao.UserDao;
import com.ecommerce.dao.util.CartResultSetExtractor;
import com.ecommerce.dao.util.OrderResultSetExtractor;
import com.ecommerce.dao.util.UserResultSetExtractor;
import com.ecommerce.exception.extractor.ConstraintExceptionConverter;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * 
 * Implementation of UserDao that
 * uses Spring's JdbcTemplate classes to access the database 
 * @see UserDao
 */
@Repository("userDao")
public class JdbcUserDao implements UserDao,UserDetailsService {

	private static final Logger logger = Logger.getLogger(JdbcUserDao.class);
	
	private JdbcTemplate template;
	
	private static final ResultSetExtractor<User> userExtractor = new UserResultSetExtractor();
	private static final ResultSetExtractor<List<Order>> orderExtractor = new OrderResultSetExtractor();
	private static final ResultSetExtractor<Cart> cartExtractor = new CartResultSetExtractor();
	private static final RowMapper<User> userMapper = new BeanPropertyRowMapper<User>(User.class,false);

	
	private static final String insertUserStatement = "INSERT INTO users (username,password,details,imagePath,address,email) "
			                                        + "VALUES (?,?,?,?,?,?)",
	
			                     editUserStatement = "UPDATE users "
			                     		              + "SET username =?, "
			                     		                  + "imagePath=?, "
			                     		                  + "details=?, "
			                     		                  + "address=?, "
			                     		                  + "email=? "
			                     		            + "WHERE user_id = ?",
			                     
			                     selectUserQuery = "SELECT u.user_id,"
											            + "u.username,"
											            + "u.address,"
											            + "u.details,"
											            + "u.email,"
											            + "u.enabled,"
											            + "u.imagePath,"
											            + "p.product_id as id,"
											            + "p.name as productName,"
											            + "p.imagePath,"
											            + "p.description,"
											            + "p.price "
										           + "FROM users u "
										                + "LEFT JOIN products p "
										   	            + "ON p.user_id = u.user_id "
											      + "WHERE u.username = ?",
											         
								 searchUserQuery = "SELECT username, "
								 		                + "imagePath,"
								 		                + "details "
								 		           + "FROM users "
								 		          + "WHERE username LIKE ? "
								 		          + "LIMIT 15", 
	
							     deleteUserStatement = "DELETE FROM users WHERE user_id = ?",
	
			                     selectOrderQuery =  "SELECT p.product_id as id,"
		                                          		  + "p.name as productName,"
		                                          		  + "p.category,"
		                                                  + "p.description,"
		                                                  + "p.price,"
		                                                  + "p.imagePath,"
		                                                  + "o.order_id,"
		                                                  + "o.total,"
		                                                  + "o.address,"
		                                                  + "o.sold, "
		                                                  + "o.sent "
						                            + " FROM products p"
					                                     + " INNER JOIN orders_products op"
					                                     + " ON op.product_id = p.product_id"
					                                        + " AND op.order_id = ?"
					                                     + " INNER JOIN orders o"
					                                     + " ON o.order_id = op.order_id",		                                                  
							     
	                             selectOrdersQuery = "SELECT p.product_id as id,"
				                                          + "p.name as productName,"
				                                          + "p.category,"
				                                          + "p.description,"
				                                          + "p.price,"
				                                          + "p.imagePath,"
				                                          + "o.order_id,"
				                                          + "o.total,"
				                                          + "o.address,"
				                                          + "o.sent,"
				                                          + "o.sold "
				                                    + " FROM products p"
				                                         + " INNER JOIN orders_products op"
				                                         + " ON op.product_id = p.product_id"
				                                            + " AND op.order_id IN "
				                                                + "(SELECT order_id from orders where user_id =?)"
				                                         + " INNER JOIN orders o"
				                                         + " ON o.order_id = op.order_id"
				                                + " ORDER BY o.sold DESC",
				                                  				                            
				                  selectCartQuery  = "SELECT p.product_id as id,"
				                                          + "p.name as productName,"
				                                          + "p.category,"
				                                          + "p.description,"
				                                          + "p.price,"
				                                          + "p.imagePath,"
				                                          + "c.cart_id"
				                                    + " FROM products p"
				                                         + " INNER JOIN carts_products cp"
				                                         + " ON cp.product_id = p.product_id"
				                                           + " AND cp.cart_id = "
				                                               + "(SELECT cart_id from carts where user_id = ?)"
				                                         + " INNER JOIN carts c"
				                                         + " ON c.cart_id = cp.cart_id",              
				                    
				                 latestOrdersQuery = "SELECT p.product_id as id,"
				                                          + "p.name as productName,"
				                                          + "p.category,"
				                                          + "p.description,"
				                                          + "p.price,"
				                                          + "p.imagePath,"
				                                          + "o.order_id,"
				                                          + "o.total,"
				                                          + "o.address,"
				                                          + "o.sent,"
				                                          + "o.sold "
				                                    + " FROM products p"
				                                         + " INNER JOIN orders_products op"
				                                         + " ON op.product_id = p.product_id"
				                                         + " INNER JOIN orders o"
				                                         + " ON o.order_id = op.order_id"
				                                + " ORDER BY o.sold DESC"
				                                   + " LIMIT 5",
				                                   
				                 latestUsersQuery ="SELECT u.username,"
				                 		                + "u.imagePath,"
				                 		                + "u.createdOn "
				                 		           + "FROM users u "
				                 		       + "ORDER BY createdOn DESC "
				                 		       +    "LIMIT 5",
				                 		       
				                 loadUserByUsernameQuery = "SELECT u.user_id,"
				                 		                        + "u.username,"
				                 		                        + "u.password,"
				                 		                        + "u.locked,"
				                 		                        + "u.enabled,"
				                 		                        + "a.authority "
			                                               + "FROM users u "
			                                                    + "INNER JOIN authorities a "
			                                                    + "ON u.username = a.username "
			                                              + "WHERE u.username =?",
			                                              
			                     deleteCartItemStatement = "DELETE FROM carts_products "
			                     		                       + "WHERE cart_id = "
			                     		                            + "(SELECT cart_id FROM carts WHERE user_id = ?) "
			                     		                         + "AND product_id = ?";
	
	private final SimpleJdbcCall addOrderCall;
	
	
	@Autowired
	public JdbcUserDao(JdbcTemplate template) {
		super();
		this.template = template;
		addOrderCall =new SimpleJdbcCall(template).withProcedureName("addOrder")
				                                  .declareParameters(new SqlParameter("userId",Types.BIGINT),
				                                        		     new SqlParameter("address",Types.VARCHAR));
	}

	
	@Override
	public List<Order> getOrders(long user_id) {
		return template.query(selectOrdersQuery,new Object []{user_id},orderExtractor);
	}

	@Override
	public Cart getCart(long user_id) {
		return template.query(selectCartQuery,new Object []{user_id},cartExtractor);
	}

	@Override
	public void editUser(User user) {
		try{
			template.update(editUserStatement, 
				new Object[]{user.getUsername(),user.getImagePath(),
				             user.getDetails(),user.getAddress(),user.getEmail(),user.getId()});
		}catch(DuplicateKeyException de){
			throw ConstraintExceptionConverter.convertException(de);
		}
	}


	@Override
	public long saveUser(User user) {
		KeyHolder idHolder = new GeneratedKeyHolder();
		PreparedStatementCreatorFactory psF = new PreparedStatementCreatorFactory(insertUserStatement,
												                                 new int [] {Types.VARCHAR,Types.VARCHAR,
														                                     Types.VARCHAR,Types.VARCHAR,
														                                     Types.VARCHAR,Types.VARCHAR,});
		
		try{
			template.update(psF.newPreparedStatementCreator(new Object[]{user.getUsername(),user.getPassword(),user.getDetails(),user.getImagePath(),
																		 user.getAddress(),user.getEmail()})
																		 						  ,idHolder);
		}catch(DuplicateKeyException de){
			throw ConstraintExceptionConverter.convertException(de);
		}

		long userId = idHolder.getKey().longValue();
		logger.debug("Saved Id " + userId);
		
		createCart(userId);
		insertRole(user.getUsername());
		
		return userId;
	}

	@Override
	public User getUser(String username) {
		logger.debug("Dao Loading user " + username + " from DB");
		try{
			return (User) template.query(selectUserQuery,
					                      new Object[] {username},
					                      userExtractor);
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
			
	}

	@Override
	public List<Order> latestOrders(){
		return (List<Order>) template.query(latestOrdersQuery,orderExtractor);
	}
	
	@Override
	public List<User> searchUser(String username) {
		StringBuilder restrBuilder = new StringBuilder("%").append(username)
				                                           .append("%");
		
		return (List<User>)template.query(searchUserQuery,
				                           new Object[] {restrBuilder.toString()},
				                           userMapper);
	}

	@Override
	public void deleteUser(long user_id) {
		template.update(deleteUserStatement,new Object[]{user_id});
	}

	@Override
	public void addCartItem(long user_id, long product_id) {
		template.update("INSERT INTO carts_products(cart_id,product_id) "
				      + "VALUES((select cart_id from carts where user_id=?),?)",
				 new Object[]{user_id,product_id});
	}

	@Override
	public void addOrder(User user) {
		addOrderCall.execute(user.getId(),user.getAddress());
	}

	@Override
	public void removeCartItem(long user_id, long product_id) {
		template.update(deleteCartItemStatement, new Object[]{user_id,product_id});
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserDetails u =template.queryForObject(loadUserByUsernameQuery,new Object[]{username}, new RowMapper<User>(){

					@Override
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User u = new User();
						u.setId(rs.getLong("user_id"));
						u.setUsername(rs.getString("username"));
						u.setPassword(rs.getString("password"));
						u.setLocked(rs.getBoolean("locked"));
						u.setEnabled(rs.getBoolean("enabled"));
						u.addAuthority(new SimpleGrantedAuthority(rs.getString("authority")));
						return u;
					}});
		return u;
	}
	
	@Override
	public void toggleEnabled(boolean enabled, String username){
		template.update("UPDATE users SET enabled = ? WHERE username = ?", new Object[]{enabled,username});
	}
	
	public void toggleLock(boolean locked, String username){
		template.update("UPDATE users SET locked = ? WHERE username = ?", new Object[]{locked,username});
	}

	@Override
	public void changeRole(String username,String role){
		role = resolveRole(role);
		logger.debug("Changing authority for user " + username + " to " + role);
		template.update("UPDATE authorities SET authority = ? WHERE username= ?",new Object[]{role,username});
	}
	
	@Override
	public List<User> getLatest() {
		return (List<User>) template.query(latestUsersQuery, userMapper);
	}

	@Override
	public void changePassword(String principal, String password) {
		template.update("UPDATE users SET password = ? WHERE username = ? ", new Object[] {password,principal});
	}


	@Override
	public String getPassword(String principal) {
		return template.queryForObject("SELECT password FROM users WHERE username = ?", new Object[]{principal},String.class);
	}
	
	@Override
	public Order getOrder(long orderId) {
		return template.query(selectOrderQuery, new Object []{orderId},orderExtractor)
				       .get(0);
	}
	
	/**
	 * Convenience method that creates empty carts for all new users.
	 * @param user_id the newly created user's id
	 */
	private void createCart(long user_id) {
		template.update("INSERT INTO carts(user_id) VALUES(?)",new Object[]{user_id});
	}
	/**
	 * Convenience method for inserting default role to newly create users.
	 * the role will always be the lowest privilege role available
	 * @param username the username of the newly created user
	 */
	private void insertRole(String username) {
		template.update("INSERT INTO authorities(username,authority) VALUES(?,'ROLE_USER')",new Object[]{username});
	}
	
	
	/**
	 * Resolve the supplied role to an authority role that 
	 * Spring Security uses.<br/>
	 * Used before updating a user's role.
	 * @param role This is the role that will be resolved
	 * @return String role that will be saved to db 
	 * @throws IllegalArgumentException if supplied role is unmapped
	 */
	private String resolveRole(String role){
		role = role.toUpperCase();
		switch(role){
		case "USER":
			role="ROLE_USER";
			break;
		case "WRITER":
			role = "ROLE_WRITER";
			break;
		case "ADMIN":
			role = "ROLE_ADMIN";
			break;
		default:
			throw new IllegalArgumentException("Received an unmapped role as argument "+role);
		}
		return role;
	}

}
