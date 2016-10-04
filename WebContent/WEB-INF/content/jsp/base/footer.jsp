<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


  <div class="container-fluid" style="background-color : #fff">
    <div class="footer">
      <div class="links">
        <div class="row">
    	  <div class="col-sm-6 col-md-4 col-md-offset-1">
    	    <h3>Information</h3>
    	    <ul>
    	      <li><a href="http://github.com/wanderingivan">About Me</a></li>
    	      <li><s:a action="photoCredits" namespace="/">Photo Credits</s:a></li>   	
    	    </ul>
    	  </div>
    	  <div class="col-sm-6 col-md-4">
    	    <h3>Account</h3>
    	    <ul>
		     <sec:authorize access="isAuthenticated()">
		        <li><s:a href="%{account}">My Account</s:a></li>
			    <li><s:a action="loadCreate" namespace="/product">Create Ad</s:a></li>
			    <li><s:a href="%{checkout}">Checkout</s:a></li>
			 </sec:authorize>
			 <sec:authorize access="isAnonymous()">
			    <li><s:a action="loadCreate" namespace="/user">My Account</s:a></li>
			    <li><s:a action="login" namespace="/user">Shopping Cart</s:a></li>		 
			 </sec:authorize>
    	    </ul>
    	  </div>
    	  <div class="col-sm-6 col-md-3">
    	    <h3>Made possible by:</h3>
    	    <ul>
			  <li><a href="http://struts.apache.org/">Struts 2</a></li>
			  <li><a href="https://spring.io/">Spring</a></li>
			  <li><a href="https://tiles.apache.org/">Tiles</a></li>
    	    </ul>
    	  </div>
        </div>
      </div>
    </div>  
  </div> 