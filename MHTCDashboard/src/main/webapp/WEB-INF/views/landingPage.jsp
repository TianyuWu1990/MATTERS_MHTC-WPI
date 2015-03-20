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
	<title>MATTERS</title>
	<link rel="profile" href="http://gmpg.org/xfn/11">
	<link rel="stylesheet" href="https://cdn.caliper.com/mapplications/MHTC/MATTERS/2015/2/12/css">
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
	          height: 560px;
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
		compatible = compatible & Modernizr.generatedcontent;
		compatible = compatible & Modernizr.inlinesvg;
		compatible = compatible & Modernizr.svgclippaths;
		compatible = compatible & Modernizr.mediaqueries;
		compatible = compatible & Modernizr.boxsizing;
		compatible = compatible & Modernizr.bgpositionshorthand;
		compatible = compatible & Modernizr.bgpositionxy;
		
		if (!compatible)
			window.location = "./unsupported";			
	</script>
	
	
	<script type="text/javascript" src="https://cdn.caliper.com/mapplications/MHTC/MATTERS/2015/2/12/js"></script>
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
	<div class="container map-container" ng-app="map_application">
		<h1><p>Learn what MATTERS in your state.</p></h1>
			<h2><p>Click on any state below to display its current key performance metrics and view an individual state profile.</p>
				<p>Click the "Explore the Data" button below to access all of MATTERS multi-year comparative data from one or more states.</p>
				</h2>
		<div id="matters_map_canvas" class="map-area" ng-controller="map_canvas_controller as leaflet_map">
		</div>	
		<input type="hidden" ng-controller="map_tile_controller as map_tiles" id="map_tile" value="Tiles" storage="inline">
		<input type="hidden" ng-controller="map_data_controller as map_data"  id="map_data" value="{'Features':'Visible_Features','Search':'Visible_Features'}" storage="inline">
		<div id="html_static_templates" style="display: none;" ng-non-bindable>
			<script id="popup_content" type="text/template">
				<div class="popop-window">
				  <table style="border-style: none !important;" class="table table-bordered table-hover table-condensed popup-table">
					<tbody>
					  {{#cells}}
					  <tr>
						<td class="cell-name string {{class}}" colspan="2">{{name}}</td>
						<td class="cell-value {{class}}">{{format}}</td>
					  </tr>
					  {{/cells}}
					</tbody>
				  </table>
				  <a class="view-more" href="profile?name={{cells.0.format}}">LEARN MORE</a>
				</div>
			</script>
		</div>
		
		<!-- cxf -->
		<div id="map-label">
		<a class="map-btn db-btn" href="explore">Explore the Data</a>
		<a class="map-btn state" href="profile">View State Profiles</a>
		</div>
		<!-- cxf -->
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
				that help attract and retain business to the state.<br/><br/></p>
				<a class="view-more" href="<c:url value="/about"/>">LEARN MORE</a>

			</div>
		</div>
		<div class="bucket bucket-center">
			<h1 class="bucket-title">Methodology</h1>
			<div class="bucket-content">

				<p>MATTERS is not a primary data source, but instead is an aggregation of published data and analysis from several sources 
				including federal and state governmental agencies, non-profit organizations and media outlets.  Click "learn more" to access 
				a listing of the sources for each metric or data point in MATTERS with links to the source where available.				
				</p>
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
		   	<br/><br/></p>
			
				<a class="view-more" href="<c:url value="/howto"/>">LEARN MORE</a>

			</div>	
		</div>
	</div>
</section>

<!-- ==================================== PARTNERS SECTION ========= -->

	<div class="container partner-container">
		<h2>Program Partners</h2>

			<div class="logo-wrap">
				<div class="logo-contain">
				<a href = "http://www.emdmillipore.com/">
					<img src="img/EMD_Blue_RGB.jpg" alt="header-sponsors" width="50" height="50" />
					</a>
				 </div> <!-- .logo-contain -->
			 </div> <!-- .logo-wrap -->
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
				<a href = "http://www.caliper.com/">
					<img src="img/caliper-mapping-software.png" alt="header-sponsors" height="30" />
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
		  <p class="copy">2015. Worcester Polytechnic Institute. All Rights Reserved.<br />
		  Sponsored by Mass High Technology Council</p>

		</div><!-- .site-info -->
	</footer><!-- #colophon -->
</div><!-- #page -->


<!--[if lt IE 9]><script src="http://cdnjs.cloudflare.com/ajax/libs/html5shiv/r29/html5.min.js"></script><![endif]-->


<script type="text/javascript" src="js/mesh/data/tiles/tiles.js" ></script>
<script type="text/javascript" src="js/mesh/data/features.js" ></script>
<script type="text/javascript" src="js/mesh/data/search.js" ></script>  
<script src="js/mesh/modernizr.min.js"></script>
<script src="js/mesh/scripts.js" type="text/javascript"></script>
<script src="js/mesh/persistent.js" type="text/javascript"></script>
<script src="js/mesh/responsive.js" type="text/javascript"></script>
<script src="js/mesh/viewport.min.js" type="text/javascript"></script>

<script type="text/javascript">
$("#noJSError").hide();
</script>

</body>
</html>
