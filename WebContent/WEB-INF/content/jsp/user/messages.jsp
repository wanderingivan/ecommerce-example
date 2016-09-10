<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="container">
 <div class="messages-main">
  <div class="row">
       <div class="col-md-4 col-sm-3 col-xs-12">
		<ul class="user-messages">
		  <s:iterator value="chats" status="stat">
            <s:url action="show" namespace="/user" var="loadUser">
              <s:param name="username" value="messages.get(0).sender.username"/>
            </s:url>
            <s:url action="loadImage" namespace="/util" var="loadImage">
              <s:param name="path" value="messages.get(0).sender.imagePath"/>
			</s:url>
            <s:url action="loadChat" namespace="/message" var="loadChat">
              <s:param name="chatId" value="%{id}"/>
            </s:url>
            <li>
              <div class="media">
                <div class="media-left">
 		  	      <sj:a id="linkchat%{id}" 
				    href="%{loadChat}" 
				    targets="results" 
				    indicator="msg-indicator"  
			      >
		  	        <img class="img-responsive" style="width:100px;height:100px;border-radius:4px" src="<s:property value='#loadImage'/>" alt="User Profile Image"/>
			      </sj:a>
                </div>
                <div class="media-body hidden-sm hidden-xs" style="width:65%;">
                  <sj:a id="linkchat%{id}_2" 
				    href="%{loadChat}" 
				    targets="results" 
				    indicator="msg-indicator"  
			      >
		  	        <h4><s:text name="global.chat_with"/>&nbsp;<s:property value="messages.get(0).sender.username"/></h4>
		  	      </sj:a>
                  <p>${messages.get(0).getMessage()}</p>
                  <p><s:date name="messages.get(0).getSent()" nice="true"/></p>
                </div>
              </div>
              <s:if test="#stat.isFirst()">
		        <s:url action="loadChat" namespace="/message" var="mostRecent">
		          <s:param name="chatId" value="%{id}"/>
		        </s:url>
			  </s:if>
			</li>
	       </s:iterator>
		 </ul>
        </div>
		<s:if test="chats == null">
	      <p><s:text name="global.no_messages"/></p>
		</s:if>
        <div class="col-md-offset-1 col-md-6 col-sm-9 col-xs-12">
		  <sj:div id="results" href="%{mostRecent}">
		    <img id="msg-indicator" class="img-responsive" src="${pageContext.request.contextPath}/img/spiffygif.gif" />
		  </sj:div>
        </div>
  </div>
 </div>
</div>