<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:r="xalan://com.epam.xmltransformation"
	xmlns:product="xalan://com.epam.xmltransforming.entity.Product">
	<xsl:output method="xml" />
	<xsl:param name="product" />
	<xsl:param name="categoryname" />
	<xsl:param name="subcategoryname" />

	<xsl:template match="/|node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="subcategory[@name=$subcategoryname]">
		<xsl:copy>
 			<xsl:apply-templates select="@*|node()" /> 
			<xsl:element name="product">
				<xsl:attribute name="name">
					<xsl:value-of select="product:getName($product)" />
				</xsl:attribute>
				<xsl:element name="provider">
					<xsl:value-of select="product:getProvider($product)" />
				</xsl:element>
				<xsl:element name="model">
					<xsl:value-of select="product:getModel($product)" />
				</xsl:element>
				<xsl:element name="date-of-issue">
					<xsl:value-of select="product:getDateOfIssue($product)"></xsl:value-of>
				</xsl:element>
				<xsl:element name="color">
					<xsl:value-of select="product:getColor($product)" />
				</xsl:element>
				<xsl:element name="price">
					<xsl:value-of select="product:getPrice($product)" />
				</xsl:element>
			</xsl:element>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>