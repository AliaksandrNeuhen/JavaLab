<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:r="xalan://com.epam.xmltransformation"
	xmlns:product="xalan://com.epam.xmltransforming.entity.Product">
    <xsl:output method="html" version="1.0" encoding="UTF-8" indent="yes"/>
 <!-- 	<xsl:param name="product" />
	<xsl:param name="categoryname" />
	<xsl:param name="subcategoryname" />
-->
	<xsl:strip-space elements="*"/>
	<xsl:template name="save">
		<xsl:apply-templates />
	</xsl:template>
	
	<xsl:template match="node()|@*">
 		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="subcategory[@name=$subcategoryname]">
		<xsl:copy>
 			<xsl:apply-templates select="@*|node()" /> 
			<xsl:element name="product">
				<xsl:attribute name="name">
					<xsl:value-of select="$name" />
				</xsl:attribute>
				<xsl:element name="provider">
					<xsl:value-of select="$provider" />
				</xsl:element>
				<xsl:element name="model">
					<xsl:value-of select="$model" />
				</xsl:element>
				<xsl:element name="date-of-issue">
					<xsl:value-of select="$dateOfIssue"></xsl:value-of>
				</xsl:element>
				<xsl:element name="color">
					<xsl:value-of select="$color" />
				</xsl:element>
				<xsl:choose>
					<xsl:when test="$notInStock = 'true'">
						<xsl:element name="notinstock"/>
					</xsl:when>
					<xsl:when test="not($notInStock)">
						<xsl:element name="price">
							<xsl:value-of select="$price" />
						</xsl:element>
					</xsl:when>
				</xsl:choose>
			</xsl:element>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>