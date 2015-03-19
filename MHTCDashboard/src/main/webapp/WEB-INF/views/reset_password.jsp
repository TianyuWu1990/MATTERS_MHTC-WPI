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

<title>Forgot your password?</title>

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

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</head>

<body>
    <div class="container">    
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                    <div class="panel-heading" style="color: white; background-color: #680017;">
                        <div class="panel-title">Forgot your password?</div>
                    </div>     

                    <div style="padding-top:10px" class="panel-body" >
                    	<c:choose>
                        	<c:when test="${!error}">
		                        <div id="login-alert" class="alert alert-primary">
		                        Please enter a new password and password confirmation for user with email ${email}. 
		                        </div>
		                        <div>
		                        	<form id="resetForm" class="form-horizontal" role="form" action="reset/submit" method="POST">                         
	                            		<input type="hidden" name="token" value="${token}">
	                            		<div style="margin-bottom: 25px" class="input-group">
	                                    	<span class="input-group-addon"><i class="fa fa-key"></i></span>
	                                    	<input type="password" class="form-control" name="password" placeholder="Password"> 
	                                    </div>
		                                <div style="margin-bottom: 25px" class="input-group">
		                                    <span class="input-group-addon"><i class="fa fa-key"></i></span>
		                                    <input type="password" class="form-control" placeholder="Password confirmation">  
	                                    </div>           
			                               
			                            <div style="margin-top:10px" class="form-group">
			                                <div class="col-sm-12 controls center" align="center">
			                                  <a id="btn-login" href="#" class="btn btn-success" onclick="$('#resetForm').submit();">Reset my password</a>
			                                </div>
			                            </div>   
		                        	</form>
		                        </div>
                        	</c:when>
                        	<c:otherwise>
		                        <div id="login-alert" class="alert alert-danger">
		                        	Error while processing your request. Invalid Token Data.
		                        </div>                        		
                        	</c:otherwise>
                        </c:choose> 
                     </div>    
                 </div>                     
             </div>  
        </div>
	<!-- /container -->
</body>
</html>

