package com.ecommerce.test.dao;

import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.exception.DuplicateProductNameException;

public class ProductDaoTests extends AbstractDBTest {

	private ProductDao dao;

	@Test
	@Transactional
	public void testProductRetrieve(){
		Product p = getProduct("product1");
		assertNotNull(p);
		assertEquals("product1",p.getProductName());
		assertEquals("description",p.getDescription());
		assertEquals("cat",p.getCategory());
	}
	
	
	@Test 
	@Transactional
	public void testProductEdit(){
		Product test =new Product((long) 2,"product2","new cat", "new description", null, new BigDecimal(10.5000), null);
		test.setImagePath("new ImagePath");
		dao.editProduct(test);
		Product p = getProduct("product2");
		assertNotNull(p);
		assertEquals("product2",p.getProductName());
		assertEquals("new description",p.getDescription());
		assertEquals("new cat",p.getCategory());
		assertEquals((double) 10.5,p.getPrice().doubleValue());
	}
	
	@Test
	@Transactional
	public void testProductDelete(){
		dao.deleteProduct(3);
		assertNull(getProduct("product3"));
	}

	@Test
	@Transactional
	public void testProductSearch(){
		List<Product> p = dao.search("product");
		assertNotNull(p);
		assertEquals(10,p.size());
	}
	
	@Test
	@Transactional
	public void testCategoryRetrieve(){
		List<Product> p = dao.getCategory("cat2");
		assertNotNull(p);
		assertEquals(2,p.size());	
	}

	@Test 
	@Transactional
	public void testGetMostSeen(){
		List<Product> p = dao.getMostSeen();
		assertNotNull(p);
		assertEquals(8,p.size());	
	}
	
	@Test 
	@Transactional
	public void testGetMostSold(){
		List<Product> p = dao.getMostSeen();
		assertNotNull(p);
		assertEquals(8,p.size());	
	}
	
	@Test 
	@Transactional
	public void testGetFeatured(){
		List<Product> p = dao.getFeatured();
		assertNotNull(p);
		assertEquals(4,p.size());	
	}
	
	@Test
	@Transactional
	public void testGetReviews(){
		List<Review> r = dao.getReviews(3);
		assertNotNull(r);
		assertEquals(2,r.size());		
	}
	
	@Test
	@Transactional
	public void testCreateProduct(){
		Product p = new Product(0,"product11","cat", "desc", "product.png", new BigDecimal(14.67), null);
		dao.createProduct(p,3);
		assertNotNull(getProduct("product11"));
	}

	@Test(expected=DuplicateProductNameException.class)
	@Transactional
	public void testCreateProductDuplicateKeyException(){
		Product p = new Product(0,"product10", "cat", "desc", "product.png", new BigDecimal(14.67), null);
		dao.createProduct(p,3);
	}
	
	@Test
	@Transactional
	public void testAddReview(){
		dao.addReview(1, "message", 3, "product2");
		List<Review> r = dao.getReviews(2);
		assertNotNull(r);
		assertEquals(1,r.size());		
	}
	
	@Test
	@Transactional
	public void testUpdateHits(){
		Map<String,Object[]> productHits = new HashMap<>();
		productHits.put("product5", new Object[]{10,true});
		dao.updateHits(productHits.entrySet());
		Product test = getProduct("product5");
		assertEquals("Incorrect number of hits saved",10,test.getHits());
	}
	
	@Test
	@Transactional
	public void testToggleFeaturedOn(){
		dao.toggleFeatured("product6", true);
		Product test = getProduct("product6");
		assertEquals(true, test.isFeatured());
	}
	
	@Test
	@Transactional
	public void testToggleFeaturedOff(){
		dao.toggleFeatured("product4", false);
		Product test = getProduct("product4");
		assertEquals(false, test.isFeatured());
	}
	
	
	@Test
	@Transactional
	public void testToggleOnSale(){
		dao.toggleSale("product6", true);
		Product test = getProduct("product6");
		assertEquals(true, test.isOnSale());
		
	}
	
	@Test
	@Transactional
	public void testToggleOffSale(){
		dao.toggleSale("product7", false);
		Product test = getProduct("product7");
		assertEquals(false, test.isOnSale());
		
	}
	
	@Autowired
	public void setDao(ProductDao dao) {
		this.dao = dao;
	}
	
	@Override
	protected IDataSet getDataSet() throws MalformedURLException,
			DataSetException {
		return new FlatXmlDataSetBuilder().build(new File(defaultTestResourceFolder.concat("DbTestDataSet.xml")));
	}

	private Product getProduct(String productName){
		return dao.getProduct(productName);
	}
	
}
