<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"
	prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html"
	prefix="html" %>
<div id="menu">
	<div id="menuname">
		<bean:message key="menu.name"/>
	</div>
	<div id="menulist">
		<ul>
			<li>
				<a href="ModuleAction.do?method=list" id="menuref">
						<bean:message key="menu.ref.news_list"/>
				</a>
			</li>
			<li>
				<a href="ModuleAction.do?method=add" id="menuref">
						<bean:message key="menu.ref.add_news"/>
				</a>
			</li>
		</ul>
	</div>
</div>