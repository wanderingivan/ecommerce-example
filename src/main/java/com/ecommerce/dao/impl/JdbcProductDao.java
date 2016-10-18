package com.ecommerce.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.dao.util.ProductResultSetExtractor;
import com.ecommerce.dao.util.ReviewRowMapper;
import com.ecommerce.exception.extractor.ConstraintExceptionConverter;
import com.ecommerce.model.Product;
import com.ecommerce.model.Review;

/**
 * Implementation of ProductDao that
 * uses Spring's JdbcTemplate classes to access the database 
 * @see ProductDao
 */
@Repository
public class JdbcProductDao implements ProductDao{

	private static final Logger logger = Logger.getLogger(JdbcProductDao.class);
	
	
	private JdbcTemplate template;

	private static ResultSetExtractor<List<Product>> productExtractor = new ProductResultSetExtractor();
	private static RowMapper<Review> reviewMapper = new ReviewRowMapper();
	private static RowMapper<Product>  productMapper = new BeanPropertyRowMapper<Product>(Product.class,false);

	
	private static final String insertProductStatement = "INSERT INTO products (name,description,imagePath,price,category,user_id) VALUES(?,?,?,?,?,?)",
			
						        insertReviewStatement = "INSERT INTO reviews(product_id,user_id,message,rating) "
						        		                   + "VALUES((SELECT p.product_id from products p WHERE p.name=?),?,?,?)",
						  
						        insertDetailsStatement = "INSERT INTO details (message,product_id) VALUES (?,?)",
						  
						        editProductStatement = "UPDATE products "
						        		                + "SET name = ?,"
						        		                    + "description = ?,"
						        		                    + "price = ?,"
						        		                    + "category = ?,"
						        		                    + "imagePath = ? "
						        		               +"WHERE product_id = ?",
						  
						        editDetailsStatement = "UPDATE details SET message = ? WHERE detail_id = ?",
						        
						        deleteDetailsStatement = "DELETE FROM details WHERE detail_id = ?",
						  
						        deleteProductStatement = "DELETE FROM products WHERE product_id = ?",
						  
						        deleteReviewStatement = "DELETE FROM reviews WHERE review_id= ?",
						  
					            searchProductQuery = "SELECT p.product_id as id, "
					            		                  + "p.name as productName, "
					            		                  + "p.category, "
					            		                  + "p.description, "
					            		                  + "p.imagePath, "
					            		                  + "p.sale as onSale, "
					            		                  + "p.created, "
					            		                  + "p.price "
					            		             + "FROM products p ",
					      
						        selectProductQuery = "SELECT p.product_id,"
				                                          + "p.name,"
				                                          + "p.category,"
				                                          + "p.description,"
				                                          + "p.imagePath, "
				                                          + "p.hits,"
				                                          + "p.sold,"
				                                          + "p.sale,"
				                                          + "p.featured,"
				                                          + "p.created,"
				                                          + "p.price,"
				                                          + "u.user_id,"
				                                          + "u.username,"
				                                          + "d.detail_id,"
				                                          + "d.message"
									                + " FROM products p"
									                     + " INNER JOIN users u"
									                     + " ON p.user_id = u.user_id"
									                     + " LEFT JOIN details d "
									                     + " ON p.product_id = d.product_id"
									               + " WHERE p.name = ?",
									 
						        selectReviewsQuery = "SELECT r.review_id,"
						        		                  + "r.message,"
						        		                  + "r.rating,"
						        		                  + "r.posted,"
						        		                  + "u.username,"
						        		                  + "u.imagePath "
								  				     + "FROM reviews r "
								  				          + "INNER JOIN users u "
								  				          + "ON u.user_id = r.user_id "
								  				    + "WHERE r.product_id = ? ORDER BY r.posted desc";
	

	private static final PreparedStatementCreatorFactory insertStatementCreator  = new PreparedStatementCreatorFactory(insertProductStatement,
				                           new int []{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DECIMAL,Types.VARCHAR,Types.BIGINT});
	
	private final SimpleJdbcCall toggleFeaturedCall;
	
	@Autowired
	public JdbcProductDao(JdbcTemplate template) {
		super();
		this.template = template;
		this.toggleFeaturedCall = new SimpleJdbcCall(template).withProcedureName("setFeatured")
		                                                      .declareParameters(new SqlParameter("product_name",Types.VARCHAR),
		                        		  		                                 new SqlParameter("isFeatured",Types.TINYINT));
		insertStatementCreator.setReturnGeneratedKeys(true);
	}

	@Override
	public Product getProduct(String productName) {
		logger.debug("Dao loading " + productName);
		List<Product> products =  template.query(selectProductQuery,
											 new Object[]{productName},productExtractor);
		logger.debug(products);
		return products.size() == 0 ? null : products.get(0);
	}

	@Override
	public long createProduct(Product p,long userId) {
		KeyHolder holder = new GeneratedKeyHolder(); 
		try{
			template.update(insertStatementCreator.newPreparedStatementCreator(new Object[]{p.getProductName(),p.getDescription(),
																							p.getImagePath(),p.getPrice(),p.getCategory(),userId})
																				  												     	 ,holder);
			if(p.getDetails() != null) {
				insertDetails(p.getDetails(),holder.getKey().longValue());
			}
		}catch(DuplicateKeyException de){
			throw ConstraintExceptionConverter.convertException(de);
		}
		return  holder.getKey().longValue();
	}

	@Override
	public void editProduct(Product p) {
		try{
			template.update(editProductStatement,new Object[]{p.getProductName(),p.getDescription()
												  ,p.getPrice(),p.getCategory(),p.getImagePath(),p.getId()});
		
			if(p.getDetails() != null){
				updateDetails(p.getDetails(),p.getId());
			}
		}catch(DuplicateKeyException de){
			throw ConstraintExceptionConverter.convertException(de);
		}
	}

