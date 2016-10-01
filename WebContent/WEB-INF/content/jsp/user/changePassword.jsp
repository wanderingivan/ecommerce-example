<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<div class="container" style="min-height:650px;">
 <div class="form-login">
   <div class="row">
   <div class="col-md-2 hidden-sm"></div>
	 <div class="col-md-8">
       <s:form class="form-horizontal" action="changePassword" namespace="/user" method="POST" name="login" theme="simple">

         <s:label><s:text name="global.old_password"/></s:label>
         <div class="form-group">
           <s:fielderror fieldName="oldPassword" cssClass="alert alert-danger"/>
           <s:password name="oldPassword" cssClass="form-control"/>
         </div>
   	     
         <s:label><s:text name="global.new_password"/></s:label>
         <div class="form-group">
           <s:fielderror fieldName="newPassword" cssClass="alert alert-danger"/>
           <s:password name="newPassword" cssClass="form-control"/>
         </div>
         
         <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
         <div class="form-group" style="">
          <div align="center">
	       <s:submit align="center" class="btn btn-add-cart" key="global.change_password"/>
	      </div>
   		 </div>
  	   </s:form>
     </div>
   <div class="col-md-2 hidden-sm"></div>
   </div>
 </div>
</div>