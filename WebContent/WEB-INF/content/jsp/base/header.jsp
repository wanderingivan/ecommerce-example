<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>
	
  <s:url id="localeEN" includeParams="get">
    <s:param name="request_locale">en</s:param>
  </s:url>
  <s:url id="localeBG" includeParams="get">
    <s:param name="request_locale">bg</s:param>
  </s:url>
  <sec:authorize access="isAuthenticated()">
    <s:url action="show" namespace="/user" var="account">
      <s:param name="username">
        <sec:authentication property="principal.username"/>
      </s:param>
    </s:url>
    <s:url action="cart" namespace="/cart" var="cart"/>
    <s:url action="checkout" namespace="/order" var="checkout"/>
  </sec:authorize>
  <div class="header">
	<div class="topnav">
      <div class="container">
        <div class="row">
          <div class="col-md-1"></div>
          <div class="col-md-10">
		    <ul class="list-inline text-center">
		     <sec:authorize access="isAuthenticated()">
		        <li><s:a href="%{account}"><i class="fa fa-user"></i><s:text name="global.my_account"/></s:a></li>
			    <li><s:a action="loadCreate" namespace="/product"><i class="fa fa-file"></i><s:text name="global.create_ad"/></s:a></li>
			    <li><s:a href="%{checkout}"><i class="fa fa-shopping-cart"><s:text name="global.checkout"/></i></s:a></li>
			    <sec:authorize access="hasRole('ROLE_ADMIN')"><li><s:a action="welcome" namespace="/admin">Admin Page</s:a></li></sec:authorize>
			 </sec:authorize>
			 <sec:authorize access="isAnonymous()">
			    <li><s:a action="loadCreate" namespace="/user"><i class="fa fa-user"></i><s:text name="global.my_account"/></s:a></li>
			    <li><s:a action="login" namespace="/user"><i class="fa fa-shopping-cart"><s:text name="global.shopping_cart"/></i></s:a></li>		 
			 </sec:authorize>
			    <li><s:a href="%{localeEN}" >English</s:a></li>
	            <li><s:a href="%{localeBG}" >Български</s:a></li>
		    </ul>
	      </div>
          <div class="col-md-1"></div>
        </div>
      </div>
    </div>
	<div  class="subnav">
      <navbar class="navbar navbar-default">
  	    <div class="container-fluid">
    	  <!-- Brand and toggle get grouped for better mobile display -->
    	  <div class="navbar-header">
      	    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#subnav" aria-expanded="false">
          	  <span class="sr-only">Toggle navigation</span>
        	  <span class="icon-bar"></span>
        	  <span class="icon-bar"></span>
        	  <span class="icon-bar"></span>
      		</button>
      		<s:a action="welcome" namespace="/" class="navbar-brand">EcommerceSite</s:a>
    	  </div>
    	  <div id ="subnav" class="collapse navbar-collapse">
      		<ul id="left" class="nav navbar-nav">
      		  <s:iterator value="{'fighters','ground-attack','bombers','transport'}">
      		    <s:url action="category" namespace="/product" var="loadCategory">
      		    	<s:param name="category">
      		    	  <s:property />
      		    	</s:param>
      		    </s:url>
      		    <li><s:a href="%{loadCategory}" cssClass="btn-subnav text-uppercase"><s:property/></s:a></li>
      		  </s:iterator>
            </ul>
            <ul class="nav navbar-nav">
			  <li>
      		    <s:form id="nav-form" class="navbar-form navbar-left" 
      		    		role="search" action="search" namespace="/" theme="simple">
        		  <div class="form-group">
          		    <i><s:textfield name="query" class="form-control" placeholder="%{getText('global.search_products')}"/></i>
          		    <s:hidden name="type" value="product"/>
	                <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
        		  </div>
        		  <s:submit class="btn btn-subnav" key="global.search"/>
      			</s:form>
			  </li>
			  <sec:authorize access="isAuthenticated()">
			  <li class="dropdown">
			    <a href="#" class="btn-cart dropdown-toggle" type="button" 
			           id="dropdownMenuCart" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			      <s:text name="global.cart"/>  
			    </a>
			    <ul id="cartDropdown" class="dropdown-menu" aria-labelledby="dropdownMenu1">
			      <img src="../img/spiffygif.gif"></img>
			    </ul>
			  </li>
			  <li class="dropdown">
			    <s:url action="unread-count" namespace="/message" var="countUnread"/>
			    <a href="#" class="btn-cart dropdown-toggle" type="button" 
			           id="dropdownMenuMessages" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				    <sj:div id="unread" href="%{countUnread}" onSuccessTopics="addUnreadCount"/>  
			    </a>
			    <ul id="messagesDropdown" class="dropdown-menu" aria-labelledby="dropdownMenu2">
			      <img src="../img/spiffygif.gif"></img>
			    </ul>
			  </li>
		      <li>
		        <form action="/Ecommerce/logout" class="navbar-form" method="POST">
  				  <s:submit id="logoutSubmit" class="btn btn-subnav" type="submit" key="global.log_out"/>
    			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    </form>
			  </li>			  
			  </sec:authorize>
			  <sec:authorize access="isAnonymous()">
			  <li><s:a action="login" namespace="/user" cssClass="btn btn-subnav"><s:text name="global.log_in"/></s:a></li>
			  <li><s:a action="loadCreate" namespace="/user" cssClass="btn btn-subnav"><s:text name="global.sign_up"/></s:a></li>
			  </sec:authorize>
			</ul>
          </div>
        </div>
      </navbar>
  	</div>
  </div>