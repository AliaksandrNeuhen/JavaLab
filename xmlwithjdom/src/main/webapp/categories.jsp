<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
 
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
 			<nested:root name="ProductsForm">
 			<nested:nest property="productsDocument.rootElement">
 			<nested:iterate id="category" property="children"
 					indexId="index">
 				<tr>
 					<td>
 						<a href="shop.do?command=showSubcategories&categoryname=${category.attributes[0].value}">
 						<bean:write name="category" property="attributes[0].value"/>
 						</a>
 					</td>
 					<td>
 						<bean:define id="size" value="0"></bean:define>
 						<nested:iterate id="subcategory" property="children">
 							<nested:size id="subcategorySize" property="children" />
  							<bean:define id="size" value="${size + subcategorySize}"/>
 						</nested:iterate>
 						<bean:write name="size"/>
 					</td>
 				</tr>
 			</nested:iterate>
 			</nested:nest>
 			</nested:root>
 		</table>
 	</body>
 </html>