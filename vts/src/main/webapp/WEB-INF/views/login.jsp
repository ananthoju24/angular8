<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page pageEncoding="UTF-8" %>--%>
 <!DOCTYPE html>
<html>
<head>
<!-- <meta charset="utf-8"> -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="apple-touch-icon" sizes="180x180"
	href="/resources/assets/favicon_vts/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="/resources/assets/favicon_vts/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="/resources/assets/favicon_vts/favicon-16x16.png">
<link rel="manifest" href="/site.webmanifest">
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
<title>VillageTaxation</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script>
	function noBack() {
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
	}
</script>
<!------ Include the above in your HEAD tag ---------->
</head>
<body onload="noBack()">
	<!-- Default form login -->
	<div role="main" class="container">
		<div id="login">

			<h3 class="text-center .text-muted pt-5">Village Taxation</h3>
			<div class="container">
				<div id="login-row"
					class="row justify-content-center align-items-center">
					<div id="login-column" class="col-md-6">
						<div id="login-box" class="col-md-12">
							<form action="/login" method="post">
								<!-- <form id="login-form" class="form" action="doLogin"
							method="post"> -->
								<div class="form-group">
									<h3 class="text-center text-info">Login</h3>
								</div>
								<div class="form-group">
									<label for="username" class="text-info">Username:</label><br>
									<input type="text" name="username" id="username"
										class="form-control">
								</div>
								<div class="form-group">
									<label for="password" class="text-info">Password:</label><br>
									<input type="password" name="password" id="password"
										class="form-control"> <label for="hno"
										class="text-danger"> ${errorMsg} </label>
								</div>
								<div class="form-group">
									<!-- <label for="remember-me" class="text-info"><span>Remember
										me</span>  <span><input id="remember-me" name="remember-me"
										type="checkbox"></span></label><br>  -->
									<input type="submit" name="submit" class="btn btn-info btn-md"
										value="submit">
								</div>

								<!-- <div id="register-link" class="text-right">
								<a href="#" class="text-info">Register here</a>
							</div> -->
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
</body>

</html>