<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="/resources/assets/js/formValidation.js"></script>
<title>VillageTaxation</title>
</head>
<body>

	<jsp:include page="/WEB-INF/views/header.jsp" />
	<div id="page-wrapper" style="margin-top: 25px;">
		<form:form class="form-horizontal border border-light p-5 col-md-6 needs-validation"
				method="post" modelAttribute="owner" action="/enroll" novalidate="novalidate">
			<h5 class="text-left text-info">House Enroll </h5>
			<!-- style="background-color: #f2f2f2;" -->
			<div class="form-group">
				<label for="InputHno">House Number </label>
				<form:input path="houseNumber" type="text"
					class="form-control col-sm-6" id="houseNumber" required="required"
					maxlength="10" minlength="2" />
				<!-- pattern="([1-9]+(\-)([0-9]?[a-zA-Z])*)*" -->
				<div class="invalid-feedback notranslate">The house number must be more
					than 1 and less than 10 characters</div>
			</div>

			<div class="form-group">
				<label for="InputOwnerName">Owner Name</label>
				<form:input path="ownerName" type="text"
					class="form-control col-sm-6" id="ownerName" required="required" />
				<div class="invalid-feedback notranslate">The owner name must be more than
					2 and less than 40 characters</div>

			</div>
			<div class="form-group">
				<label for="InputOwnerName">Father/Husband</label>
				<form:input path="FatherOrHusband" type="text"
					class="form-control col-sm-6" id="FatherOrHusband"
					required="required" />
				<div class="invalid-feedback notranslate">The Father/Husband name must be more than
					2 and less than 40 characters</div>

			</div>

			<div class="form-group">
				<label for="InputMobileNo">Mobile Number </label>
				<form:input path="mobileNumber" type="text"
					class="form-control only-numeric col-sm-6" id="mobileNumber"
					required="required" minlength="10" maxlength="10"
					pattern="([9]*[8]*[7]*[6]*){1}[0-9]{9}"
					onkeypress="return event.charCode >= 48 && event.charCode <= 57" />
				<div class="invalid-feedback notranslate">The mobile number must be 10
					digits and start with 9/8/7/6</div>
			</div>
			<div class="form-check"></div>
			<button type="submit" class="btn btn-primary">Enroll New
				House</button>
			<br>
			<br>
			<div class="form-group">
				<c:if test="${vtsResponse.respCode == 201}">
					<label for="searchErrorMsg" class="text-success">
						${vtsResponse.respDesc} </label>
					<br>
				</c:if>
				<c:if test="${vtsResponse.respCode == 404}">
					<label for="searchErrorMsg" class="text-danger">
						${vtsResponse.respDesc} </label>
					<br>
				</c:if>
			</div>
		</form:form>
		<!-- /.row -->
	</div>
</body>
</html>