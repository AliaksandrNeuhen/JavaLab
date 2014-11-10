<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:r="xalan://com.epam.xmltransformation">
<xsl:output method="html"/>
<xsl:param name="categoryname"/>

<xsl:template match="/">
	<html>
		<body>
			<table border="1" align="center">
    			<caption>
    				<form action="shop" method="GET" style="display:inline-block;float:left;">
    					<input type="hidden" name="command" value="back"/>
    					<input type="hidden" name="page" value="categories"/>
    					<input type="submit" value="Back"/>
    				</form>Subcategories list
				</caption>
    			<tr>
    				<th>Subcategory name</th>
    				<th>Number of products</th>
    			</tr>
				<xsl:apply-templates />
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="category">
	<xsl:if test="@name = $categoryname">
		<xsl:apply-templates/>
	</xsl:if>
</xsl:template>

<xsl:template match="subcategory">
<tr>	
		<td>
			<a href="shop?command=show_products&amp;subcategoryname={@name}">
				<div style="height:100%; width:100%;">
					<xsl:value-of select="@name"/>
				</div>
			</a>
		</td>
		<td>
			<xsl:value-of select="count(product)"/>
		</td>
</tr>
</xsl:template>

</xsl:stylesheet>    