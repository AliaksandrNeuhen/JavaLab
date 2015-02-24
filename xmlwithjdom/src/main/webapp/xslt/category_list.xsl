<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:r="xalan://com.epam.xmltransformation">
    <xsl:output method="html"/>
	<xsl:param name="url"/>
    <xsl:template match="/">
    	<html><body>
    		<table border="1" align="center">
    			<caption>Categories list</caption>
    			<tr>
    				<th>Category name</th>
    				<th>Number of products</th>
    			</tr>
				<xsl:apply-templates />
			</table>
	  	</body></html>
    </xsl:template>
  <xsl:template match="category">
  	<tr>
  			<td>
  			<a href="shop?command=show_subcategories&amp;categoryname={@name}">
	  			<div style="height:100%; width:100%;">
  					<xsl:value-of select="@name"/>
  				</div>
  			</a>
  			</td>
  		<td>
  			<xsl:value-of select="count(subcategory/product)"/>
  		</td>
  	</tr>
  </xsl:template>
</xsl:stylesheet>