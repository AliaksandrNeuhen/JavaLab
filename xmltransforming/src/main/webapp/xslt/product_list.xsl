<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:r="xalan://com.epam.xmltransformation">
<xsl:output method="html"/>
<xsl:param name="subcategoryname"/>

<xsl:template match="/">
	<html>
		<body>
			<table border="1" align="center">
    			<caption>
    			
    			Products list
    			
    			</caption>
    			<tr>
    				<th>Product name</th>
    				<th>Provider</th>
    				<th>Model</th>
    				<th>Date of Issue</th>
    				<th>Color</th>
    				<th>Price</th>
    			</tr>
				<xsl:apply-templates />
			</table>
			<form action="shop" method="GET" align="center" style="display:inline-block;float:left;margin-left:388px;margin-top:10px;">
    				<input type="hidden" name="command" value="back"/>
    				<input type="hidden" name="page" value="subcategories"/>
    				<input type="submit" value="Back" style="width:89px;"/>
    		</form>
    		<form action="shop" metho="GET" align="center" style="display:inline-block;float:right;margin-right:389px;margin-top:10px;">
    				<input type="hidden" name="command" value="add_product"/>
    				<input type="hidden" name="isAdded" value="false" />
    				<input type="submit" value="Add product"/>
    		</form>
		</body>
	</html>
</xsl:template>

<xsl:template match="subcategory">
	<xsl:if test="@name = $subcategoryname">
		<xsl:apply-templates/>
	</xsl:if>	
</xsl:template>

<xsl:template match="product">
	<tr>
		<td>
			<xsl:value-of select="@name"/>
		</td>
		<xsl:apply-templates/>
	</tr>
</xsl:template>

<xsl:template match="provider | model | date-of-issue | color | price">
	<td>
		<xsl:apply-templates/>
	</td>
</xsl:template>

<xsl:template match="notinstock">
	<td>
		<xsl:text>N/A</xsl:text>
	</td>
</xsl:template>
</xsl:stylesheet>