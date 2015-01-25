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

<title>Account Registration</title>

<!-- Bootstrap core CSS -->
<link href="../adminPanel/css/bootstrap.min.css" rel="stylesheet">
<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
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

.form-signin input[type="email"] {
	margin-bottom: 10px;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
</style>

<script src="../js/jquery-2.1.1.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</head>

<body>
    <div class="container">    
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">Account Registration</div>
                    </div>     

                    <div style="padding-top:10px" class="panel-body" >
                        <div id="login-alert" class="alert alert-primary">
                        	Please provide the necessary information in order to create an account.
                        </div>
                        <div>
                        	<form id="registerForm" class="form-horizontal" role="form" action="register/submit" method="POST">                         
                           		<div style="margin-bottom: 25px" class="input-group">
                                   	<span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
                                   	<input type="text" class="form-control" name="UserName" placeholder="Username"> 
                                   </div>
                                   
                                   <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-envelope fa-fw"></i></span>
                                    <input type="text" class="form-control" name="Email" placeholder="Email">  
                                   </div>
                                   
                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
                                    <input type="password" class="form-control" name="Password" placeholder="Password">  
                                   </div>
                                              
                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
                                    <input type="password" class="form-control" placeholder="Password confirm">  
                                   </div>
                                   
                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-info fa-fw"></i></span>
                                    <input type="text" class="form-control" name="FirstName" placeholder="First Name">  
                                   </div>
                                   
                                   <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-info fa-fw"></i></span>
                                    <input type="text" class="form-control" name="LastName" placeholder="Last Name">  
                                   </div>	                                    
                                   			                               
	                            <div style="margin-top:10px" class="form-group">
	                                <div class="col-sm-12 controls center" align="center">
	                                  <a id="btn-login" href="#" class="btn btn-success" onclick="$('#registerForm').submit();">Register</a>
	                                </div>
	                            </div>   
                        	</form>
                        </div>
                     </div>    
                 </div>                     
             </div>  
        </div>
	<!-- /container -->
</body>
</html>