	@Override
	public List<Product> search(String productName) {
		StringBuilder builder= new StringBuilder("%");
		builder.append(productName);
		builder.append("%");
		return listProducts("WHERE name LIKE ? LIMIT 10",new Object[]{builder.toString()});
	}

	@Override
	public List<Product> getCategory(String category) {
	    if(category.equals("mostSeen")){
	        return getMostSeen();
	    }else if(category.equals("mostSold")){
	        return getMostSold();
	    }
		return listProducts("WHERE category = ? LIMIT 8",new Object[]{category});
	}

	@Override
	public List<Product> getMostSeen() {
			return listProducts("ORDER BY hits DESC LIMIT 8",null);
	}

	@Override
	public List<Product> getMostSold() {
			return  listProducts("ORDER BY sold DESC LIMIT 8",null);
	}

	@Override
	public List<Product> getFeatured() {
		return listProducts("INNER JOIN featured f ON p.product_id=f.product_id ORDER BY f.added DESC LIMIT 4",null);
	}

	@Override
	public List<Product> getLatest() {
			return listProducts("ORDER BY created DESC LIMIT 8",null);
	}

	/**
	 * Retrieve a collect of products from db
	 * by building a query and applying supplied parameter 
	 * @param queryPredicate predicate that will be appended to a preset search query
	 * @param queryParams optional parameters for prepared statement
	 * @return a collection of products 
	 * @see Product
	 */
	private List<Product> listProducts(String queryPredicate,Object [] queryParams){
		
		if(queryParams == null){
			return (List<Product>) template.query(buildSearchQuery(queryPredicate),productMapper);
		}else{
			return (List<Product>) template.query(buildSearchQuery(queryPredicate),queryParams,productMapper);
		}
	}
	
	@Override
	public void deleteProduct(long product_id) {
		template.update(deleteProductStatement, new Object[]{product_id});
	}

	@Override
	public List<Review> getReviews(long pId){
		
		return template.query(selectReviewsQuery,new Object[]{pId},reviewMapper);
	}
	
	@Override
	public void addReview(long user_id, String message, int rating, String productName) {
		logger.debug(String.format("Adding review for product %s message %s rating %d from user_id %d",productName,message,rating,user_id));
		template.update(insertReviewStatement,new Object[]{productName,user_id,message,rating});
	}
	
	public void removeReview(long review_id){
		template.update(deleteReviewStatement, new Object[]{review_id});
	}
	
	@Override
	public void updateHits(final Set<Entry<String, Object[]>> productHits){
		List<Object[]> values = new ArrayList<>();
		for(Entry<String,Object[]> e : productHits){
			if((boolean)e.getValue()[1]){// has the record been updated 
			    values.add(new Object[] {new SqlParameterValue(java.sql.Types.BIGINT,e.getValue()[0]),
					                     new SqlParameterValue(java.sql.Types.VARCHAR,e.getKey())});
			}
		}
		template.batchUpdate("UPDATE products SET hits = ? WHERE name = ?",values);
	}
	
	@Override
	public void toggleFeatured(String productName, boolean on) {
		toggleFeaturedCall.execute(productName,on);
	}

	@Override
	public void toggleSale(String productName, boolean on) {
		template.update("UPDATE products SET sale = ? WHERE name =?",new Object[]{on,productName});
	}

	/**
	 * Inserts details for a product in a batch update
	 * @param details collection of details to add
	 * @param id id of product in dab
	 */
	private void insertDetails(final List<String> details,final long id){
		template.batchUpdate(insertDetailsStatement,new  BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int rowNum) throws SQLException {
				ps.setString(1, details.get(rowNum));
				ps.setLong(2, id);
			}
			
			@Override
			public int getBatchSize() {
				return details.size();
			}
		});		
	}
	
	/**
	 * Updates details for a product in a batch update
	 * @param details collection of details to add
	 * @param id id of product in dab
	 */
	private void updateDetails(final List<String> details,long product_id){
		List<Long> ids = getDetailIds(product_id);
		updateDetails(details, ids);
		int newSize =  details.size() - ids.size();
		if(newSize > 0){
			insertDetails(details.subList(details.size() - newSize, details.size()),product_id);
		}else if(newSize < 0){
			removeDetails(details.size()-newSize,product_id);
		}
			
	 
	}
	
	private void updateDetails(final List<String> details,final List<Long> ids){
		
		template.batchUpdate(editDetailsStatement, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int rowNum) throws SQLException {
				ps.setString(1,details.get(rowNum));
				ps.setLong(2, ids.get(rowNum));
			}
			
			@Override
			public int getBatchSize() {
				return ids.size();
			}
		});	
	}

	private void removeDetails(long startId, long product_id){
		template.update("DELETE FROM details WHERE product_id = ? AND detail_id > ?", new Object[]{product_id,startId});
	}
	
	private List<Long> getDetailIds(long product_id){
		return template.query("SELECT detail_id FROM details WHERE product_id = ?", new Object[]{product_id}, new RowMapper<Long>(){

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return rs.getLong("detail_id");
			}
		});
	}
	
	/**
	 * Convenience method that  builds a query 
	 * by appending a predicate to a preset searchProductQuery
	 * @param predicate the predicate to append
	 * @return query sting to be used as a prepared statement
	 * @see static searchProductQuery
	 */
	private String buildSearchQuery(String predicate){
		StringBuilder builder = 
				new StringBuilder(searchProductQuery);
	
		builder.append(predicate);
		return builder.toString();
	}

}
