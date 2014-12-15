<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<html:html lang="true">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/appstyle.css"/>
		<script type="text/javascript" src="js/validate.js"></script>
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
			<nested:nest property="productsDocument.rootElement.children[${ProductsForm.currCategoryIndex}].children[${ProductsForm.currSubcategoryIndex}]">
			<nested:iterate id="product" property="children"
					indexId="index">
				<tr>
					<td>
						<nested:text property="attributes[0].value"
									onblur="validateName(this, ${index});" styleClass="validationField"/>
						<div id="nameError${index}" class="popupMessage"></div>
					</td>
					<td>
						<nested:text property="child(provider).text"
									onblur="validateProvider(this, ${index});" styleClass="validationField"/>
						<div id="providerError${index}" class="popupMessage"></div>
					</td>
					<td>
						<nested:text property="child(model).text"
									onblur="validateModel(this, ${index}, '${ProductsForm.models}');"
									styleClass="model validationField"/>		
						<div id="modelError${index}" class="popupMessage"></div>
					</td>
					<td>
						<nested:text property="child(date-of-issue).text"
									onblur="validateDate(this, ${index});" styleClass="validationField"/>
						<div id="dateError${index}" class="popupMessage"></div>
					</td>
					<td>
						<nested:text property="child(color).text"
									onblur="validateColor(this, ${index})" styleClass="validationField"/>
						<div id="colorError${index}" class="popupMessage"></div>
					</td>
					<td>
						<logic:empty name="product" property="child(price)">
							<nested:text property="child(notinstock).text"
										onblur="validatePrice(this, ${index});" styleClass="validationField"/>
							<div id="priceError${index}" class="popupMessage"></div>
						</logic:empty>
						<logic:notEmpty name="product" property="child(price)">
							<nested:text property="child(price).text"
										onblur="validatePrice(this, ${index});" styleClass="validationField"/>
							<div id="priceError${index}" class="popupMessage"></div>
						</logic:notEmpty>
					</td>
				</tr>
			</nested:iterate>
			</nested:nest>
		</table>
		<a href='shop.do?command=back&page=subcategories'>
			<span><input type="button" value="Back" class="backButton"/></span>
		</a>
		<a href='xslt?command=add_product'>
			<span><input type="button" value="Add product"/></span>
		</a>
		<html:submit styleId="submitSave" onclick="return validateForm(this);">Save</html:submit>
		</html:form>		
	</body>
</html:html>