<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"
	prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html"
	prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-nested"
	prefix="nested" %>
<div id="content">
	<div id="contentpath">
		<a href="ModuleAction.do?method=list" id="contentpathlink">
		<bean:message key="label.content_path"/>
		</a><bean:message key="label.edit.content_path"/>
	</div>
<html:form action="/ModuleAction.do" method="post">
<nested:nest property="newsMessage">
	<div id="newsedittable">
	<div id="titleeditrow">
		<div id="addcolumn1">
			<bean:message key="label.title"/>
		</div>
		<div id="titleeditcolumn">
			<nested:text property="title" styleId="textedittitle"/>
			<p><nested:errors property="title"/></p>
		</div>
	</div>
	<div id="dateeditrow">
		<div id="addcolumn1">
			<bean:message key="label.date"/>
		</div>
		<div id="dateeditcolumn">
			<input type="text" id="texteditdate" name="newsMessage.editionDate"
				value="<fmt:formatDate value="${NewsForm.newsMessage.editionDate }" pattern="MM/dd/yyyy"/>"
				maxlength="10"/>
				<p><nested:errors property="editionDate"/></p>
		</div>
	</div>
	<div id="briefeditrow">
		<div id="addcolumn1">
			<bean:message key="label.brief"/>
		</div>
		<div id="briefeditcolumn">
			<nested:textarea property="brief" styleId="textareaeditbrief"/>
			<p><nested:errors property="brief"/></p>
		</div>
	</div>
	<div id="contenteditrow">
		<div id="addcolumn1">
			<bean:message key="label.content"/>
		</div>
		<div id="contenteditcolumn">
			<nested:textarea property="content" styleId="textareaeditcontent"/>
			<p><nested:errors property="content"/></p>
		</div>
	</div>
</div>
	<div id="editbuttonsarea">
		<input type="hidden" name="method" value="save" id="method"/>
		<html:submit styleId="viewbuttons" onclick="return validateNews(this);"><bean:message key="button.submit"/></html:submit>
		<html:submit onclick="return checkCancel(this);" styleId="cancelbutton"><bean:message key="button.cancel"/></html:submit>
	</div>
</nested:nest>
</html:form>
</div>
<script type="text/javascript" src="js/editpagehelper.js"></script>