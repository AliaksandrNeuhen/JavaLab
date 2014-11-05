<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<div id="content">
	<div id="contentpath">
		<a href="ModuleAction.do?method=list" id="contentpathlink">
		<bean:message key="label.content_path"/>
		</a><bean:message key="label.list.content_path"/>
	</div>
		<html:form action="ModuleAction.do" method="post">
		<logic:iterate id="item" name="NewsForm" property="newsList">
			<div id="news">
				<div id="titleanddate">
					<div id="column1">
						<strong><bean:write name="item" property="title"/></strong>
					</div>
					<div id="column2">
						<fmt:formatDate value="${item.editionDate }" 
										pattern="MM/dd/yyyy" />
					</div>
				</div>
				<div id="newscontent">
					<div id="column1">
							<bean:write name="item" property="brief" />		
					</div>
				</div>
				<div id="listviewcheckbox">
					<div id="listrefs">
					<html:link action="ModuleAction.do?method=view" paramId="newsMessage.id" paramName="item"
      							paramProperty="id"><bean:message key="body.ref.view" /></html:link>
      				<html:link action="ModuleAction.do?method=edit" paramId="newsMessage.id" paramName="item"
      							paramProperty="id"><bean:message key="body.ref.edit" /></html:link>
						<html:checkbox property="newsToDelete" name="NewsForm" value="${item.id }" style="margin-left: 20px"></html:checkbox>
					</div>
				</div>
			</div>		
		</logic:iterate>
		<logic:notEmpty name="NewsForm" property="newsList">
			<input type="hidden" name="method" value="delete"/>
			<div id="deletebutton">
				<html:submit onclick="return checkDelete(this.form);"><bean:message key="button.delete"/></html:submit>
			</div>
		</logic:notEmpty>
		</html:form>
</div>