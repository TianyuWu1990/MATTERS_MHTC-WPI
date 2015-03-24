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

<title>User Feedback</title>

<!-- Bootstrap core CSS -->
<link href="../mhtc/adminPanel/css/bootstrap.min.css" rel="stylesheet">
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
                    <div class="panel-heading" style="background-color: #7b0020; color: #ffffff;">
                        <div class="panel-title">User Feedback</div>
                    </div>     

                    <div style="padding-top:20px" class="panel-body" >
	                    <c:choose>
	                       <c:when test="${completed}">
	                       		 Thank you for your feedback. Please allow 24-48 hours to receive our reply.
	                       </c:when>  
	                       <c:otherwise>
		                        <div id="login-alert" class="alert alert-danger">
		                        	Error while sending feedback form. Please try again
		                        </div> 
	                       </c:otherwise>
	                    </c:choose>
	                    <p>Click <a href="<c:url value="/" />">here</a> to return to the dashboard.</p>
                    </div>                     
             </div>  
        </div>
    </div>
	<!-- /container -->
</body>
</html>

