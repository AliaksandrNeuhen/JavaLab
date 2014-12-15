<!-- <?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:r="xalan://com.epam.xmltransformation"
                xmlns:validator="xalan://com.epam.xmltransforming.logic.validator.ProductFieldsCheck">
<xsl:output method="html"/>
<xsl:include href="save_product.xsl"/>
<xsl:param name="name"/>
<xsl:param name="categoryname"/>
<xsl:param name="subcategoryname"/>
<xsl:param name="provider"/>
<xsl:param name="model"/>
<xsl:param name="dateOfIssue"/>
<xsl:param name="color"/>
<xsl:param name="price"/>
<xsl:param name="notInStock"/>
<xsl:param name="tryingAgain"/>
<xsl:param name="validatorObject"/>
<xsl:param name="validationMethod" select="validator:validate($validatorObject, $name, $provider, $model, $dateOfIssue, $color, $price, $notInStock)"/>
<xsl:template match="/">
	<xsl:choose>
	<xsl:when test="not($validationMethod)">
	<html>
		<head>
			<link rel="stylesheet" type="text/css" href="css/appstyle.css"/>
			<script type="text/javascript" src="js/pricechanging.js"/>
		</head>
		<body>
			<div class="addProductForm">
			<form action="shop" method="POST">
				<input type="hidden" name="command" value="add_product"/>
				<div class="formRow">
					<label>Name</label> <input type="text" name="name"  value="{$name}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<span class="alertMessage">
							<xsl:value-of select="validator:getErrorsForName($validatorObject)"/>
						</span>
					</xsl:if>
				</div>
				<div class="formRow">
					<label>Category</label> <input type="text" value="{$categoryname}"/>
				</div>
				<div class="formRow">
					<label>Subcategory</label> <input type="text" value="{$subcategoryname}"/>
				</div>
				<div class="formRow">
					<label>Provider</label> <input type="text" name="provider" value="{$provider}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<span class="alertMessage">
							<xsl:value-of select="validator:getErrorsForProvider($validatorObject)"/>
						</span>
					</xsl:if>
				</div>
				<div class="formRow">
					<label>Model</label> <input type="text" name="model" value="{$model}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<span class="alertMessage">
							<xsl:value-of select="validator:getErrorsForModel($validatorObject)"/>
						</span>
					</xsl:if>
				</div>
				<div class="formRow">
					<label>Date of issue</label> <input type="text" name="dateOfIssue" value="{$dateOfIssue}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<span class="alertMessage">
							<xsl:value-of select="validator:getErrorsForDateOfIssue($validatorObject)"/>
						</span>
					</xsl:if>
				</div>
				<div class="formRow">
					<label>Color</label> <input type="text" name="color" value="{$color}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<span class="alertMessage">
							<xsl:value-of select="validator:getErrorsForColor($validatorObject)"/>
						</span>
					</xsl:if>
				</div>
				<div class="formRow">
					<label>Price</label> <input type="text" name="price" value="{$price}" id="priceText" />
					 Not In Stock <input type="checkbox" id="notInStockCheckbox" name="notInStock" value="checked" onchange="priceCheckboxChanged();"/>
					<xsl:if test="$tryingAgain = 'true'">
						<span class="alertMessage">
							<xsl:value-of select="validator:getErrorsForPrice($validatorObject)"/>
						</span>
					</xsl:if>
				</div>
				<div class="formRow">
					<input type="hidden" name="isValid" value="{$validationMethod}"/>
					<input type="hidden" name="tryingAgain" value="true"/>
					<input class="formButton" type="submit" value="Save"/>
				</div>
			</form>
			<form action="shop" method="POST" style="margin-top:-31px;margin-left:100px;">
				<input type="hidden" name="command" value="back"/>
				<input type="hidden" name="page" value="products"/>
				<input  class="formButton" type="submit" value="Cancel"/>
			</form>
			</div>
		</body>
	</html>
	</xsl:when>
	<xsl:when test="$validationMethod">
		<xsl:call-template name="save"/>
	</xsl:when>
	</xsl:choose>
</xsl:template>
</xsl:stylesheet>