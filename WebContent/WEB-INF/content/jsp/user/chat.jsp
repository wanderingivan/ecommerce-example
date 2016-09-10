<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	      <s:url namespace="/message" action="loadChat" var="reload">
			 <s:param name="chatId" value="%{chat.id}"/>
		  </s:url>
			<div class="chatList">
              <s:form id="chat%{chat.id}addMessage" action="addMessage" namespace="/message" theme="simple" cssClass="form-inline">
                <div class="form-group" style="width:100%; margin-bottom:20px;">
                  <button  type="submit" class="btn btn-primary msg-submit">
                    <i class="fa fa-envelope"></i>
                    <s:property value="getText('global.send')"/>
                  </button>
                  <s:textarea class="form-control share-text msg-textarea" placeholder="%{getText('global.send_message')}" name="message" required="true" maxLength="180"/>
                </div>
			      <s:hidden name="chatId" value="%{chat.id}"/>
			      <s:hidden name="%{#attr._csrf.parameterName}" value="%{#attr._csrf.token}"/>
			  </s:form>
			  <s:iterator value="chat.messages">
			    <s:url action="show" namespace="/user" var="loadUser">
			      <s:param name="username" value="%{sender.username}"/>
			    </s:url>
			    <s:url action="loadImage" namespace="/util" var="loadPic">
  					<s:param name="path" value="%{sender.imagePath}"/>	      
			    </s:url>
			    <div class="media well well-sm">
			      <div class="media-left" style="width:20%;">
			        <img class="img-responsive" style="width:55px;height:55px;border-radius:4px" src="<s:property value='#loadPic'/>" alt="User Profile Image"/> 
			      </div>
			      <div class="media-body" style="width:80%;">
			        <h5 class="media-heading"><s:text name="global.sent_by"/>&nbsp;<s:a href="%{loadUser}">${sender.username}</s:a>&nbsp;<s:date name="sent" nice="true"/></h5>
			        <p><s:property value="message"/></p>
			        <p></p>
			      </div>
			    </div>
			  </s:iterator>
			</div>	
		  <img id="msg-indicator" class="img-responsive"  src="${pageContext.request.contextPath}/img/spiffygif.gif" />