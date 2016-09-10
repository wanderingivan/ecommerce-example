<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
    
  <div class="list">
   <div class="container">
    <div class="row">
      <div class="col-xs-12 col-sm-10 col-md-9">
		<div class="box">
		  <h3 class="text-center"><s:text name="global.edit_user"/></h3>
		</div>
		<div class="forms">
		  <s:form action="editUser" namespace="/user" theme="simple" enctype="multipart/form-data">
		   	<s:hidden name="user.id" value="%{user.id}"/>
		   	<div class="form-group">
		   	  <s:fielderror fieldName="user.username" cssClass="alert alert-danger"/>
		   	  <s:label for="user.username"><s:text name="global.username"/></s:label>
		      <s:textfield name="user.username" value="%{user.username}" class="form-control"/>
		    </div>
		    <div class="form-group">
		      <s:label for="email"><s:text name="global.email"/></s:label>
		   	  <s:fielderror fieldName="user.email" cssClass="alert alert-danger"/>
		      <s:textfield name="user.email" value="%{user.email}" class="form-control"/>
		    </div>
		    <div class="form-group">
		      <s:label for="address"><s:text name="global.address"/></s:label>
		   	  <s:fielderror fieldName="user.address" cssClass="alert alert-danger"/>
		      <s:textfield name="user.address" value="%{user.address}" class="form-control"/>
		    </div>
		   	<div class="form-group">
		   	  <s:fielderror fieldName="user.details" cssClass="alert alert-danger"/>
		   	  <s:label for="user.details"><s:text name="global.description"/></s:label>
		      <s:textarea name="user.details" value="%{user.details}" class="form-control"></s:textarea>
		    </div>
		   	<div class="form-group">
		   	  <s:label for="profilePic">Picture</s:label>
		      <s:file accept="image/jpg" name="profilePic"/>
		    </div>
		    <div class="form-group">
		   	  <s:fielderror fieldName="user.password" cssClass="alert alert-danger"/>
		      <s:label for="user.password"><s:text name="global.password"/></s:label>
		      <s:password name="user.password" placeholder="*******" class="form-control"/>
		     </div>
		   	 <div class="form-group">
		       <s:submit value="Upload"/>
		     </div>
		   	 <s:hidden name="user.imagePath" value="%{user.imagePath}"/>
			 <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
    	     <s:token/>
		   </s:form>
 	     </div>
       </div>
       
       <div class="col-xs-12 col-sm-2 col-md-3 "></div>
    </div>
   </div>
  </div>     