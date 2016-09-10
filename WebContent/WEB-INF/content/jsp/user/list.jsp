<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page trimDirectiveWhitespaces="true" %>



  <div class="list">
   <div class="container">
    <div class="row">
      <ul class="breadcrumb">
       	<li><s:a action="welcome" namespace="/">Home</s:a></li>
      </ul>
      <div class="col-xs-12 col-sm-10 col-md-9 ">
        <div class="col-md-12">
       	  <div class="box">
       	    <h3>Results for: <span class="text-muted">${query}</span></h3>
       	  </div>
       	  <div class="list-body">
       	   <ul style="list-style:none;">
       	    <s:iterator value="users" end="9">
		      <s:url action="show" namespace="/user" var="user">
		        <s:param name="username" value="%{username}"/>
		      </s:url>
  			  <s:url action="loadImage" namespace="/util" var="loadImage">
    			<s:param name="path" value="%{imagePath}"/>
  			  </s:url>	
  			  <li>
              <div class="media" style="margin-bottom:20px;">
               <div class="media-left">
                 <div class="image-container">
                   <s:a href="%{user}"><img src="<s:property value="#loadImage"/>"/></s:a>
                 </div>
               </div>
               <div class="media-body">
                 <s:a href="%{user}"><h4 class="media-heading">${username}</h4></s:a>
                 <p class="text-muted">${details}</p>
               </div>
              </div>
              </li>                       
			</s:iterator>
			</ul>
       	  </div>
       	</div>
      </div>       
      <div class="col-xs-12 col-sm-2 col-md-3 "></div>
    </div>
   </div>
  </div>