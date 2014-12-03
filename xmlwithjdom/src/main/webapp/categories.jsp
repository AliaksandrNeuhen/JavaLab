<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
 
 <html>
 	<head>
 		<title>Categories</title>
 		<link rel="stylesheet" type="text/css" href="css/appstyle.css"/>
 	</head>
 	<body>
 		<table>
 			<tr>
 				<th>Category Name</th>
 				<th>Number Of Products</th>
 			</tr>
 			<logic:iterate id="category" name="ProductsForm" property="productsDocument.rootElement.children"
 					indexId="index">
 				<tr>
 					<td>
 						<a href="shop.do?command=showSubcategories&categoryname=${category.attributes[0].value}"><bean:write name="category" property="attributes[0].value"/></a>
 					</td>
 					<td>
 						<logic:iterate id="count" name="ProductsForm" property="countOfProducts" offset="index" length="1">
	 						<bean:write name="count" />
 						</logic:iterate>
 					</td>
 				</tr>
 			</logic:iterate>
 		</table>
 	</body>
 </html>