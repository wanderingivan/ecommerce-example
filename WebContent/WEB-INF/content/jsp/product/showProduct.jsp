<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page trimDirectiveWhitespaces="true" %>

  <s:url action="deleteProduct" namespace="/product" var="delete">
		<s:param name="id" value="%{product.id}"/>
		<s:param name="productName" value="%{product.productName}"/>
  </s:url>
  <s:url action="loadEdit" namespace="/product" var="edit">
		<s:param name="productName" value="%{product.productName}"/>
  </s:url>
  <s:url action="show" namespace="/user" var="seller">
    <s:param name="username" value="%{product.seller.username}"/>
  </s:url>
  <s:url action="loadImage" namespace="/util" var="loadImage">
    <s:param name="path" value="%{product.imagePath}"/>
  </s:url>
  <s:url action="category" namespace="/product" var="category">
    <s:param name="category" value="%{product.category}"/>
  </s:url>
  <div class="product">
    <div class="container">
	  <ul class="breadcrumb">
		<li><s:a action="welcome" namespace="/">Home</s:a></li>
		<li><s:a href="%{category}">${product.category}</s:a></li>
		<li>${product.productName}</li>
	  </ul>
      <div class="heading">
	    <div class="row">
		  <div class="col-md-1 hidden-sm"></div>
		  <div class="col-md-10 col-sm-12">
		    <h2>${product.productName}</h2>
			<a class="scroll_to" href="#details"><s:text name="global.scroll_details"/></a>
		  </div>
		  <div class="col-md-1 hidden-sm"></div>
	    </div>
	  </div>		     
      <div class="row">
	    <div class="col-md-12">
	     <ul class="controls-list">
		  <sec:accesscontrollist hasPermission="WRITE" domainObject="${product}">
		    <li><s:a class="btn-add-cart" href="%{edit}"><s:text name="global.edit_product"/></s:a></li>
	      </sec:accesscontrollist>
	      <sec:accesscontrollist hasPermission="DELETE" domainObject="${product}">
		    <li><s:a class="btn-add-cart" href="%{delete}"><s:text name="global.delete_product"/></s:a></li>
	      </sec:accesscontrollist>
	      <sec:authorize access="hasRole('ROLE_ADMIN')">
		    <li><s:a class="btn-add-cart" href="%{edit}"><s:text name="global.edit_product"/></s:a></li>
		    <li><s:a class="btn-add-cart" href="%{delete}"><s:text name="global.delete_product"/></s:a></li>
	        <s:if test="product.featured">
	          <s:url var="featured" namespace="/admin" action="removeFeatured">
	            <s:param name="productName" value="%{productName}"/>
	          </s:url>
		      <li><s:a class="btn-alt" href="%{featured}"><s:text name="global.remove_from_featured"/></s:a></li>
		    </s:if>
		    <s:else>
	          <s:url var="featured" namespace="/admin" action="addFeatured">
	            <s:param name="productName" value="%{productName}"/>
	          </s:url>
		      <li><s:a class="btn-alt" href="%{featured}"><s:text name="global.add_to_featured"/></s:a></li>
		    </s:else>
		    <s:if test="product.onSale">
	          <s:url var="sale" namespace="/admin" action="removeSale">
	            <s:param name="productName" value="%{productName}"/>
	          </s:url>
		      <li><s:a class="btn-alt" href="%{sale}"><s:text name="global.put_off_sale"/></s:a></li>
		    </s:if>
		    <s:else>
	          <s:url var="sale" namespace="/admin" action="addSale">
	            <s:param name="productName" value="%{productName}"/>
	          </s:url>
		      <li><s:a class="btn-alt" href="%{sale}"><s:text name="global.put_on_sale"/></s:a></li>
		    </s:else>
	      </sec:authorize>
	     </ul>
		</div>
      </div>
	  <div class="row">
	    <div class="col-md-12">
		  <div class="row">
			<div class="col-md-9 col-sm-8 col-xs-12">
			  <div class="image-container">
				<img class="product-image" alt="Product Image" src="<s:property value='#loadImage'/>"/>
			  </div>
			</div>
			<div class="col-md-3 col-sm-4 col-xs-12">
			  <div class="box">
				<ul class="box-list">
				  <li>
				    <span class="price">
				      <s:text name="global.money">
					    <s:param name="value" value="%{product.price}"/>				  
				      </s:text>
				    </span>
				  <li>
	          	  	<s:form action="addItem"  theme="simple" namespace="/cart">
	          	  		<s:hidden name="product_id">
	          	  	      <s:param name="value">
                            ${product.id}
                          </s:param>
                        </s:hidden>
			 			<s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
	          	  		<sj:submit targets="" dataType="json"  onCompleteTopics="" cssClass="btn-add-cart" key="global.add_to_cart"></sj:submit>
	          	  	</s:form>
				  <li>
					<div class="info-box">
					  <i class="fa fa-eye"></i>
						${product.hits}&nbsp;<s:text name="global.views"/>
					</div>
				  </li>
				  <li>
					<div class="info-box">
					  <i class="fa fa-archive"></i>
					  ${product.sold}&nbsp;<s:text name="global.sold"/>
					</div>
				  </li>
				</ul>
			  </div>
			</div>
		  </div>
		  <div id="details" class="box">
		    <s:url var="loadReviews" action="loadReviews" namespace="/product">
		      <s:param name="product_id" value="%{product.id}"/>
		    </s:url>
		    <ul class="nav nav-pills" style="margin-bottom:15px">
			  <li class="active"><a data-toggle="tab" class="tab-button" href="#pinfo"><s:text name="global.product_information"/></a></li>
			  <li><a data-toggle="tab" class="tab-button" href="#reviews"><s:text name="global.reviews"/></a></li>
			</ul>
			<div class="row">
			  <div class="col-md-12">
			    <div class="tab-content" style="min-height:400px">
                  <div id="pinfo" class="tab-pane fade in active">
                    <h4><s:text name="global.sold_by"/>&nbsp;<s:a href="%{seller}"><s:property value="product.seller.username"/></s:a></h4>
				    <div class="description text-muted">
				      <p>${product.description}</p>
				    </div>
				    <div class="details">
					  <h3><s:text name="global.product_details"/>:</h3>
					  <ul>
					  <s:iterator value="product.details">
					       <li><s:property /></li>
					  </s:iterator>
					  </ul>
                    </div>
				  </div>
                  <div id="reviews" class="tab-pane fade">
				    <sj:div href="%{loadReviews}"/>
				    <sec:authorize access="isAuthenticated() && hasRole('ROLE_USER')">
                     <div class="row">
                      <div class="col-md-6">
				      <s:form action="addReview" namespace="/product" theme="simple">
				        <s:hidden name="productName" value="%{product.productName}"/>
					    <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
				        <div class="form-group">
				          <s:label cssClass="title" for="message" key="global.add_review"/>
				          <s:textarea name="message" cssClass="form-control"/>
				        </div>
                        <div class="form-group row">
				         <div class="rating col-md-7 col-sm-6 col-xs-12">
                           <span><input type="radio" name="rating" id="str5" value="5"><label for="str5">&#9733;</label></span>
                           <span><input type="radio" name="rating" id="str4" value="4"><label for="str4">&#9733;</label></span>
                           <span><input type="radio" name="rating" id="str3" value="3"><label for="str3">&#9733;</label></span>
                           <span><input type="radio" name="rating" id="str2" value="2"><label for="str2">&#9733;</label></span>
                           <span><input type="radio" name="rating" id="str1" value="1"><label for="str1">&#9733;</label></span>
                         </div>
                         <div class="col-md-5 col-sm-6 col-xs-12">
                           <s:submit cssClass="btn-alt" key="global.send_review"/>
                         </div>
                        </div>
				      </s:form>
                      </div>
                     </div>
				    </sec:authorize>
                 </div>
			   </div>
		     </div>
	 	    </div>
		    <div class="col-md-12 text-center">
	          <s:form action="addItem"  theme="simple" namespace="/cart">
	          	<s:hidden name="product_id">
	          	  <s:param name="value">
                    ${product.id}
                  </s:param>
                </s:hidden>
			    <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
	          	  	<sj:submit targets="" dataType="json"  onCompleteTopics="" cssClass="btn-add-cart" key="global.add_to_cart"></sj:submit>
	          </s:form>
		    </div>	
		  </div>
		  <div class="row">
			<div class="col-md-12">
			  <h3 class="category-title"><s:text name="global.more_in_category"/>:</h3>
		    </div>
		  </div>
		  <div class="row">
		   <s:subset source="products" count="6">
		    <s:iterator >
		      <s:url action="show" namespace="/product" var="product">
		        <s:param name="productName" value="%{productName}"/>
		      </s:url>
  			  <s:url action="loadImage" namespace="/util" var="loadImage">
    			<s:param name="path" value="%{imagePath}"/>
  			  </s:url>		      
			  <div class="col-xs-12 col-sm-6 col-md-3">
              <div class="product-block">
                <div class="image-container">
                 <s:a href="%{#product}"><img src="<s:property value="#loadImage"/>"/></s:a>
                  <s:if test="isOnSale()">
                    <div class="badge-sale">
                      Sale
                    </div>
                  </s:if>
                </div>
		        <s:a href="%{#product}" class="btn-view-me"><s:text name="global.view_me"/></s:a>
                <div class="heading">
                  <s:a href="%{#product}">
                    <s:if test="productName.length() > 30">
                      <h3>${productName.substring(0,30)}...</h3>
                    </s:if>
                    <s:else>
                  	  <h3>${productName}</h3>
                    </s:else>
                  </s:a>
                </div>
                <div class="details">
                  <s:if test="description.length() > 50">
                    <p>${description.substring(0,50)}...</p>
                  </s:if>
                  <s:else>
                    <p>${description}</p>
                  </s:else>
                  <span class="price">				      
                    <s:text name="global.money">
					  <s:param name="value" value="%{price}"/>				  
				    </s:text>
				  </span>
                </div>
	          	<s:form action="addItem"  theme="simple" namespace="/cart">
	          	  <s:hidden name="product_id" value="%{id}"/>
			 	  <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
	          	  <sj:submit targets="" dataType="json"  onCompleteTopics="" cssClass="btn-add-cart" key="global.add_to_cart"></sj:submit>
	          	</s:form>
              </div>                       
			  </div>
            </s:iterator>
           </s:subset>  
		  </div>
		</div>
	  </div>
	</div>
  </div>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/stars.js"></script>