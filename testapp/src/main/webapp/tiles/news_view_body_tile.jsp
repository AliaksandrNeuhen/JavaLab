<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<div id="content">
	<div id="contentpath">
		<a href="ModuleAction.do?method=list" id="contentpathlink">
		<bean:message key="label.content_path"/>
		</a><bean:message key="label.view.content_path"/>
	</div>
<html:form action="ModuleAction.do">
<nested:nest property="newsMessage">
<html:hidden property="id" value="${NewsForm.newsMessage.id}"/>
<div id="viewtable">
	<div id="titlerow">
		<div id="tableinfocolumn">
			<bean:message key="label.title"/>
		</div>
		<div id="titleview">
			<nested:write property="title"/>
		</div>
	</div>
	<div id="daterow">
		<div id="tableinfocolumn">
			<bean:message key="label.date"/>
		</div>
		<div id="dateview">
			<fmt:formatDate value="${NewsForm.newsMessage.editionDate }" pattern="MM/dd/yyyy"/>
		</div>
	</div>
	<div id="briefrow">
		<div id="tableinfocolumn">
			<bean:message key="label.brief"/>
		</div>
		<div id="briefview">
			<nested:write property="brief"/>
		</div>
	</div>
	<div id="contentrow">
		<div id="tableinfocolumn">
			<bean:message key="label.content"/>
		</div>
		<div id="contentview">
			<nested:write property="content"/>
		</div>
	</div>
</div>
</nested:nest>
</html:form>
	
	<div id="viewbuttonsarea">
	<html:form action="ModuleAction.do" styleId="editform">
			<html:hidden property="id" value="${NewsForm.newsMessage.id}"/>
			<input type="hidden" name="method" value="edit"/>
			<html:submit styleId="viewbuttons">
				<bean:message key="button.edit"/>
			</html:submit>	
	</html:form>
	<html:form action="ModuleAction.do" styleId="deleteform">
			<html:hidden property="id" value="${NewsForm.newsMessage.id}"/>
			<input type="hidden" name="method" value="delete"/>
			<html:submit styleId="viewbuttons" onclick="return (checkDeleteSingle(this.form));"><bean:message key="button.delete"/></html:submit>		
	</html:form>
	</div>

</div>