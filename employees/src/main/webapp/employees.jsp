<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/pagination" prefix="paginator" %>
<html>
<head>
	<title>Employees</title>
	 <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<h1>Employees List</h1>
<paginator:paginator numOfRecords="${max}" firstRecord="${first}" prevNumOfRecords="${prev_max}"/>
<div class="content">
<c:forEach items="${employees}" var="employee">
	<div class="employeeInfo">
		<div class="primaryInfo">
			<h3>Employee's Info:</h3>
			<div class="nameAdress">
				<label class="empLabel">First Name</label>: ${employee.firstName}<br>
				<label class="empLabel">Last Name</label>: ${employee.lastName}<br>
				<label class="empLabel">Address</label>: ${employee.adress.content}
			</div>
		</div>
	<div class="wrapOffices">
	<c:forEach items="${employee.positions}" var="position">
		<div class="officeInfo">
			<label class="officeLabel">Company name</label>: ${position.office.company.name}<br>
			<label class="officeLabel">City</label>: ${position.office.adress.city.name}<br> 
			<label class="officeLabel">Country</label>: ${position.office.adress.city.country.name}<br> 
			<label class="officeLabel">Address</label>: ${position.office.adress.content}<br>
			<label class="officeLabel">Count of employees</label>: ${position.office.countOfEmployees}<br> 
			<label class="officeLabel">Position</label>: ${position.name}<br>
		</div>
	</c:forEach>
	</div>
	<br>
	</div>	
</c:forEach>
</div>
</body>
</html>