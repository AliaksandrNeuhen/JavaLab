<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<html:html lang="true">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Categories</title>
	</head>
	<body>
		<html:form action="shop.do?command=save" method="POST">
		<table>
			<tr>
				<th>Product Name</th>
    				<th>Provider</th>
    				<th>Model</th>
    				<th>Date of Issue</th>
    				<th>Color</th>
    				<th>Price</th>
			</tr>
			<logic:iterate id="product" name="ProductsForm"
				property="productsDocument.rootElement.children[${ProductsForm.currCategoryIndex}].children[${ProductsForm.currSubcategoryIndex}].children"
					indexId="index">
				<tr>
					<td>
						<html:text name="ProductsForm" property="productsDocument.rootElement.children[${ProductsForm.currCategoryIndex}].children[${ProductsForm.currSubcategoryIndex}].children[${index}].attributes[0].value"/>
					</td>
					<td>
						<html:text name="ProductsForm" property="productsDocument.rootElement.children[${ProductsForm.currCategoryIndex}].children[${ProductsForm.currSubcategoryIndex}].children[${index}].child(provider).text"/>
					</td>
					<td>
						<html:text name="ProductsForm" property="productsDocument.rootElement.children[${ProductsForm.currCategoryIndex}].children[${ProductsForm.currSubcategoryIndex}].children[${index}].child(model).text"/>
					</td>
					<td>
						<html:text name="ProductsForm" property="productsDocument.rootElement.children[${ProductsForm.currCategoryIndex}].children[${ProductsForm.currSubcategoryIndex}].children[${index}].child(date-of-issue).text"/>
					</td>
					<td>
						<html:text name="ProductsForm" property="productsDocument.rootElement.children[${ProductsForm.currCategoryIndex}].children[${ProductsForm.currSubcategoryIndex}].children[${index}].child(color).text"/>
					</td>
					<td>
						<logic:empty name="product" property="child(price)">
							N/A
						</logic:empty>
						<logic:notEmpty name="product" property="child(price)">
							<html:text name="ProductsForm" property="productsDocument.rootElement.children[${ProductsForm.currCategoryIndex}].children[${ProductsForm.currSubcategoryIndex}].children[${index}].child(price).text"/>
						</logic:notEmpty>
					</td>
				</tr>
			</logic:iterate>
		</table>
		<html:submit>Save</html:submit>
		</html:form>
		<form action="xslt?command=add_product" method="POST">
			<html:submit>New Product</html:submit>
		</form>
		
	</body>
</html:html>