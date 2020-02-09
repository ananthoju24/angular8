<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>VillageTaxation</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<style>
@media screen and (min-width: 768px) {
	.modal-dialog {
		width: 700px; /* New width for default modal */
	}
	.modal-sm {
		width: 350px; /* New width for small modal */
	}
}

@media screen and (min-width: 992px) {
	.modal-lg {
		width: 950px; /* New width for large modal */
	}
}
</style>
<!-- <script src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script> -->

<script>
window.onload = function() {
	history.replaceState("", "", "/taxDetails");
}
/* 	function googleTranslateElementInit() {
		new google.translate.TranslateElement({
			pageLanguage : 'en',
			includedLanguages : 'te',
			autoDisplay : false
		}, 'google_translate_element');
		var a = document.querySelector("#google_translate_element select");
		a.selectedIndex = 1;
		a.dispatchEvent(new Event('change'));
	} */
</script>
<style type="text/css">
html, body {
	max-width: 100%;
	overflow-x: auto;
}

@media print {
	.no-print, .no-print * {
		display: none !important;
	}
}
</style>
</head>
<body onload="noBack()">
	<jsp:include page="/WEB-INF/views/header.jsp" />
	<div id="google_translate_element"></div>
	<div id="page-wrapper" class="no-print" style="margin-top: 85px;">
		<div class="container col-md-12">
			<!-- Page Heading -->
			<div class="row" id="main">
				<div class="col-sm-12 col-md-12 well" id="content">
					<div class="container">
						<div id="serachresult"
							class="responsive row justify-content-center align-items-center">
							<div class="col-md-6">
								<table class="table table-responsive" noborder>
									<thead>
										<th><h4>Tax Details</h4></th>
									</thead>
									<tbody>
										<tr>
											<td class="col-sm-6"><h5>House Number</h5></td>
											<td class="col-sm-6"><h5>
													<b><c:out value="${hoBean.houseNumber}" /></b>
												</h5></td>
										</tr>
										<tr>
											<td class="col-sm-6"><h5>Owner Name</h5></td>
											<td class="col-sm-6"><h5>
													<b><c:out value="${hoBean.ownerName}" /></b>
												</h5></td>
										</tr>
										<tr>
											<td class="col-sm-6"><h5>Mobile number</h5></td>
											<td class="col-sm-6"><h5>
													<b><c:out value="${hoBean.mobileNumber}" /></b>
												</h5></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="col-sm-6">
								<div class="form-group col-sm-4">
									<form action="/viewTax" method="GET">
										<input type="text" value="${hoBean.houseNumber}" id="houseNumber" name="houseNumber" hidden />
										<button class="btn btn-primary">View Tax</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>