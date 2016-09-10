<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


 <div class="container">
  <div class="row">
    <div class="col-md-12">
	  <h3 class="text-center" style="color:#22a17a;"><s:text name="global.please_fill_out_forms"/></h3>
	</div>
  </div>
  <div class="row">
   <div class="col-md-4 col-sm-6 col-xs-12">
	<div class="forms">
	<s:form id="user" action="order" namespace="/order" theme="simple" cssClass="form-horizonthal" method="POST">
	    <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
		<div class="form-group">
		  <s:label for="address" key="global.address"/>
		  <s:fielderror name="address"/>
		  <s:textarea name="address" cssClass="form-control" label="address">
			<s:param name="value" value="%{user.address}"/>
		  </s:textarea>		
		</div>
	 	<div class="form-group">
	 	  <s:label for="cardNumber" key="global.card_number"/>
	 	  <s:fielderror name="cardNumber"/>
		  <s:textfield name="cardNumber" cssClass="form-control" label="Card Number"/>
		</div>
        <div class="form-group pull-right">
          <s:submit cssClass="btn-alt" key="global.place_order"/>
        </div>
	</s:form>
	</div>
  </div>
  </div>
  </div>