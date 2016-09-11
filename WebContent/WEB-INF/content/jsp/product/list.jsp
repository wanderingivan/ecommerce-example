<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page trimDirectiveWhitespaces="true" %>



  <div class="list">
   <div class="container">
    <div class="row">
      <ul class="breadcrumb">
       	<li><s:a action="welcome" namespace="/">Home</s:a></li>
      </ul>
      <div class="col-xs-12 col-sm-10 col-md-9 ">
        <div class="col-md-12">
       	  <div class="box">
       	    <h3>Results for: <span class="text-muted">${query}</span></h3>
       	  </div>
       	  <div class="list-body">
       	    <s:iterator value="products" end="9">
		      <s:url action="show" namespace="/product" var="product">
		        <s:param name="productName">
		          <s:property value="productName"/>
		        </s:param>
		      </s:url>
  			  <s:url action="loadImage" namespace="/util" var="loadImage">
    			<s:param name="path">
      			   <s:property value="imagePath"/>
    			</s:param>
  			  </s:url>	
			  <div class="col-xs-12 col-sm-6 col-md-4">
              <div class="product-block">
                <div class="image-container">
                 <s:a href="%{product}"><img src="<s:property value="#loadImage"/>"/></s:a>
                  <s:if test="isOnSale()">
                    <div class="badge-sale">
                      Sale
                    </div>
                  </s:if>
                </div>
		        <s:a href="%{product}" class="btn-view-me"><s:text name="global.view_me"/></s:a>
                <div class="heading">
                  <s:a href="%{product}">
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
	          	<s:form action="addItem"  theme="simple" namespace="/test">
	          	  <s:hidden name="product_id" value="%{id}"/>
			 	  <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
	          	  <sj:submit targets="" dataType="json"  onCompleteTopics="" cssClass="btn-add-cart" key="global.add_to_cart"></sj:submit>
	          	</s:form>
              </div>                       
			  </div>
			</s:iterator>
       	  </div>
       	</div>
      </div>       
      <div class="col-xs-12 col-sm-2 col-md-3 "></div>
    </div>
   </div>
  </div>