<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html class=" js flexbox flexboxlegacy canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths js flexbox flexboxlegacy canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths" lang="en-US" style="">


<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<meta name="description" content="Search matters Map">
<meta name="keywords" content="Maps,GIS,Maptitude">
<meta name="author" content="MHTC">
<meta name="application-name" content="Maptitude for the Web">
<meta name="company-name" content="MHTC">
<meta name="application-date" content="2015/2/12">
<meta name="application-version" content="2015.2.12">
<meta name="generator" content="Maptitude">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="img/MHTC_Favicon.jpg" rel="shortcut icon" >

<title>Users Feedback</title>
<link href="../mhtc/adminPanel/css/bootstrap.min.css" rel="stylesheet">
<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="profile" href="http://gmpg.org/xfn/11">
<link href='http://fonts.googleapis.com/css?family=Muli:400,400italic' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,400,600' rel='stylesheet' type='text/css'>
<link href="css/mesh/base.css" rel="stylesheet" type="text/css">
<link href="css/mesh/main.css" rel="stylesheet" type="text/css">
<link href="css/mesh/main-responsive.css" rel="stylesheet" type="text/css">
<link href="css/mesh/animate.css" rel="stylesheet" type="text/css">
<link href="img/MHTC_Favicon.jpg" rel="shortcut icon" >
<!-- Check for browser compatibility before we do anything else -->
<script src="js/modernizr.js"></script>


<script type="text/javascript">
var compatible = true;
compatible = compatible & Modernizr.rgba;
compatible = compatible & Modernizr.backgroundsize;
compatible = compatible & Modernizr.borderradius;
compatible = compatible & Modernizr.boxshadow;
compatible = compatible & Modernizr.opacity;
compatible = compatible & Modernizr.csstransforms;
compatible = compatible & Modernizr.svg;
compatible = compatible & Modernizr.canvas;
compatible = compatible & Modernizr.generatedcontent;
compatible = compatible & Modernizr.inlinesvg;
compatible = compatible & Modernizr.svgclippaths;
compatible = compatible & Modernizr.mediaqueries;
compatible = compatible & Modernizr.boxsizing;
compatible = compatible & Modernizr.bgpositionshorthand;
 
if (!compatible)
window.location = "./unsupported";
</script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

</head>

<body >
<div class="wrap">
<div id="page" class="hfeed site">
<jsp:include page="unifiedHeader.jsp"/>
<div class="content-area">
    <div class="site-main" role="main">
      
      <div class="main-content-wrap">
       <div class="container interior-container">


  <div class="copy-block">
  <h1 class="int-title"></h1>
  
  <h2 class="int-sub-title">Users Feedback</h2>
  
      
                          
          
                        

                  
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
                                       
             
        
   
 

  </div> <!-- copy block -->
<!-- ====================================== SIDEBAR START ============== -->    
    
 




</div><!-- container -->
    
    
      </div><!-- main-content-wrap -->
    </div><!-- #main -->
</div><!-- #primary -->
<footer id="colophon" class="site-footer" role="contentinfo">
<div class="site-info container">
 <p class="copy">2015. Worcester Polytechnic Institute. All Rights Reserved.<br />
 Sponsored by Mass High Technology Council</p>

</div><!-- .site-info -->
</footer><!-- #colophon -->
</div><!-- #page -->
</div>

<!--[if lt IE 9]><script src="//cdnjs.cloudflare.com/ajax/libs/html5shiv/r29/html5.min.js"></script><![endif]-->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="js/mesh/modernizr.min.js"></script>
<script src="js/mesh/scripts.js" type="text/javascript"></script>
<script src="js/mesh/persistent.js" type="text/javascript"></script>
<script src="js/mesh/responsive.js" type="text/javascript"></script>
<script src="js/mesh/jquery.svgdom.min.js" type="text/javascript"></script>
<script src="js/mesh/jquery.svg.min.js" type="text/javascript"></script>
<script src="js/mesh/viewport.min.js" type="text/javascript"></script>

</body>
</html>