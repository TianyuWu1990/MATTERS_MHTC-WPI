<!DOCTYPE html>
<html class="js flexbox flexboxlegacy canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths js flexbox flexboxlegacy canvas canvastext webgl no-touch geolocation postmessage no-websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients no-cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths" lang="en-US" style="">

<head>
	<meta charset="UTF-8">
	<meta name="description" content="Search matters Map">
	<meta name="keywords" content="MHTC,Matters,Maps,GIS,Maptitude">
	<meta name="author" content="MHTC">
	<meta name="application-name" content="Maptitude for the Web">
	<meta name="company-name" content="MHTC">
	<meta name="application-date" content="2015/2/12">
	<meta name="application-version" content="2015.2.12">
	<meta name="generator" content="Maptitude">
	<meta name="apple-mobile-web-app-capable" content="yes"> 
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link href="img/MHTC_Favicon.jpg" rel="shortcut icon" >
	
	<title>MATTERS State Profiles</title>
	<link rel="profile" href="http://gmpg.org/xfn/11">
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Muli:400,400italic' rel='stylesheet' type='text/css'>
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,400,600' rel='stylesheet' type='text/css'>
	<link href="css/mesh/base.css" rel="stylesheet" type="text/css">
	<link href="css/mesh/main.css" rel="stylesheet" type="text/css">
	<link href="css/mesh/main-responsive.css" rel="stylesheet" type="text/css">
	<link href="css/mesh/animate.css" rel="stylesheet" type="text/css">
	<link href="css/stateProfiles.css" rel="stylesheet" type="text/css">
	
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
		$(document).on("ready", function() { 
		$("#noJSError").hide(); 
		$("#mainContent").show();});
	</script>
	
</head>

<body class="home" id="top">
<div class="wrap">
<div id="page" class="hfeed site">
	
	<jsp:include page="unifiedHeader.jsp"/>
