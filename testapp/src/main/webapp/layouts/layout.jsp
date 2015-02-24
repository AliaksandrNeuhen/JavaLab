<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles"  prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="stylesheet" type="text/css" href="css/appstyle.css">
<script type="text/javascript" src="js/validatemessages.jsp"></script>
 <script type="text/javascript" src="js/validate.js"></script>
<title>News Management</title>
</head>
<body>
	<div id="page">
		<tiles:insert attribute="header" />
		<tiles:insert attribute="menu" />
		<tiles:insert attribute="body" />
		<tiles:insert attribute="footer" />
	</div>
</body>
</html>