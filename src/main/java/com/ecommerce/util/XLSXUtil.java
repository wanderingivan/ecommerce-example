package com.ecommerce.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ecommerce.model.Order;
import com.ecommerce.model.Product;

/**
 *	Utility that converts an Order object
 *	to a XLS Workbook representation 
 *	@see Order
 */
public class XLSXUtil {

	
	/**
	 * Converts an Order to a workbook
	 * @param Order o
	 * @return Workbook
	 */
	public static Workbook getWorkBook(Order o){
		XSSFWorkbook wb = new XSSFWorkbook();
		fillSheet(o,wb.createSheet("Order " + o.getId()));		
		return wb;
	}

	/**
	 * Converts a list of order to a workbook.
	 * Each Order gets a different sheet based on list ordering
	 * @param orders
	 * @return Workbook
	 */
	public static Workbook getWorkBook(List<Order> orders){
		XSSFWorkbook wb = new XSSFWorkbook();
		for(Order o : orders){
			fillSheet(o,wb.createSheet("Order " + o.getId()));
		}
		return wb;
	}
	
	/**
	 * Fills a sheet from and order as such:<br/>
	 *  First row : Order Legend<br/>
	 *  Second row : Order info<br/>
	 *  Third row : Product Legend<br/>
	 *  Following  rows : Product info<hr/>
	 * @param Order o
	 * @param sheet
	 */
	private static void fillSheet(Order o, XSSFSheet sheet){
		int rowNum = 0;
		mapRow(orderLegend(),sheet.createRow(rowNum++));
		mapRow(convertToHArray(o),sheet.createRow(rowNum++));
		mapRow(productLegend(),sheet.createRow(rowNum++));
		
		for(Product p: o.getItems()){
			mapRow(convertToHArray(p),sheet.createRow(rowNum++));
		}
		
	}
	
	private static Object [] orderLegend(){
		return new Object [] {"Order id","Item count"," Order Total","Date sold","Address"};
	}

	private static Object [] productLegend(){
		return new Object [] {"Product id","Product name","Category","Price","Quantity","Total"};
	}
	
	/**
	 * Converts an Order to a heterogeneous array
	 * @param o
	 * @return Object[]
	 */
	private static Object [] convertToHArray(Order o){
		return new Object [] {o.getId(),o.getItems().size(),o.getTotal(),o.getSold(),o.getAddress()};
	}
	
	/**
	 * Converts an Order to a heterogeneous array
	 * @param p 
	 * @return Object[]
	 */
	private static Object [] convertToHArray(Product p){
		return new Object [] {p.getId(),p.getProductName(),p.getCategory(),p.getPrice(),p.getQuantity(),p.getPrice().multiply(new BigDecimal(p.getQuantity()))};
	}
	
	/**
	 * Writes a  heterogeneous array to a row
	 * @param hArray
	 * @param row
	 */
	private static void mapRow(Object[] hArray,Row row){
		int cellNum = 0;
        for (Object obj : hArray)
        {
           Cell cell = row.createCell(cellNum++);
           if(obj instanceof String){
                cell.setCellValue((String) obj);
           }else if(obj instanceof Long){
                cell.setCellValue((Long) obj);
           }else if(obj instanceof BigDecimal){
        	   cell.setCellValue(((BigDecimal) obj).doubleValue());
           }else if(obj instanceof Date){
        	   cell.setCellValue(obj.toString());
           }
        }
		
	}
	
}
