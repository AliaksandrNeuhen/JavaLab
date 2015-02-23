<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head><title></title></head>
<body>
<table width="100%">
	<tr><td>
	<tiles:insert attribute="header"/>
	</td></tr>
	<tr><td>
	<tiles:insert attribute="menu"/> 
	</td>
	<td>
	<tiles:insert attribute="body"/>
	</td></tr>
	<tr><td>
	<tiles:insert attribute="footer"/> 
	</td></tr>
</table>
</body>
</html>