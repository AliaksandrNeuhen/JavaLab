<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
	<head>
		<title>Subcategories</title>
		<link rel="stylesheet" type="text/css" href="css/appstyle.css"/>
	</head>
	<body>
		<table>
			<tr>
				<th>Subcategory name</th>
				<th>Number of products</th>
			</tr>
			<logic:iterate id="subcategory" name="ProductsForm" 
				property="productsDocument.rootElement.children[${ProductsForm.currCategoryIndex}].children"
				indexId="index">
				<tr>
					<td>
						<a href="shop.do?command=showProducts&subcategoryname=${subcategory.attributes[0].value}">
							<bean:write name="subcategory" property="attributes[0].value"/>
						</a>
					</td>
					<td>
						<bean:size id="subcategorySize" name="subcategory" property="children"/>
						<bean:define id="size" value="${subcategorySize}"/>
						<bean:write name="size"/>
					</td>
				</tr>
			</logic:iterate>
		</table>
		<form action="shop.do" method="POST">
			<input type="hidden" name="command" value="back"/>
			<input type="hidden" name="page" value="categories"/>
			<input type="submit" value="Back" class="backButton"/> 
		</form>
	</body>
</html>