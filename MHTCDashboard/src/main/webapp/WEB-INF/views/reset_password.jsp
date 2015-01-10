<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Password reset form</title>

<!-- Bootstrap core CSS -->
<link href="../adminPanel/css/bootstrap.min.css" rel="stylesheet">

<style>
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #eee;
}

.form-signin {
	max-width: 330px;
	padding: 15px;
	margin: 0 auto;
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin .checkbox {
	font-weight: normal;
}

.form-signin .form-control {
	position: relative;
	height: auto;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	padding: 10px;
	font-size: 16px;
}

.form-signin .form-control:focus {
	z-index: 2;
}

.form-signin input[type="password"] {
	margin-bottom: 10px;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
</style>
</head>

<body>
	<div class="container">
		<form class="form-signin" action="${pageContext.request.contextPath}/login/" method="post">
			<h2 class="form-signin-heading">Please choose your new password:</h2>
			<label for="inputUsername" class="sr-only">New Password</label> 
			<input name="password" id="new_password" class="form-control" placeholder="Username" required autofocus> 
			<label for="inputPassword" class="sr-only">Confirm New Password</label> <br/>
			<input name="confirm_password" type="password" data-match="#new_password" data-match-error="Whoops, these don't match" class="form-control" placeholder="Password" required>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Change your password</button>
		</form>

	</div>
	<!-- /container -->
</body>
</html>

