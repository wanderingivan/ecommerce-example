<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page trimDirectiveWhitespaces="true" %>


<s:iterator value="orders">
  <s:url action="getOrderXls" namespace="/order" var="getOrderXLS">
  	<s:param name="id" value="%{id}"/>
  </s:url>
  <div class="media">
   <div class="media-body text-left">
	<h3 class="media-heading">&nbsp;<s:text name="global.order_id"/>:&nbsp;${id}</h3>
	<p class="text-muted"><s:text name="global.order_sold"/>&nbsp;<s:date name="sold" format="dd/MMMM/yy"/></p> 
	<p><s:text name="global.order_address"/>&nbsp;<s:property value="address"/><p>
	<p>
		<s:if test="sent">
		  <s:text name="global.sent"/>
		</s:if>
		<s:else>
		  <s:text name="global.not_sent"/>
		</s:else>
	</p>
	<s:iterator value="items">
	   <s:url action="show" namespace="/product" var="showProduct">
	     <s:param name="productName" value="%{productName}"/>
	   </s:url>
	   <s:url action="loadImage" namespace="/util" var="productImage">
	     <s:param name="path" value="%{imagePath}"/>
	   </s:url>
	   <div class="media well well-sm" style="background-color:#fff">
	     <div class="media-left">
		   <img src="<s:property value='#productImage'/>" alt="Product Image"/>
	     </div>
	     <div class="media-body">
	       <p class="media-heading"><s:a href="%{showProduct}"><s:property value="productName"/></s:a></p>
	       <p class="text-muted">
	         <s:text name="global.unit_price"/>:&nbsp;                  			 
	         <span class="price">
               <s:text name="global.money">
                 <s:param name="value" value="%{price}"/>				  
			   </s:text>
             </span>
           </p>
	       <p class="text-muted"><s:text name="global.quantity"/>:&nbsp;<s:property value="quantity"/></p>
	     </div>
       </div>	
	</s:iterator>
	<h3 class="text-center">
	  <s:text name="global.order_total"/>:&nbsp;
               <s:text name="global.money">
                 <s:param name="value" value="%{total}"/>				  
			   </s:text>
			<br/>
	  <s:a href="%{getOrderXLS}"><s:text name="global.get_xls"/></s:a>
	</h3>
   </div>
  </div>
  <div style="border-bottom:1px solid #22a17a"></div>
</s:iterator>