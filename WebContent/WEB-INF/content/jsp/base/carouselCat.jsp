<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
           <div class="carousel-inner">
               	<s:set var="cnt" value="1"/>
                <s:iterator status="stat" value="products" begin="0" end="6" step="2">

			      <s:if test="%{#stat.first}">
				    <s:set var="active">
			          item active                  
                    </s:set>
                  </s:if>
                  <s:else>
				    <s:set var="active">
			    	  item                 
                    </s:set>
				  </s:else>
                    <s:div cssClass="%{#active}">
                       <div class="col-md-6 col-sm-6 col-xs-12">
                         <s:url action="loadImage" namespace="/util" var="loadImage">
						   <s:param name="path">
						     <s:property value="imagePath"/>
						   </s:param>
					     </s:url>
		                 <s:url action="show" namespace="/product" var="product">
		                   <s:param name="productName">
		                     <s:property value="productName"/>
		                   </s:param>
		                 </s:url>
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
                             <s:a href="%{product}"><h3><s:property value="productName"/></h3></s:a>
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
	          	  		<s:hidden name="product_id">
	          	  	      <s:param name="value">
                            <s:property value="id"/>
                          </s:param>
                        </s:hidden>
			 			<s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
	          	  		<sj:submit targets="" dataType="json"  onCompleteTopics="" cssClass="btn-add-cart" key="global.add_to_cart"></sj:submit>
	          	  	</s:form>
						 </div> 
                       </div>
                       <div class="col-md-6 col-sm-6 col-xs-12">
					   <s:push value="products[#cnt]">
						 <s:url action="loadImage" namespace="/util" var="loadImage">
			  	           <s:param name="path">
			 		         <s:property value="imagePath"/>
						   </s:param>
					     </s:url>
		                 <s:url action="show" namespace="/product" var="product">
		                   <s:param name="productName">
		                     <s:property value="productName"/>
		                   </s:param>
		                 </s:url>
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
	          	  	         <s:form action="addItem"  theme="simple" namespace="/test">
	          	  		       <s:hidden name="product_id" value="%{id}"/>
			 			       <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
	          	  		       <sj:submit targets="" dataType="json"  onCompleteTopics="" cssClass="btn-add-cart" key="global.add_to_cart"></sj:submit>
	          	   	         </s:form>
						   </div>
						 </div>
					   </s:push>
                       <s:set var="cnt" value="%{#cnt+2}"/>                                           
                      </div>
                    </s:div>
                </s:iterator>
	         </div>