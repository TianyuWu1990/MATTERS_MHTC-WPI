<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Admin Manage Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js" ></script> 
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>

    </head>
    <body>
    	 <div id="admin-add-form">
		     <form method="POST" action="account/create">
		     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		     <table>
				<tr><td>Username</td><td><input type="text" name="Username"> </td></tr>
				<tr><td>First name</td><td><input type="text" name="FirstName"> </td></tr>
				<tr><td>Last name</td><td> <input type="text" name="LastName">  </td></tr>
				<tr><td>Email</td><td><input type="text" name="Email">  </td></tr>
				<tr><td>Password</td><td><input type="password" name="Password">  </td></tr>
			</table>
				<input type="submit" value="Add Admin Account">
			</form>
		</div>
		
		<hr>
		<div id="admin-change-my-pwd">
			<form method="POST" action="account/change-password">
		     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		     <table>
				<tr><td>Current password</td><td><input type="password" name="oldPassword"> </td></tr>
				<tr><td>New Password</td><td><input type="password" name="newPassword">  </td></tr>
				<tr><td>Confirm New Password</td><td><input type="password" name="confirmPassword">  </td></tr>
			</table>			
			<input type="submit" value="Change my password">
			</form>
		</div>
		
		<hr>
		<div id="admin-reset-password">
			<form method="POST" action="account/reset-password">
		     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		     <table>
				<tr><td>Admin's Username</td><td><input type="text" name="resetUsername"> </td></tr>
			</table>			
			<input type="submit" value="Reset password for this account">
			</form>
		</div>
    </body>
</html>
