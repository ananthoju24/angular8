<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>VillageTaxation</title>
<!-- <script type="text/javascript">
	$(document).ready(function() {
		$("#errorMessage").html("");
		$("#searchTax").click(function() {
			var houseNo = $("#houseno").val();
			if (houseNo === null) {
				 $("#errorMessage").html("Please enter house number.");
			} else {
				$.ajax({
					url : "./fetchTax",
					method:post,
					async : false,
					data : $("#search-form").serialize(),
					success : function(resp){
						if(resp === 'SUCCESS'){
							
						}else {
							 $("#errorMessage").html("Please enter valid house number.");
						}
					}error : function(){
						 $("#errorMessage").html("Internal error please try after some time.");	 
					}
				});
			}
			setTimeout(function() {
				$("#errorMessage").fadeOut().empty();}, 5000);
		});
	});
</script> -->

</head>
<body>

	<jsp:include page="/WEB-INF/views/header.jsp" />
	<div id="google_translate_element"></div>
	<script>
		function googleTranslateElementInit() {
			new google.translate.TranslateElement({
				pageLanguage : 'en',
				includedLanguages : 'te',
				autoDisplay : false
			}, 'google_translate_element');
			var a = document.querySelector("#google_translate_element select");
			a.selectedIndex = 1;
			a.dispatchEvent(new Event('change'));
		}
	</script>
	<script
		src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
	<!-- Begin page content -->
	<div id="container " style="margin-top: 85px;">
		<div class=" container-fluid ">
			<!-- <form id="search-form" class="form-horizontal border border-light p-5 col-md-4" action="/taxDetails" method="post"> -->
			<form:form
				class="form-horizontal border border-light p-5 col-md-4 needs-validation"
				method="get" novalidate="novalidate" modelAttribute="owner"
				action="/taxDetails">
				<h5 class="text-left text-info">Search Tax Details</h5>
				<div class="form-group">
					<label for="hno" class="text-info">House No :</label><br> <input
						type="text" name="houseno" id="houseno" class="form-control"
						required="required" minlength="1" maxlength="10">
					<div class="invalid-feedback notranslate">The house number must be more than 1 and less than 10 characters</div>
				</div>
				<div class="form-group">
					<span class="text-danger" id="errorMessage">${searchErrMsg}</span><br>
				</div>
				<div class="form-group">
					<button class="btn btn-primary">Fetch tax</button>
					<!-- 	<input type="submit" name="submit" id="searchTax" class="btn btn-primary" value="Fetch tax"> -->
				</div>
			</form:form>
		</div>
	</div>
	<script src="/resources/assets/js/formValidation.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			setTimeout(function() {
				$("#errorMessage").fadeOut().empty();
			}, 3000);
		});
	</script>
</body>
</html>