<div class="content-area">
    <main class="site-main" role="main">
       <div class="main-content-wrap">
       <!-- available state.properties:
					   "ID", "Name", "Peer State Group", "Milken Science and Tech Index",
                       "Tax Foundation Business Tax Index", "CNBC Top States for Business",
                       "Percent bachelors degree holders", "Bachelors degree holders rank",
                       "Key tech demand hiring difficulty", "Key tech demand hiring rank", "Tax burden per capita",
                       "Tax burden per capita rank", "Percent tech employment", "Tech employment rank", "Unempl insurance",
                       "Unempl insurance rank"
       -->
       <div id="noJSError" style="color: #680017; font-weight: bolder; font-size: 25px; text-align: center; padding-top: 20px;">
			WARNING: You must have JavaScript enabled to use this page.
			<br/>
			<br/>
		</div>
		<div class="container" style="overflow-x:hidden; display:none;" id="mainContent">
		
			<div class="state-profile-title">
				<div id="stateChooserWrapper">
					<div id="stateChooserTitle">
					<span id="stateTitleAct"></span> <i class="fa fa-caret-down"></i> 
					</div>
					
					<div id="stateChooser" style="display: none;">
						<ul>
							<li><a class="stateChoice">Alabama</a></li>
							<li><a class="stateChoice">Alaska</a></li>
							<li><a class="stateChoice">Arizona</a></li>
							<li><a class="stateChoice">Arkansas</a></li>
							<li><a class="stateChoice">California</a></li>
							<li><a class="stateChoice">Colorado</a></li>
							<li><a class="stateChoice">Connecticut</a></li>
							<li><a class="stateChoice">Delaware</a></li>
							<li><a class="stateChoice">Florida</a></li>
							<li><a class="stateChoice">Georgia</a></li>
							<li><a class="stateChoice">Hawaii</a></li>
							<li><a class="stateChoice">Idaho</a></li>
							<li><a class="stateChoice">Illinois</a></li>
							<li><a class="stateChoice">Indiana</a></li>
							<li><a class="stateChoice">Iowa</a></li>
							<li><a class="stateChoice">Kansas</a></li>
							<li><a class="stateChoice">Kentucky</a></li>
							<li><a class="stateChoice">Louisiana</a></li>
							<li><a class="stateChoice">Maine</a></li>
							<li><a class="stateChoice">Maryland</a></li>
							<li><a class="stateChoice">Massachusetts</a></li>
							<li><a class="stateChoice">Michigan</a></li>
							<li><a class="stateChoice">Minnesota</a></li>
							<li><a class="stateChoice">Mississippi</a></li>
							<li><a class="stateChoice">Missouri</a></li>
							<li><a class="stateChoice">Montana</a></li>
							<li><a class="stateChoice">Nebraska</a></li>
							<li><a class="stateChoice">Nevada</a></li>
							<li><a class="stateChoice">New Hampshire</a></li>
							<li><a class="stateChoice">New Jersey</a></li>
							<li><a class="stateChoice">New Mexico</a></li>
							<li><a class="stateChoice">New York</a></li>
							<li><a class="stateChoice">North Carolina</a></li>
							<li><a class="stateChoice">North Dakota</a></li>
							<li><a class="stateChoice">Ohio</a></li>
							<li><a class="stateChoice">Oklahoma</a></li>
							<li><a class="stateChoice">Oregon</a></li>
							<li><a class="stateChoice">Pennsylvania</a></li>
							<li><a class="stateChoice">Rhode Island</a></li>
							<li><a class="stateChoice">South Carolina</a></li>
							<li><a class="stateChoice">South Dakota</a></li>
							<li><a class="stateChoice">Tennessee</a></li>
							<li><a class="stateChoice">Texas</a></li>
							<li><a class="stateChoice">Utah</a></li>
							<li><a class="stateChoice">Vermont</a></li>
							<li><a class="stateChoice">Virginia</a></li>
							<li><a class="stateChoice">Washington</a></li>
							<li><a class="stateChoice">West Virginia</a></li>
							<li><a class="stateChoice">Wisconsin</a></li>
							<li><a class="stateChoice">Wyoming</a></li>
						</ul>
					</div>
				</div> 
				
				State Profile 
			</div>
			
			<div class="index key">
				<div class="state-strength" style="display: inline-block;"></div><span>Strength</span>
				<div class="state-weakness" style="display: inline-block;"></div><span>Weakness</span>
			</div>
			<h2>National Ranking and Data</h2>
			<hr class="state-ranking">
			<div style="width: 100%; overflow-x: scroll;">
			
			<table style="min-width: 750px;">
			<tr class="ranking-header">
				<td class="rank">Rank</td>
				<td class="data">Data</td>
				<td class="status">Status</td>
				<td class="year">Year</td>
				<td class="survey">Index / Survey</td>
				<td class="source">Source</td>
			</tr>
			<tr class="row" >
				<td class="rank" id="milken"></td>
				<td class="data">-</td>
				<td class="status"><div id="milken-status"></div></td>
				<td class="year">2014</td>
				<td class="survey">Milken State Science and Technology Index</td>
				<td class="source">Milken Institute</td>
			</tr>
			<tr class="row" >
				<td class="rank" id="tax-found"></td>
				<td class="data">-</td>
				<td class="status"><div id="tax-found-status"></div></td>
				<td class="year">2015</td>
				<td class="survey">State Business Tax Climate Index</td>
				<td class="source">Tax Foundation</td>
			</tr>
			<tr class="row" >
				<td class="rank" id="cnbc"></td>
				<td class="data">-</td>
				<td class="status"><div id="cnbc-status"></div></td>
				<td class="year">2014</td>
				<td class="survey">CNBC Top States for Business</td>
				<td class="source">CNBC</td>
			</tr>
			<tr class="row" >
				<td class="rank" id="tech-demand"></td>
				<td class="data" id="tech-demand-data"></td>
				<td class="status"><div id="tech-demand-status"></div></td>
				<td class="year">2015</td>
				<td class="survey">Tech Demand Hiring Difficulty</td>
				<td class="source">Wanted Analytics and Monster Government Solutions</td>
			</tr>
			<tr class="row" >
				<td class="rank" id="tech-emp"></td>
				<td class="data" id="tech-emp-data"></td>
				<td class="status"><div id="tech-emp-status"></div></td>
				<td class="year">2012</td>
				<td class="survey">Tech Employment as a % of Workforce</td>
				<td class="source">National Science Foundation and Bureau of Labor Statistics, Occupational Employment  Statistics Survey</td>
			</tr>
			<tr class="row" >
				<td class="rank" id="bach"></td>
				<td class="data" id="bach-data"></td>
				<td class="status"><div id="bach-status"></div></td>
				<td class="year">2011</td>
				<td class="survey">Bachelors degree holders as a % of Workforce</td>
				<td class="source">US Census/Bureau of Labor Statistics</td>
			</tr>
			<tr class="row" >
				<td class="rank" id="unemp"></td>
				<td class="data" id="unemp-data"></td>
				<td class="status"><div id="unemp-status"></div></td>
				<td class="year">2013</td>
				<td class="survey">Unemployment Insurance Average Premium per Employee</td>
				<td class="source">US Department of Labor - Employment and Training Administration, Bureau of Labor Statistics</td>
			</tr>
			<tr class="row" >
				<td class="rank" id="tax-burden"></td>
				<td class="data" id="tax-burden-data"></td>
				<td class="status"><div id="tax-burden-status"></div></td>
				<td class="year">2011</td>
				<td class="survey">State and Local Tax Burden per capita</td>
				<td class="source">U.S. Census</td>
			</tr>
			
			</table>
			
			</div>
			<hr class="state-ranking">
			<p class="note">
			Note: Status attributes top 10 ranking as a STRENGTH. bottom 25 ranking as a WEAKNESS
			</p>
			
		</div>


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


<!--[if lt IE 9]><script src="http://cdnjs.cloudflare.com/ajax/libs/html5shiv/r29/html5.min.js"></script><![endif]-->
<script src="js/mesh/scripts.js" type="text/javascript"></script>
<script src="js/mesh/persistent.js" type="text/javascript"></script>
<script src="js/mesh/responsive.js" type="text/javascript"></script>
<script src="js/mesh/jquery.svgdom.min.js" type="text/javascript"></script>
<script src="js/mesh/jquery.svg.min.js" type="text/javascript"></script>
<script src="js/mesh/viewport.min.js" type="text/javascript"></script>
<script src="js/jquery.history.js" type="text/javascript"></script>
<script src="js/mesh/data/state_profile_data.js" type="text/javascript"></script>
<script type="text/javascript" src="js/stateChooser.stateProfiles.js"></script>
</body>
</html>
