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
    				Subcategories list
				</caption>
    			<tr>
    				<th>Subcategory name</th>
    				<th>Number of products</th>
    			</tr>
				<xsl:apply-templates />
			</table>
			<form action="shop" method="GET" align="center" style="margin-right:197px; margin-top: 10px;">
    					<input type="hidden" name="command" value="back"/>
    					<input type="hidden" name="page" value="categories"/>
    					<input type="submit" value="Back" style="width:89px;"/>
    		</form>
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