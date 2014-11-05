<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Products list</title>
</head>
<body>
<c:forEach var="category" items="${categories}">
	<div id="category">
	${category.name}
		<c:forEach var="subcategory" items="${category.subcategories}">
			<div id="subcategory">
				${subcategory.name}
				<c:forEach var="product" items="${subcategory.products}">
					<div id="product">
						${product.name} 
						${product.model}
						${product.provider}
						${product.dateOfIssue}
						${product.color}
						${product.price}
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
</c:forEach>
</body>
</html>
