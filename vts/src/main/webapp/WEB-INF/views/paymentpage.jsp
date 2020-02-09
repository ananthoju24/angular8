<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<!DOCTYPE html>
<html>
<head>
<title>VillageTaxation</title>
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
<script>
	/* function noBack() {
		window.history.forward(1);
	}
	noBack();
	window.onload = noBack;
	window.onpageshow = function(evt) {
		if (evt.persisted)
			noBack();
	}
	window.onunload = function() {
		void (0);
	} */
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
	$(document).ready(function() {
		/* $("#datepicker").datepicker({
			format : " yyyy", // Notice the Extra space at the beginning
			viewMode : "years",
			minViewMode : "years"
		}); */
		console.log("ready!");
		setTimeout(function() {
			$("#response").fadeOut().empty();
		}, 5000);
	});
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
<body>
	<!-- onload="noBack()" -->
	<div id="page-wrapper" class="no-print" style="margin-top: 85px;">
		<div class="container"></div>

	</div>
	<script src="/resources/assets/js/formValidation.js"></script>
</body>
</html>