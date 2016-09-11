<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>



                   <div class="row">
                    <div class="col-md-6">
				    <s:iterator value="reviews" end="10">
				     <s:url action="loadImage" namespace="/util" var="userImage">
				       <s:param name="path" value="%{user.imagePath}"/>
				     </s:url>
				     <s:url action="show" namespace="/user" var="loadUser">
				       <s:param name="username" value="%{user.username}"/>
				     </s:url>
				     <div class="well well-sm">
				       <div class="media">
				         <div class="media-left">
				             <s:a href="%{loadUser}"><img src="<s:property value='#userImage'/>" alt="User image"/></s:a>
				         </div>
				         <div class="media-body">
				           <h4 class="media-heading"><s:text name="global.review_by"/>&nbsp;<s:a href="%{loadUser}"><s:property value="user.username"/></s:a></h4>
				           <p ><s:property value="message"/></p>
                           <p><s:text name="global.rating"/>:</p>
                           <s:iterator begin="1" end="5" status="stat">
                              
                              <s:if test="#stat.count <= rating">
                                <span style="color:#F90;">&#9733;</span>
                              </s:if>
                              <s:else>
                                &#9733;
                              </s:else>
                           </s:iterator>
                           <p class="text-muted"><s:date name="posted" nice="true"/></p>
				         </div>
				       </div>
				     </div>
				    </s:iterator>
                    </div>
                   </div>
                   <s:if test="reviews.size() == 0">
				     <div class="well well-sm">
						<p style="color:#22a17a;text-align:center;"><s:text name="global.no_reviews"/></p>
					 </div>                   
                   </s:if>
