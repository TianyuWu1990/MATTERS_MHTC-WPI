<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<!DOCTYPE html>
<html class=" js flexbox flexboxlegacy canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths js flexbox flexboxlegacy canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths" lang="en-US" style="">

<head>
<meta charset="UTF-8">
<meta name="description" content="Search matters Map">
<meta name="keywords" content="Maps,GIS,Maptitude">
<meta name="author" content="MHTC">
<meta name="application-name" content="Maptitude for the Web">
<meta name="company-name" content="MHTC">
<meta name="application-date" content="2015/2/12">
<meta name="application-version" content="2015.2.12">
<meta name="generator" content="Maptitude">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>MATTERS</title>
<link rel="profile" href="http://gmpg.org/xfn/11">
<link href='http://fonts.googleapis.com/css?family=Muli:400,400italic' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,400,600' rel='stylesheet' type='text/css'>
<link href="css/mesh/base.css" rel="stylesheet" type="text/css">
<link href="css/mesh/main.css" rel="stylesheet" type="text/css">
<link href="css/mesh/main-responsive.css" rel="stylesheet" type="text/css">
<link href="css/mesh/animate.css" rel="stylesheet" type="text/css">

<style>
#recaptcha_response_field {
	height: 20px;
}

.form-error {
	color: red;
}
</style>


</head>

<body class="home" id="top">
<div class="wrap">
<div id="page" class="hfeed site">
	<jsp:include page="unifiedHeader.jsp"/>
<div class="content-area">
    <main class="site-main" role="main">
      
      <div class="main-content-wrap">
      	<div id="noJSError" style="color: #680017; font-weight: bolder; font-size: 25px; text-align: center; padding-top: 20px;">
			WARNING: You must have JavaScript enabled to use this page.
			<br/>
			<br/>
		</div>
      
       <div class="container interior-container" id="mainContent" style="display: none;">


		   <div class="copy-block">
		   <h1 class="int-title">Feedback</h1>
		   <h2 class="int-sub-title">Please provide your suggestions about MATTERS. Thank you!</h2>
		   <c:if test="${'' eq param.error}">
				<div style="color: red">
					Authentication failed!<br /> Reason :
					${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				</div>
			</c:if>
			<form action="feedback_post" method="post">

				<label class="feedback_label">Name <span class="gfield_required">*</span></label><br/>
				<input name="name" id="name" type="text" style="width: 100%; max-width: 530px; height: 30px; margin-top: 5px;" autofocus> 
				<span class="form-error"></span> 
				<br/><br/>
				
				<label class="feedback_label">Affiliation</label><br/>
				<input name="affiliation" id="affiliation" style="width: 100%; max-width: 530px; height: 30px; margin-top: 5px;" type="text"> 
				<span class="form-error"></span> 
				<br/><br/>
				
				<label class="feedback_label">Email Address <span class="gfield_required">*</span> </label><br/>
				<input name="email" id="email" style="width: 100%; max-width: 530px; height: 30px; margin-top: 5px;" type="text">
				<span class="form-error"></span>  
				<br/><br/>
				
				<label class="feedback_label">Subject <span class="gfield_required">*</span></label><br/>
				<input name="subject" id="subject" style="width: 100%; max-width: 530px; height: 30px; margin-top: 5px;" type="text">
				<span class="form-error"></span>  
				<br/><br/>

				<label class="feedback_label">Comments <span class="gfield_required">*</span></label><br/>
				<textarea name="comments" style="width: 100%; max-width: 530px; height: 180px; margin-top: 5px;"></textarea>
				<span class="form-error"></span> 

				<br/><br/>
				
                   <%
		                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LfXmgATAAAAABM7oYTbs6-XZyU29ozVca5taJIb",
		                                    "6LfXmgATAAAAAP_qkRZBcBBqnb8yRuUKMm9LJYSW", false);
		                out.print(c.createRecaptchaHtml(null, null));
		            %> 
				
				<button type="submit" style="margin-top: 20px; margin-bottom: 40px;">
					Send Feedback
				</button>
				
			</form>
		   </div> <!-- copy block -->

		</div><!-- container -->
    
    
      </div><!-- main-content-wrap -->
    </main><!-- #main -->
</div><!-- #primary -->
	<footer id="colophon" class="site-footer" role="contentinfo">
		<div class="site-info container">
		  <p class="copy">2015. Worcester Polytechnic Institute. All Rights Reserved.<br />
		  Sponsored by Mass High Technology Council</p>

		</div><!-- .site-info -->
	</footer><!-- #colophon -->
</div><!-- #page -->


<!--[if lt IE 9]><script src="//cdnjs.cloudflare.com/ajax/libs/html5shiv/r29/html5.min.js"></script><![endif]-->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="js/mesh/modernizr.min.js"></script>
<script src="js/mesh/scripts.js" type="text/javascript"></script>
<script src="js/mesh/persistent.js" type="text/javascript"></script>
<script src="js/mesh/responsive.js" type="text/javascript"></script>
<script src="js/mesh/jquery.svgdom.min.js" type="text/javascript"></script>
<script src="js/mesh/jquery.svg.min.js" type="text/javascript"></script>
<script src="js/mesh/jquery.validate.min.js" type="text/javascript"></script>
<script src="js/mesh/viewport.min.js" type="text/javascript"></script>
<script>
	// Validation
	var validator = $("form").validate({
		rules: {
			name: "required",
			affiliation: "required",
			email: {
				required: true,
				email: true
			},
			subject: "required",
			comments: "required"
		},
		errorPlacement: function(error, element) {
			error.appendTo(element.next(".form-error"));
		}
	});

</script>

<script type="text/javascript">
	$("#mainContent").show();
	$("#noJSError").hide();
</script>
</body>
</html>
