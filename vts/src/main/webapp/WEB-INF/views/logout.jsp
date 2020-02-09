<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>VillageTaxation</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link type="text/css" rel="stylesheet" href="./assets/css/main.css">

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
	<div id="login">
		<h3 class="text-center text-info pt-5">Village Taxation</h3>
		<div class="container">
			<div id="login-row"
				class="row justify-content-center align-items-center">
				<div id="login-column" class="col-md-6">
					<div class="form-group">
						<label for="logout-text" class="text-info"><span>Logout successfully
						 <a href="./login">Click here to login</a>
						</span>  </label>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>