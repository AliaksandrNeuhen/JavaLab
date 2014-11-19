<!-- <?xml version="1.0" encoding="UTF-8"?>-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:r="xalan://com.epam.xmltransformation"
                xmlns:validator="xalan://com.epam.xmltransforming.util.ProductFieldsCheck">
<xsl:output method="html"/>
<xsl:include href="save_product.xslt"/>
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
			<script type="text/javascript" src="js/pricechanging.js"/>
		</head>
		<body>
			<div style="align:center;">
			<form action="shop" method="POST">
				<input type="hidden" name="command" value="add_product"/>
				<div style="display:block;">
					Name <input type="text" name="name"  value="{$name}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<xsl:value-of select="validator:getErrorsForName($validatorObject)"/>
					</xsl:if>
				</div>
				<div>
					Category <input type="text" value="{$categoryname}"/>
				</div>
				<div>
					Subcategory <input type="text" value="{$subcategoryname}"/>
				</div>
				<div>
					Provider <input type="text" name="provider" value="{$provider}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<xsl:value-of select="validator:getErrorsForProvider($validatorObject)"/>
					</xsl:if>
				</div>
				<div>
					Model <input type="text" name="model" value="{$model}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<xsl:value-of select="validator:getErrorsForModel($validatorObject)"/>
					</xsl:if>
				</div>
				<div>
					Date of issue <input type="text" name="dateOfIssue" value="{$dateOfIssue}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<xsl:value-of select="validator:getErrorsForDateOfIssue($validatorObject)"/>
					</xsl:if>
				</div>
				<div>
					Color <input type="text" name="color" value="{$color}"/>
					<xsl:if test="$tryingAgain = 'true'">
						<xsl:value-of select="validator:getErrorsForColor($validatorObject)"/>
					</xsl:if>
				</div>
				<div>
					Price <input type="text" name="price" value="{$price}" id="priceText" />
					 Not In Stock <input type="checkbox" id="notInStockCheckbox" name="notInStock" value="checked" onchange="priceCheckboxChanged();"/>
					<xsl:if test="$tryingAgain = 'true'">
						<xsl:value-of select="validator:getErrorsForPrice($validatorObject)"/>
					</xsl:if>
				</div>
				<div>
					<input type="hidden" name="isValid" value="{$validationMethod}"/>
					<input type="hidden" name="tryingAgain" value="true"/>
					<input type="submit" value="Save"/>
				</div>
			</form>
			<form action="shop" method="POST">
				<input type="hidden" name="command" value="back"/>
				<input type="hidden" name="page" value="products"/>
				<input type="submit" value="Cancel"/>
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