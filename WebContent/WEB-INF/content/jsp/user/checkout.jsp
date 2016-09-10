<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

 <div class="container" style="min-height:950px;">
  <div class="row">
    <div class="col-md-12">
	  <h3 class="text-center" style="color:#22a17a;"><s:text name="global.checkout"/></h3>
	</div>
  </div>
  <div class="row">
   <s:if test="cart.items.size() > 0">
   <div class="col-md-8 col-sm-12 col-xs-12 well">
	<s:iterator value="cart.items">
	  <s:url action="showProduct" namespace="/product" var="product">
		<s:param name="productName" value="%{productName}"/>
	  </s:url>
  	  <s:url action="loadImage" namespace="/util" var="loadImage">
        <s:param name="path" value="%{imagePath}"/>
  	  </s:url>	
  	  <s:url action="removeItem" namespace="/cart" var="removeItem">
  	  	<s:param name="product_id" value="%{id}"/>
  	  </s:url>
	   <div class="media well well-sm" style="background-color:#fff">
	     <div class="media-left">
		   <s:a href="%{product}"><img src="<s:property value='#loadImage'/>" alt="Product Image"/></s:a>
	     </div>
	     <div class="media-body">
	       <p class="media-heading"><s:a href="%{product}"><s:property value="productName"/></s:a></p>
	       <p class="text-muted"><s:text name="global.unit_price"/>:&nbsp;<s:property value="price"/></p>
	       <p class="text-muted" style="margin-bottom:35px;"><s:text name="global.quantity"/>:&nbsp;<s:property value="quantity"/></p>
           <s:a class="btn-add-cart" href="%{removeItem}">
            <i class="fa fa-shopping-cart"></i>
            <span style="color:#000"><s:text name="global.remove_from_cart"/></span>
           </s:a> 
	     </div>
       </div>
     </s:iterator>
     <span class="price" style="border-bottom:1px solid #22a17a;"><s:text name="global.order_total"/>:&nbsp;<s:property value="cart.total"/></span>
	</div>
   <div class="col-md-4 col-sm-12 col-xs-12">
	<div class="forms">
	<h4 class="text-center" style="color:#22a17a;"><s:text name="global.delivery_information"/></h4>
	<s:form id="user" action="order" namespace="/order" theme="simple" cssClass="form-horizonthal" method="POST">
		<s:hidden name="id" value="%{user.id}"/>
		<s:hidden name="username" value="%{user.username}"/>
	    <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
		<div class="form-group">
		  <s:label for="address" key="global.address"/>
		  <s:textarea name="address" cssClass="form-control" label="address">
			<s:param name="value" value="%{user.address}"/>
		  </s:textarea>		
		</div>
	 	<div class="form-group">
	 	  <s:label for="cardNumber" key="global.card_number"/>
		  <s:textfield name="cardNumber" cssClass="form-control" label="Card Number"/>
		</div>
        <div class="form-group pull-right">
          <s:submit cssClass="btn-alt" key="global.place_order"/>
        </div>
	</s:form>
	</div>
  </div>
  </s:if>
  <s:else>
  	<h3 style="color:#22a17a;text-align:center;margin-top:50px;"><s:text name="global.nothing_here"/></h3>
  </s:else>
  </div>
 </div>