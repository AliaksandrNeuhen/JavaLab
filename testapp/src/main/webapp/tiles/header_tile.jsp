<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<div id="header">
    <html:form action="ModuleAction.do">
        <h1><bean:message key="header.label.news_management"/></h1>
        <div id="changeLanguage"> 
            <html:link page="/ModuleAction.do?method=changeLanguage&language=en">
                <bean:message key="header.label.language_en"/>
            </html:link>
            <html:link page="/ModuleAction.do?method=changeLanguage&language=ru">
                <bean:message key="header.label.language_ru"/>
            </html:link>   
        </div> 
    </html:form>
</div>