<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<!-- <link rel="icon"
	href="https://getbootstrap.com/docs/4.0/assets/img/favicons/favicon.ico"> -->
<link rel="apple-touch-icon" sizes="180x180"
	href="/resources/assets/favicon_vts/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="/resources/assets/favicon_vts/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="/resources/assets/favicon_vts/favicon-16x16.png">
<link rel="manifest" href="/site.webmanifest">
<style>
#google_translate_element, .skiptranslate {
	display: none;
}

body {
	top: 0 !important;
}
</style>
<!-- <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script> -->
<!-- <script src="/resources/assets/js/jquery-3.2.1.slim.min.js"></script> -->
<script src="/resources/assets/js/jquery-3.4.1.min.js"
	type="text/javascript"></script>
<script src="/resources/assets/js/popper.min.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"
	integrity="sha256-xNjb53/rY+WmG+4L6tTl9m6PpqknWZvRt0rO1SRnJzw="
	crossorigin="anonymous"></script>

<script src="/resources/assets/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="Stylesheet" type="text/css" />


<!-- Fixed navbar -->
<!--
  This code will translate page contents automatically (without user input)
  Settings located at line 9, current script will translate english to estonian
-->
<style>
#google_translate_element, .skiptranslate {
	display: none;
}

body {
	top: 0 !important;
}
</style>
</head>
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
<header>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<a class="navbar-brand" href="/home"> VTS </a>
		<button class="navbar-toggler collapsed" type="button"
			data-toggle="collapse" data-target="#navbarCollapse"
			aria-controls="navbarCollapse" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="navbar-collapse collapse" id="navbarCollapse" style="">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link notranslate"
					href="/home">Home <span class="sr-only">(current)</span></a></li>
				<li class="nav-item"><a class="nav-link" href="/search/tax">Tax
						Details</a></li>
				<li class="nav-item"><a class="nav-link" href="/enroll">Enroll</a></li>
				<!--	<li class="nav-item"><a class="nav-link disabled"
					href="#">Disabled</a>
				</li> -->
			</ul>
			<!-- <form class="form-inline mt-2 mt-md-0">
					<a class="nav-link disabled" href="/logout">Logout</a>
			</form> -->
			<ul class="nav navbar-nav ml-auto">
				<li class="nav-item dropdown"><a href="#"
					class="nav-link dropdown-toggle notranslate" data-toggle="dropdown">
						<c:out value="${username}"></c:out>
				</a>
					<div class="dropdown-menu dropdown-menu-right">
						<!-- <a href="#" class="dropdown-item">Home</a>
						<div class="dropdown-divider"></div> -->
						<a href="/logout" class="dropdown-item notranslate">Logout</a>
					</div></li>
			</ul>
		</div>
	</nav>
</header>