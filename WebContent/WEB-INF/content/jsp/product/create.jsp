<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page trimDirectiveWhitespaces="true" %>

  <div class="list">
   <div class="container">
    <div class="row">
       <div class="col-xs-12 col-sm-10 col-md-9 ">
		 <div class="box">
		   <h3 class="text-center"><s:text name="global.create_product"/></h3>
		 </div>
		 <div class="forms">
		   <s:form action="createProduct" theme="simple" namespace="/product" enctype="multipart/form-data">
		   	 <div class="form-group">
		   	   <s:fielderror fieldName="product.productName" cssClass="alert alert-danger"/>
		   	   <s:label for="product.productName"><s:text name="global.product_name"/></s:label>
		       <s:textfield name="product.productName" class="form-control"/>
		     </div>
		     <div class="form-group">
		   	   <s:label for="product.category"><s:text name="global.product_category"/></s:label>&nbsp;
		   	   <s:fielderror fieldName="product.category" cssClass="alert alert-danger"/>
		       <s:select name="category"
		       		     list="#{'ground-attack':'ground-attack','bombers':'bombers','fighters':'fighters','transport':'transport'}" headerKey="-1"
		       />
		     </div>
		   	 <div class="form-group">
		   	   <s:fielderror fieldName="product.description" cssClass="alert alert-danger"/>
		   	   <s:label for="product.description"><s:text name="global.description"/></s:label>
		       <s:textarea name="product.description" class="form-control"/>		       
		     </div>
		   	 <div id="detailsGroup" class="form-group">
		   	   <s:fielderror fieldName="product.details" cssClass="alert alert-danger"/>
		   	   <s:label for="product.details"><s:text name="global.product_details"/></s:label>
	      	 <s:textarea id="detailInput" class="form-control" name="details[0]" placeholder="Add details"/>
		     </div>
	       <s:a href="" id="addDetail" class="btn"><s:text name="global.add_details"/></s:a>
		   	 <div class="form-group">
		   	   <s:label for="productPic"><s:text name="global.product_picture"/></s:label>
		       <s:file name="productPic" accept="image/jpg"/>
		     </div>
		   	 <div class="form-group">
		   	   <s:fielderror fieldName="product.price" cssClass="alert alert-danger"/>
		   	   <s:label for="product.price"><s:text name="global.price"/></s:label>
		       <s:textfield class="form-control" name="product.price"/>
		     </div>
		   	 <div class="form-group">
		       <s:submit key="global.upload"/>
		     </div>
    		 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		   </s:form>
		 </div>
       </div>
       
       <div class="col-xs-12 col-sm-2 col-md-3 "></div>
    </div>
   </div>
  </div>
  <script src="${pageContext.request.contextPath}/js/dynamicInput.js"></script>