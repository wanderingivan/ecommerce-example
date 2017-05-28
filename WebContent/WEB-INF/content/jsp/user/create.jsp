<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
    
  <div class="list">
   <div class="container">
    <div class="row">
      <div class="col-xs-12 col-sm-10 col-md-9">
		<div class="box">
		  <h3 class="text-center"><s:text name="global.create_user"/></h3>
		</div>
	    <div class="alert alert-info">
	  	  Premade accounts are user 1 and user2 with password <i>password</i>
	    </div>
		<div class="forms">
		  <s:form action="createUser" theme="simple" namespace="/user" enctype="multipart/form-data">
		   	<div class="form-group">
		   	  <s:fielderror id="usernameError" fieldName="user.username" cssClass="alert alert-danger"/>
		   	  <s:label for="user.username"><s:text name="global.username"/></s:label>
		      <s:textfield id="username" name="user.username" class="form-control"/>
		    </div>
		    <div class="form-group">
		   	  <s:fielderror id="emailError" fieldName="user.email" cssClass="alert alert-danger"/>
		      <s:label for="user.email"><s:text name="global.email"/></s:label>
		      <s:textfield id="email" name="user.email" class="form-control"/>
		    </div>
		    <div class="form-group">
		   	  <s:fielderror id="addressError" fieldName="user.address" cssClass="alert alert-danger"/>
		      <s:label for="user.address"><s:text name="global.address"/></s:label>
		      <s:textfield id="address" name="user.address" class="form-control"/>
		    </div>
		   	<div class="form-group">
		   	  <s:fielderror id="descriptionError" fieldName="user.details" cssClass="alert alert-danger"/>
		   	  <s:label for="user.details"><s:text name="global.description"/></s:label>
		      <s:textarea id="description" name="user.details" class="form-control"></s:textarea>
		    </div>
		   	<div class="form-group">
		   	  <s:label for="profilePic"><s:text name="global.profile_pic"/></s:label>
		      <s:file  name="profilePic" accept="image/jpg"/>
		    </div>
		    <div class="form-group">
		   	  <s:fielderror id="passwordError" fieldName="user.password" cssClass="alert alert-danger"/>
		      <s:label for="user.password"><s:text name="global.password"/></s:label>
		      <s:password id="password" name="user.password" placeholder="*******" class="form-control"/>
		     </div>
		   	 <div class="form-group">
		       <s:submit id="createSubmit" key="global.create"/>
		     </div>
			 <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
		   </s:form>
 	     </div>
       </div>
       
       <div class="col-xs-12 col-sm-2 col-md-3 "></div>
    </div>
   </div>
  </div>     