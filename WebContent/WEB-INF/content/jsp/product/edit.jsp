<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page trimDirectiveWhitespaces="true" %>

  <div class="list">
   <div class="container">
    <div class="row">
       <div class="col-xs-12 col-sm-10 col-md-9 ">
		 <div class="box">
		   <h3 class="text-center"><s:text name="global.edit_product"/></h3>
		 </div>
		 <div class="forms">
		   <s:form action="editProduct" theme="simple" namespace="/product" enctype="multipart/form-data">
		   	 <div class="form-group">
		   	   <s:hidden name="product.id" value="%{product.id}"/>
		   	   <s:fielderror id="productNameError" fieldName="product.productName" cssClass="alert alert-danger"/>
		   	   <s:label for="product.productName"><s:text name="global.product_name"/></s:label>
		       <s:textfield id="product" name="product.productName" value="%{product.productName}" class="form-control"/>
		     </div>
		     <div class="form-group">
		   	   <s:label for="product.category"><s:text name="global.product_category"/></s:label>&nbsp;
		   	   <s:fielderror fieldName="product.category" cssClass="alert alert-danger"/>
		       <s:select id="category" name="category" label="Category"
		       		     list="#{'ground-attack':'ground-attack','bombers':'bombers','fighters':'fighters','transport':'transport'}">
			     <s:param name="value">
					${product.category}
				 </s:param>
				</s:select>
		     </div>
		   	 <div class="form-group">
		   	   <s:fielderror id="descriptionError" fieldName="product.description" cssClass="alert alert-danger"/>
		   	   <s:label for="product.description"><s:text name="global.description"/></s:label>
		       <s:textarea id="description" name="product.description" value="%{product.description}" class="form-control"/>		       
		     </div>
		   	 <div id="detailsGroup" class="form-group">
		   	   <s:fielderror fieldName="product.details" cssClass="alert alert-danger"/>
		   	   <s:label for="product.details"><s:text name="global.product_details"/></s:label>
	           <s:iterator status="stat" value="product.details">
	      	     <s:textarea id="detailInput" class="form-control">
	      	 	   <s:param name="name">
	      	 	    product.details[<s:property value="#stat.index"/>]
	      	 	   </s:param>
	      		   <s:param name="value">
	      			  <s:property/>
	      		   </s:param>
	      	     </s:textarea>
	           </s:iterator>
		     </div>
	       <s:a href="" id="addDetail" class="btn"><s:text name="global.add_details"/></s:a>
		   	 <div class="form-group">
		   	   <s:label for="productPic"><s:text name="global.product_picture"/></s:label>
		       <s:file name="productPic" accept="image/jpg"/>
		     </div>
		   	 <div class="form-group">
		   	   <s:fielderror id="priceError" fieldName="product.price" cssClass="alert alert-danger"/>
		   	   <s:label for="product.price"><s:text name="global.price"/></s:label>
		       <s:textfield id="price" class="form-control"  value="%{product.price}" name="product.price"/>
		     </div>
		   	 <div class="form-group">
		       <s:submit id="editSubmit" value="Edit"/>
		     </div>
		   	 <s:hidden name="product.imagePath" value="%{product.imagePath}"/>
    		 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		   </s:form>
		 </div>
       </div>
       
       <div class="col-xs-12 col-sm-2 col-md-3 "></div>
    </div>
   </div>
  </div>
  <script src="${pageContext.request.contextPath}/js/dynamicInput.js"></script>