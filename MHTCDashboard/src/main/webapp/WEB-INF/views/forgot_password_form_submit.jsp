<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

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
<link href="../../adminPanel/css/bootstrap.min.css" rel="stylesheet">
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
<script src="../js/bootstrap-validator.js" type="text/javascript"></script>
</head>

<body>
    <div class="container">    
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                    <div class="panel-heading" style="background-color: #7b0020; color: #ffffff;">
                        <div class="panel-title">Forgot your password?</div>
                    </div>     

                    <div style="padding-top:20px" class="panel-body" >
	                    <c:choose>
	                       <c:when test="${completed}">
	                       		An email with password reset detail has been sent. Please check and follow the instruction in that email. <br>
	                       		<p>Click <a href="<c:url value="/" />">here</a> to return to the dashboard.</p>
	                       </c:when>  
	                       <c:otherwise>
		                        <div id="login-alert" class="alert alert-danger">
		                        	<c:if test="${invalid_captcha}">
		                        		Invalid Captcha Verification.
		                        	</c:if>
		                        	<c:if test="${!invalid_captcha}">
		                        		We could not find any account associated with the email address you provided.
		                        	</c:if>
		                        </div>
		                        <div>    
		                        <form id="resetRequestForm" class="form-horizontal" role="form" action="sent" method="post">
		                                    
		                            <div style="margin-bottom: 25px" class="input-group">
		                                    <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
		                                    <input type="email" class="form-control" name="email" placeholder="Email">
											<div class="help-block with-errors"></div>                                       
		                            </div>
		                            
		                          	<div style="margin-bottom: 25px;" align="center">
	                                   <%
							                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LfXmgATAAAAABM7oYTbs6-XZyU29ozVca5taJIb",
							                                    "6LfXmgATAAAAAP_qkRZBcBBqnb8yRuUKMm9LJYSW", false);
							                out.print(c.createRecaptchaHtml(null, null));
							            %> 
	                        		</div>
		                               
		                            <div style="margin-top:10px" class="form-group">
		                                <div class="col-sm-12 controls center" align="center">
		                                  <a id="btn-login" href="#" class="btn btn-success" onclick="$('#resetRequestForm').submit();">Request password reset</a>
		                                </div>
		                            </div>   
		                        </form> 
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

