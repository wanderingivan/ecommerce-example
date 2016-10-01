<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	
	<s:url action="deleteUser" namespace="/user" var="delete">
		<s:param name="user_id">
		  <s:property value="user.id"/>
		</s:param>
	</s:url>
	<s:url action="loadEdit" namespace="/user" var="edit">
		<s:param name="username">
		  <s:property value="user.username"/>
		</s:param>
	</s:url>
	<s:url action="loadImage" namespace="/util" var="profileImage">
		<s:param name="path">
			<s:property value="user.imagePath"/>
		</s:param>
	</s:url>
	
	<div class="product">
	  <div class="container">
	    <ul class="breadcrumb">
	    	<li><s:a action="welcome" namespace="/">home</s:a></li>
	    	<li><s:property value="user.username"/></li>
	    </ul>
	    <div class="heading">
	      <div class="row">
	      	<div class="col-md-1 hidden-sm"></div>
	      	<div class="col-md-10 col-sm-12">
	      	  <h2><s:text name="global.user_profile"/></h2>
	      	</div>
	      	<div class="col-md-1 hidden-sm"></div>
	      </div>
	    </div>
	      <div class="row">
	        <div class="col-md-9 col-sm-12">
	          <div class="row">
	        	<div class="col-md-6 col-sm-9 col-xs-12">
	        	  <div class="image-container user">
	        	    <img src="<s:property value='#profileImage'/>" alt="User image"></img>
	        	  </div>
	        	</div>
	        	<div class="col-md-6 col-sm-9 col-xs-12">
	        	  <div class="box">
	        	    <h4><s:text name="global.about_me"/></h4>
	        	  	<p><s:property value="user.details"/></p>
	        	  </div>
	        	</div>
	          </div>
	          <sec:accesscontrollist hasPermission="READ" domainObject="${user}">
	            <div class="box">
	              <s:url action="getOrders" namespace="/order" var="orders"/>
	              <s:url action="getAllOrdersXls" namespace="/order" var="ordersXls"/>
	          	  <ul class="nav nav-pills">
	          	    
	          	    <li class="active">
	          	  	  <sj:a href="%{orders}"><s:text name="global.get_orders"/></sj:a>
	          	    </li>
	          	    <li class="active">
		              <s:a href="%{ordersXls}"><s:text name="global.get_orders_xls"/></s:a>
	          	    </li>
	          	  </ul>
	          	  <sj:div href="%{orders}" cssClass="well"/>
	            </div>
              </sec:accesscontrollist>
	        </div>
	        <div class="col-md-3 col-sm-12">
	          <ul class="box-list">
	          <sec:authorize access="isAuthenticated() AND (authentication.name != '${user.username}')">
	            <li id="msgButton"><button  class="btn-alt"><s:text name="global.send_message"/></button></li>
	            <li id="msgForm" style="display:none"><s:form  theme="simple" action="sendMessage" namespace="/message" method="POST">
	            	  <s:hidden name="receiver" value="%{user.username}"/>
		 			  <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
	            	  <s:textarea name="message" placeholder="Your message"/>
	            	  <s:submit cssClass="btn-alt" key="global.send_message"/>
	                </s:form>
	            </li>
	          </sec:authorize>
	          <sec:accesscontrollist hasPermission="WRITE" domainObject="${user}">
		        <li><s:a href="%{edit}" cssClass="btn-add-cart"><s:text name="global.edit_your_profile"/></s:a></li>
		        <li><s:a action="changePasswordPage" namespace="/user" cssClass="btn-alt"><s:text name="global.change_your_password"/></s:a>
	          </sec:accesscontrollist>
	          <sec:authorize access="hasRole('ROLE_ADMIN')">
		        <li><s:a href="%{edit}" class="btn-add-cart"><s:text name="global.edit_user"/></s:a></li>
		        <li><s:a href="%{delete}" class="btn-add-cart"><s:text name="global.delete_profile"/></s:a></li>
		        <li><s:form action="changeRole" namespace="/admin" theme="simple">
		        	  <s:hidden name="username" value="%{user.username}"/>
		 			  <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
	  		   	      <s:fielderror fieldName="role" cssClass="alert alert-danger"/>
		              <s:select name="role" 
		        		        list="#{'user':'user','writer':'writer','admin':'admin'}"
								headerKey="-1"/>
					  <s:submit cssClass="btn-alt" key="global.change_role"/>
		            </s:form> 
                </li>
                <li>
                 <s:if test="user.enabled">
                   <s:url action="disable" namespace="/admin" var="disable">
                     <s:param name="username" value="%{user.username}"/>
                   </s:url>
                   <s:a href="%{disable}" cssClass="btn-alt"><s:text name="global.disable"/></s:a>
                 </s:if>
                 <s:else>
                   <s:url action="enable" namespace="/admin" var="enable">
                     <s:param name="username" value="%{user.username}"/>
                   </s:url>
                   <s:a href="%{enable}" cssClass="btn-alt"><s:text name="global.enable"/></s:a>
                 </s:else>  
                </li>
	          </sec:authorize>
	          </ul>
	        </div>
	      </div>
	      <div class="row">
	        <div class="col-md-12">
	          <h3 class="category-title"><s:text name="global.sold_by_this_user"/></h3>
	        </div>
	      </div>
	      <div class="row">
	           <s:subset  source="user.sale" count="7">
	            <s:iterator>
	              <s:url action="show" namespace="/product" var="showProduct">
		            <s:param name="productName">
		              <s:property value="productName"/>
		            </s:param>
		          </s:url>
  			      <s:url action="loadImage" namespace="/util" var="loadImage">
    			    <s:param name="path">
      			      <s:property value="imagePath"/>
    			    </s:param>
  			      </s:url>	
			  	  <div class="col-xs-12 col-sm-6 col-md-3">
              	    <div class="product-block">
                	  <div class="image-container">
                 	    <s:a href="%{showProduct}"><img src="<s:property value="#loadImage"/>"/></s:a>
                        <s:if test="isOnSale()">
                          <div class="badge-sale">
                            Sale
                          </div>
                        </s:if>
                      </div>
		              <s:a href="%{showProduct}" class="btn-view-me"><s:text name="global.view_me"/></s:a>
                      <div class="heading">
                        <s:a href="%{showProduct}">
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
                        <span class="price">$ ${price}</span>
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
	