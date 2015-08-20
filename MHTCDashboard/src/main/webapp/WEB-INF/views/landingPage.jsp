<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html class=" js flexbox flexboxlegacy canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths js flexbox flexboxlegacy canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths" lang="en-US" style="">

<head>
	<meta charset="UTF-8">
	<meta name="description" content="Search matters Map">
	<meta name="keywords" content="MHTC,Matters,Ranks,Maps,GIS,Maptitude">
	<meta name="author" content="MHTC">
	<meta name="application-name" content="Maptitude for the Web">
	<meta name="company-name" content="MHTC">
	<meta name="application-date" content="2015/2/12">
	<meta name="application-version" content="2015.2.12">
	<meta name="generator" content="Maptitude">
	<meta name="apple-mobile-web-app-capable" content="yes"> 
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
	<link href="css/mesh/popup.css" rel="stylesheet" type="text/css">
	<link href="css/mesh/ie9compatibility.css" rel="stylesheet" type="text/css">
	<style>
	        #matters_map_canvas {
	          background-color: black;
	          max-width: 847px;
	          height: 500px;
	        }
			td.cell-name.string.name {
				column-span: none; /* W3C */
				-webkit-column-span: none; /* Safari & Chrome */
				-moz-column-span: none; /* Firefox */
				-ms-column-span: none; /* Internet Explorer */
				-o-column-span: none; /* Opera */
				display: none;
			}
			td.cell-value.string.name {
				color: red;
				text-align: center;
				font-weight: bold;
				column-span: all; /* W3C */
				-webkit-column-span: all; /* Safari & Chrome */
				-moz-column-span: all; /* Firefox */
				-ms-column-span: all; /* Internet Explorer */
				-o-column-span: all; /* Opera */
			}
			td.cell-name.peer {
				display: none;
			}
			td.cell-value.peer {
				display: none;
			}
	</style>
	
	<style>
	#matters_map_tooltip
	{
		z-index: 9999;
		position:absolute; 
		top: 0px; 
		width: 280px; 
		height: 325px; 
		background-color: rgba(255,255,255,0.9); 
		border-radius: 8px; 
		border: 2px solid  rgba(158,158,158, 0.5); 
		box-shadow: 0px 0px 2px rgba(158, 158, 158, 0.3);
	}
	
	#matters_map_tooltip_title
	{
		color: #7b0020;
		font-size: 20px;
		padding: 7px;
		text-align: center;
		
		border-bottom: 2px solid rgba(158, 158, 158, 0.3);
		
		display: block;
	}
	
	#matters_map_tooltip_stateProfile
	{
		margin-top: 5px;
		color: #7b0020;
		text-align: center;
		border-top: 2px solid rgba(158, 158, 158, 0.3);
		padding: 10px;
	}
	
	.matters_map_tooltip_metricName {
		display: inline-block;		
		padding-left: 5px;
		padding-top: 5px;
		padding-bottom: 5px;
		width: 210px;
		text-align: right;
	}
	
	.matters_map_tooltip_metricVal {
		text-align: right;
		width: 40px;
		display: inline-block;
	}
	
	#matters_map_tooltip_hide
	{
		float: right;
		cursor: pointer;
	}
	
	#matters_map_canvas
	{
		padding-left: 60px;
	}
		
	</style>
	
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
	<script type="text/javascript">
		$(document).on("ready", function() { $("#noJSError").hide(); });
	</script>
</head>

<body class="home" id="top">
<div class="wrap">
<div id="page" class="hfeed site">
	
	<jsp:include page="unifiedHeader.jsp"/>
<div class="home-bucket-container home-bucket-container-lower">

<!-- ==================================== MAP SECTION ========= -->

