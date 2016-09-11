<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
    
    
  <div class="list">
   <div class="container">
    <div class="row">
      <div class="col-xs-12 col-sm-10 col-md-9 ">
       	<div class="col-md-12">
       	  <ul class="breadcrumb">
       	  	<li><s:a action="welcome" namespace="/">Home</s:a></li>
       	  	<li>${category}</li>
       	  </ul>
       	  <div class="box">
       	    <h3 class="text-uppercase">${category}</h3>
       	    <p class="text-muted">			  
       	     	<s:text name="global.%{category}_desc"/>
       	    </p>
       	  </div>
		</div>
	  </div>
      <div class="col-md-8 col-md-10 col-xs-12">
		<div id="bigCarousel" class="carousel slide" data-ride="carousel">
  		  <!-- Indicators -->
  		  <ol class="carousel-indicators">
    		<li data-target="#bigCarousel" data-slide-to="0" class="active"></li>
    		<li data-target="#bigCarousel" data-slide-to="1"></li>
    		<li data-target="#bigCarousel" data-slide-to="2"></li>
    		<li data-target="#bigCarousel" data-slide-to="3"></li>
  		  </ol>
	      <div class="carousel-inner">
			<s:subset source="products" count="4">
			  <s:iterator status="stat">
		        <s:url value="show" namespace="/product" var="product">
		          <s:param name="productName">
		            <s:property value="productName"/>
		          </s:param>
		        </s:url>
  			    <s:url action="loadImage" namespace="/util" var="loadImage">
    			  <s:param name="path">
      			    <s:property value="imagePath"/>
    			  </s:param>
  			  	</s:url>	
  			  	<s:if test="%{#stat.first}">
				  <s:set var="active">item active</s:set>
			    </s:if>
				<s:else>
				  <s:set var="active">item</s:set>
				</s:else>	
				<s:div cssClass="%{#active}">
				  <img src="<s:property value='#loadImage'/>"/>
				  <div class="carousel-caption">
				    <h3><s:property value="productName"/></h3>
                    <p><s:if test="description.length() > 100">
                         <s:property value="description.substring(0,100)"/>...
                       </s:if>
                       <s:else>
                      	 <s:property value="description"/>
                       </s:else>  
                     </p>
				     <s:a href="%{product}" class="btn-view-me"><s:text name="global.view_me"/></s:a>			
				    </div>
			      </s:div>
			    </s:iterator>
			  </s:subset>
			</div>
       	  </div>
	    </div>
      <div class="col-xs-12 col-sm-2 col-md-3 "></div>
    </div>
    <div class="row">
	  <div class="col-md-12">
	   <s:subset source="products" count="9">
        <s:iterator>
		  <s:url action="show" namespace="/product" var="product">
		    <s:param name="productName" value="%{productName}"/>
		  </s:url>
  	      <s:url action="loadImage" namespace="/util" var="loadImage">
    		<s:param name="path" value="%{imagePath}"/>
  		  </s:url>	
		  <div class="col-xs-12 col-sm-6 col-md-3">
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
  <script src="${pageContext.request.contextPath}/js/jquery.bcSwipe.min.js"></script>
  <script>
	    $('#bigCarousel').bcSwipe({ threshold: 10 });
	    $(document).bind('keyup', function(e) {
	        if(e.which == 39){
	            $('#bigCarousel').carousel('next');
	        }
	        else if(e.which == 37){
	            $('#bigCarousel').carousel('prev');
	        }
	    });
  </script>