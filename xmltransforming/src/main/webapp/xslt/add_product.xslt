<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:r="xalan://com.epam.xmltransformation">
<xsl:output method="html"/>
<xsl:param name="categoryname"/>
<xsl:param name="subcategoryname"/>

<xsl:template match="/">
	<html>
		<body>
			<div style="align:center;">
			<form action="shop" method="POST">
				<input type="hidden" name="command" value="save_product"/>
				<div style="display:block;">Name <input type="text" name="name"  value=""/></div>
				<div style="display:block;">Category <input type="text" value="{$categoryname}"/></div>
				<div style="display:block;">Subcategory <input type="text" value="{$subcategoryname}"/></div>
				<div style="display:block;">Provider <input type="text" name="provider" value=""/></div>
				<div style="display:block;">Model <input type="text" name="model" value=""/></div>
				<div style="display:block;">Date of issue <input type="text" name="date-of-issue" value=""/></div>
				<div style="display:block;">Color <input type="text" name="color" value=""/></div>
				<div style="display:block;">Price <input type="text" name="price" value=""/></div>
				<div style="display:block;"><input type="submit" value="Save"/></div>
			</form>
			<form action="shop" method="POST">
				<input type="hidden" name="command" value="back"/>
				<input type="hidden" name="page" value="products"/>
				<input type="submit" value="Cancel"/>
			</form>
			</div>
		</body>
	</html>
</xsl:template>

</xsl:stylesheet>