<section class="map-sect">
	<div id="noJSError" style="color: white; font-weight: bolder; font-size: 25px; text-align: center; padding-top: 20px;">
		WARNING: This page will not display correctly with JavaScript disabled. 
		<br/><br/>Please enable JavaScript and refresh your page.
		<br/>
		<br/>
	</div>
	<div class="map-container container ">	
		<h1><p>Learn what MATTERS in your state.</p></h1>
		
		<img class="map-image" src="img/MHTC_Map-Image.jpg" style="width:100%;height:100%;">

		<div id="map-label">
		<a class="map-btn db-btn" href="explore">Explore the Data</a>
		<a class="map-btn state" href="profile">View State Profiles</a>
		</div>
		<div id="matters_map_canvas" class="map-area" style="padding-top: 40px; position: relative;">
			<div id="matters_map_legend" style="position: absolute; bottom: 150px; right: 0px;">
				<div class="matters_map_legend_key" style="color: white;">
					<div class="swatch" style="background-color: #BD1C2E; width: 20px; height: 20px; border: 1px solid white; display: inline-block;">
					</div>
					<span style="margin-left: 5px;"><a href="<c:url value="/about"/>">Peer State</a></span>
					<br/>
					<div class="swatch" style="background-color: #5F0410; width: 20px; height: 20px; border: 1px solid white; display: inline-block;">
					</div>
					<span style="margin-left: 5px;">Other State</span>
				</div>
			</div>
			
			<div id="matters_map_tooltip" style="display:none;">
				<div id="matters_map_tooltip_title"><span id="matters_map_tooltip_name">State Name</span>
					<a id="matters_map_tooltip_hide">X</a>
				</div>
				<div class="matters_map_tooltip_metricName">CNBC Top States for Business</div>
				<span id="metric_cnbc" class="matters_map_tooltip_metricVal">VA</span><br/>
				
				<div class="matters_map_tooltip_metricName">Milken Tech and Science Index</div>
				<span id="metric_milken" class="matters_map_tooltip_metricVal">VA</span><br/>
				
				<div class="matters_map_tooltip_metricName">Tax Foundation Business Tax Index</div>
				<span id="metric_tax_found" class="matters_map_tooltip_metricVal">VA</span><br/>
				
				<div class="matters_map_tooltip_metricName">Tax Burden Per Capita Rank</div>
				<span id="metric_tax_burden" class="matters_map_tooltip_metricVal">VA</span><br/>
				
				<div class="matters_map_tooltip_metricName">Tech Employment Rank</div>
				<span id="metric_tech" class="matters_map_tooltip_metricVal">VA</span><br/>
				
				<div class="matters_map_tooltip_metricName">Unemployment Insurance Rank</div>
				<span id="metric_unemp" class="matters_map_tooltip_metricVal">VA</span><br/>
				
				<div class="matters_map_tooltip_metricName">Bachelor's Degree Holder Rank</div>
				<span id="metric_bach" class="matters_map_tooltip_metricVal">VA</span><br/>
				
				<div class="matters_map_tooltip_metricName">Key Tech Demand Hiring Rank</div>
				<span id="metric_demand" class="matters_map_tooltip_metricVal">VA</span><br/>
				
				<div id="matters_map_tooltip_stateProfile"><a id="matters_map_tooltip_learnMore">Learn More</a></div>
			</div>
			
		</div>	
		<h2><p>Click on any state above to display its current key performance metrics and view an individual state profile.</p>
			<p>Click the "Explore the Data" button above to access all of MATTERS multi-year comparative data from one or more states.</p>
			<br/>
		</h2>		
	</div>
</section>

<!-- ==================================== QUOTE SECTION ========= -->

	<div class="container quote-container">
		<p>"Until MATTERS, enormously valuable data resided in disparate places - and in static form - effectively locked away from 
		those who might leverage it the most to make informed decisions. By aggregating and injecting dynamism into those key data sets, 
		MATTERS will equip policymakers, business leaders, advocates and researchers with a real-time data analytics tool that will help 
		shape our public policy agenda, our debates and the outcomes of key decisions to be made in Massachusetts for years to come."</p>
		<p class="quote-credit">Gary Beach, Editor Emeritus, CIO Magazine</p>
	</div>
	
<!-- ==================================== BUCKET SECTION ========= -->

<section class="bucket-sect">
	<div class="container bucket-contianer">
		<div class="bucket bucket-left">
			<h1 class="bucket-title">About</h1>
			<div class="bucket-content">

				<p>The Massachusetts High Technology Council developed the Massachusetts Technology, Talent and Economic Reporting 
				System to advance our mission to make Massachusetts the world's most attractive place in which to create 
				and grow a high technology business.  MATTERS is designed to measure and evaluate Massachusetts' current competitive 
				position, while providing policy makers and advocates with dynamic, searchable data to inform public policy decisions 
				that help attract and retain business to the state.<br/><br/><br/></p>
				<a class="view-more" href="<c:url value="/about"/>">LEARN MORE</a>

			</div>
		</div>
		<div class="bucket bucket-center">
			<h1 class="bucket-title">Methodology</h1>
			<div class="bucket-content">

				<p>MATTERS is not a primary data source, but instead is an aggregation of published data and analysis from several sources 
				including federal and state governmental agencies, non-profit organizations and media outlets.  Click "learn more" to access 
				a listing of the sources for each metric or data point in MATTERS with links to the source where available.				
				<br/><br/></p>
				<a class="view-more" href="<c:url value="/methodology"/>">LEARN MORE</a>

			</div>
		</div>
		<div class="bucket bucket-right">
			<h1 class="bucket-title">How to Use <span class="dt-only">MATTERS</span></h1>
			<div class="bucket-content">

				<p>The MATTERS overview map highlights the 15 states that make up the MATTERS peer group technology states. By selecting any individual state, 
		   users can view 8 pre-selected key metrics and link to an individual state profile. The data explorer permits users to customize their 
		   experience and retrieve data on one or more metrics from one or more states and across multiple years simultaneously. Data can be 
		   displayed in a variety of visualizations including tables, line charts, bar charts, and heatmaps. 
		   	<br/><br/><br/></p>
			
				<a class="view-more" href="<c:url value="/howto"/>">LEARN MORE</a>

			</div>	
		</div>
	</div>
