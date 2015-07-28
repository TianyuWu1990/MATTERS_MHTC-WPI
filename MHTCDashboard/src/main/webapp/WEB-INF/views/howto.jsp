<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
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
<link href="img/MHTC_Favicon.jpg" rel="shortcut icon" >

<title>MATTERS</title>
<link rel="profile" href="http://gmpg.org/xfn/11">
<link href='http://fonts.googleapis.com/css?family=Muli:400,400italic' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,400,600' rel='stylesheet' type='text/css'>
<link href="css/mesh/base.css" rel="stylesheet" type="text/css">
<link href="css/mesh/main.css" rel="stylesheet" type="text/css">
<link href="css/mesh/main-responsive.css" rel="stylesheet" type="text/css">
<link href="css/mesh/animate.css" rel="stylesheet" type="text/css">

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

</head>

<body class="home" id="top">
<div class="wrap">
<div id="page" class="hfeed site">
	<jsp:include page="unifiedHeader.jsp"/>
<div class="content-area">
    <main class="site-main" role="main">
      
      <div class="main-content-wrap">
       <div class="container interior-container">


		   <div class="copy-block">
		   <h1 class="int-title"></h1>
		   <h2 class="int-sub-title">HOW TO USE MATTERS</h2>
		   
		   <p>MATTERS is a data analytics tool designed to empower users to measure the health of the technology environment in Massachusetts 
			(or any other state) and to allow easy and meaningful comparisons to any other state or group of states, with a particular focus 
			on other "peer" states whose economies are similarly "tech-centric".   MATTERS permits users to explore and analyze data that was 
			previously disaggregated and static.  We encourage users to "dive in" and explore the data and to utilize MATTERS as one of many 
			tools that comprise their research and analysis and inform their decision making.</p>
			<br>
		   <p> <a class="view-more-about" href="<c:url value="/"/>"> The MATTERS overview map </a> highlights the 15 states that make up the MATTERS peer group. By selecting any individual state, 
		   users can view 8 pre-selected key metrics and link to an individual state profile.</p>
			<br> 
		   <p> <a class="view-more-about" href="<c:url value="/explore"/>"> The MATTERS data explorer</a> permits users to customize their experience and retrieve data on one or more metrics from one or more states and across 
		   multiple years simultaneously.  Data can be displayed in a variety of visualizations including tables, line charts, bar 
		   charts, and heatmaps. </p>  
			
			
  
		<br></br>
		<p>MATTERS consolidates a set of key cost, economic, and talent metrics along with independent national rankings into a single 
		source for use by all parties.  MATTERS has been developed by faculty and students of Worcester Polytechnic Institute in 
		collaboration with a team of subject matter experts from Mass. High Tech Council members and partner organizations. </p>

		 </div> <!-- copy block -->
<!-- ====================================== SIDEBAR START ============== -->    

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
<script src="js/mesh/viewport.min.js" type="text/javascript"></script>

</body>
</html>