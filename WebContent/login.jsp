<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<div class="container" style="min-height:650px;">
 <div class="form-login">
 <div class="row">
  <div class="col-md-2 hidden-sm"></div>
	<div class="col-md-8">
	  <div class="alert alert-info">
	  	Premade accounts are user 1 and user2 with password <i>password</i>
	  </div>
      <div class="errorMessage">
        <s:if test="%{#session.SPRING_SECURITY_LAST_EXCEPTION}">
          <div id="loginError" class="alert alert-warning"><s:text name="global.login_error"/></div>
        </s:if>
      </div>
      <form class="form-horizontal" action="/Ecommerce/login" method="POST" name="login">
        <s:label><s:text name="global.username"/></s:label>
        <div class="form-group">
          <s:textfield id="username" name="username" cssClass="form-control"/>
   	    </div>
        <s:label><s:text name="global.password"/></s:label>
        <div class="form-group">
          <s:password id="password" name="password" cssClass="form-control"/>
        </div>
        <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
        <div class="form-group" style="">
         <div align="center">
	     <s:submit id="loginSubmit" align="center" class="btn btn-add-cart" key="global.log_in"/>
	     </div>
   		</div>
  	  </form>
    </div>
  <div class="col-md-2 hidden-sm"></div>
 </div>
 </div>
</div>