</section>

<!-- ==================================== PARTNERS SECTION ========= -->
	<div class="container partner-container">
	<h2>Sponsors</h2>
		<div class="logo-wrap">
				<div class="logo-contain">
				<a href = "http://www.emdmillipore.com/">
					<img src="img/EMD_Blue_RGB.jpg" alt="header-sponsors"  height="90" />
					</a>
				 </div> <!-- .logo-contain -->
			 </div> <!-- .logo-wrap -->
			 <div class="logo-wrap">
				<div class="logo-contain">
				<a href = "http://www.massdevelopment.com/">
					<img src="img/massdevelopment_logo_HIRES.jpg" alt="header-sponsors" height="50"/>
					</a>
				 </div> <!-- .logo-contain -->
			 </div> <!-- .logo-wrap -->
			  <div class="logo-wrap">
				<div class="logo-contain">
				<a href = "http://www.mass.gov/portal/">
					<img src="img/state seal hi res.jpg" alt="header-sponsors" height="100"/>
					</a>
				 </div> <!-- .logo-contain -->
			 </div> <!-- .logo-wrap -->
	</div>



	<div class="container partner-container">
		<h2>Program Partners</h2>

			
			<div class="logo-wrap">
				<div class="logo-contain">
				<a href = "http://www.wpi.edu/">
					<img src="img/WPI_Inst_Prim_FulClr_RGB.png" alt="header-sponsors" height="50"/>
					</a>
				 </div> <!-- .logo-contain -->
			 </div> <!-- .logo-wrap -->
			 <div class="logo-wrap">
				<div class="logo-contain">
				<a href = "http://www.nebhe.org/">
					<img src="img/NEBHE_Banner.png" alt="header-sponsors" height="50" />
					</a>
				 </div> <!-- .logo-contain -->
			 </div> <!-- .logo-wrap -->
			 <div class="logo-wrap">
				<div class="logo-contain">
				<a href = "http://monstergovernmentsolutions.com/">
					<img src="img/MGS_wm_MGS_purp_rgb_stacked.png" alt="header-sponsors" height="50" />
					</a>
				 </div> <!-- .logo-contain -->
			 </div> <!-- .logo-wrap -->
			<div class="logo-wrap">
				<div class="logo-contain">
				<a href = "http://www.mitre.org/">
					<img src="img/mitrelogo-blue.jpg" alt="header-sponsors" height="50" />
					</a>
				 </div> <!-- .logo-contain -->
			 </div> <!-- .logo-wrap -->
			 
			<div class="logo-wrap">
				<div class="logo-contain">
				<a href = "http://meshagency.com/">
					<img src="img/mesh.jpg" alt="header-sponsors"  height="30" />
					</a>
				 </div> <!-- .logo-contain -->
			 </div> <!-- .logo-wrap -->
	</div>
</div>

	</div><!-- #content -->
	<footer id="colophon" class="site-footer" role="contentinfo">
		<div class="site-info container">
		  <p class="copy">&copy; 2015. Worcester Polytechnic Institute. All Rights Reserved.<br />
		  Sponsored by Mass High Technology Council</p>
		</div><!-- .site-info -->
	</footer><!-- #colophon -->
</div><!-- #page -->


<!--[if lt IE 9]><script src="http://cdnjs.cloudflare.com/ajax/libs/html5shiv/r29/html5.min.js"></script><![endif]-->
<script src="js/raphael.js"></script> <!-- Dependency for raphael.js -->
<script src="js/jquery.usmap.js"></script>
<script src="js/mesh/data/state_profile_data.js" type="text/javascript"></script>
<script src="js/mesh/map.js" type="text/javascript"></script>
<script src="js/mesh/scripts.js" type="text/javascript"></script>
<script src="js/mesh/persistent.js" type="text/javascript"></script>
<script src="js/mesh/responsive.js" type="text/javascript"></script>
<script src="js/mesh/viewport.min.js" type="text/javascript"></script>
<script>
			(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
 			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	 		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
 			})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

 			ga('create', 'UA-61279483-1', 'auto');
 			ga('send', 'pageview');

</script>
<script src="js/jquery.history.js" type="text/javascript"></script>
<script type="text/javascript">
$("#noJSError").hide();
</script>
</body>
</html>
