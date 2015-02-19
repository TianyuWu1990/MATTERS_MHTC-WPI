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
	<title>MATTERS</title>
	<link rel="profile" href="http://gmpg.org/xfn/11">
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdn.caliper.com/mapplications/MHTC/MATTERS/2015/2/12/css">
	<link href='http://fonts.googleapis.com/css?family=Muli:400,400italic' rel='stylesheet' type='text/css'>
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,400,600' rel='stylesheet' type='text/css'>
	<link href="css/mesh/base.css" rel="stylesheet" type="text/css">
	<link href="css/mesh/main.css" rel="stylesheet" type="text/css">
	<link href="css/mesh/animate.css" rel="stylesheet" type="text/css">
	<link href="css/stateProfiles.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="https://cdn.caliper.com/mapplications/MHTC/MATTERS/2015/2/12/libjs"></script>
	<script type="text/javascript" src="js/mesh/caliper/profile-app.js"></script>
</head>

<body class="home" id="top">
<div class="wrap">
<div id="page" class="hfeed site">
	
	<jsp:include page="unifiedHeader.jsp"/>
<div class="content-area">
    <main class="site-main" role="main">
       <div class="main-content-wrap" ng-app="map_application">
       <!-- available state.properties:
					   "ID", "Name", "Peer State Group", "Milken Science and Tech Index",
                       "Tax Foundation Business Tax Index", "CNBC Top States for Business",
                       "Percent bachelors degree holders", "Bachelors degree holders rank",
                       "Key tech demand hiring difficulty", "Key tech demand hiring rank", "Tax burden per capita",
                       "Tax burden per capita rank", "Percent tech employment", "Tech employment rank", "Unempl insurance",
                       "Unempl insurance rank"
       -->
		<div class="container" ng-controller="profile_controller as state" initial="Massachusetts">
		
			<div class="state-profile-title">
				<div id="stateChooserWrapper">
					<div id="stateChooserTitle">
					{{state.properties.Name}} <i class="fa fa-caret-down"></i> 
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
				<div class="state-strength"></div><span class="index-span">Strength</span>
				<div class="state-weakness"></div><span class="index-span">Weakness</span>
			</div>
			<h2>National Ranking and Data</h2>
			<hr class="state-ranking">
			<div class="index ranking-header">
				<div class="rank">Rank</div>
				<div class="data">Data</div>
				<div class="status">Status</div>
				<div class="year">Year</div>
				<div class="survey">Index / Survey</div>
				<div class="source">Source</div>
			</div>

					<div class="index row">
						<div class="rank">{{state.properties["Milken Science and Tech Index"]}}</div>
						<div class="data">-</div>
						<div class="status"><div class='{{ state.get_rank_class(state.properties["Milken Science and Tech Index"]) }}'></div></div>
						<div class="year">2014</div>
						<div class="survey">Milken State Science and Technology Index</div>
						<div class="source">Milken Institute</div>
					</div>
					<div class="index row">
						<div class="rank">{{state.properties["Tax Foundation Business Tax Index"]}}</div>
						<div class="data">-</div>
						<div class="status"><div class='{{ state.get_rank_class(state.properties["Tax Foundation Business Tax Index"]) }}'></div></div>
						<div class="year">2015</div>
						<div class="survey">State Business Tax Climate Index</div>
						<div class="source">Tax Foundation</div>
					</div>
					<div class="index row">
						<div class="rank">{{state.properties["CNBC Top States for Business"]}}</div>
						<div class="data">-</div>
						<div class="status"><div class='{{ state.get_rank_class(state.properties["CNBC Top States for Business"]) }}'></div></div>
						<div class="year">2014</div>
						<div class="survey">CNBC Top States for Business</div>
						<div class="source">CNBC</div>
					</div>
					<div class="index row">
						<div class="rank">{{state.properties["Key tech demand hiring rank"]}}</div>
						<div class="data">{{state.properties["Key tech demand hiring difficulty"]}}</div>
						<div class="status"><div class='{{ state.get_rank_class(state.properties["Key tech demand hiring rank"]) }}'></div></div>
						<div class="year">2015</div>
						<div class="survey">Tech Demand Hiring Difficulty</div>
						<div class="source">Wanted Analytics and Monster Government Solutions</div>
					</div>
					<div class="index row">
						<div class="rank">{{state.properties["Tech employment rank"]}}</div>
						<div class="data">{{state.properties["Percent tech employment"]}}%</div>
						<div class="status"><div class='{{ state.get_rank_class(state.properties["Tech employment rank"]) }}'></div></div>
						<div class="year">2012</div>
						<div class="survey">Tech Employment as a % of Workforce</div>
						<div class="source">National Science Foundation and Bureau of Labor Statistics, Occupational Employment  Statistics Survey</div>
					</div>
					<div class="index row">
						<div class="rank">{{state.properties["Bachelors degree holders rank"]}}</div>
						<div class="data">{{state.properties["Percent bachelors degree holders"]}}%</div>
						<div class="status"><div class='{{ state.get_rank_class(state.properties["Bachelors degree holders rank"]) }}'></div></div>
						<div class="year">2011</div>
						<div class="survey">Bachelors degree holders as a % of Workforce</div>
						<div class="source">US Census/Bureau of Labor Statistics</div>
					</div>
					<div class="index row">
						<div class="rank">{{state.properties["Unempl insurance rank"]}}</div>
						<div class="data">${{state.properties["Unempl insurance"]}}</div>
						<div class="status"><div class='{{ state.get_rank_class(state.properties["Unempl insurance"]) }}'></div></div>
						<div class="year">2013</div>
						<div class="survey">Unemployment Insurance Average Premium per Employee</div>
						<div class="source">US Department of Labor - Employment and Training Administration, Bureau of Labor Statistics</div>
					</div>
					<div class="index row">
						<div class="rank">{{state.properties["Tax burden per capita rank"]}}</div>
						<div class="data">${{state.properties["Tax burden per capita"]}}</div>
						<div class="status"><div class='{{ state.get_rank_class(state.properties["Tax burden per capita rank"]) }}'></div></div>
						<div class="year">2011</div>
						<div class="survey">State and Local Tax Burden per capita</div>
						<div class="source">U.S. Census</div>
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
<script src="js/mesh/modernizr.min.js"></script>
<script src="js/mesh/scripts.js" type="text/javascript"></script>
<script src="js/mesh/persistent.js" type="text/javascript"></script>
<script src="js/mesh/responsive.js" type="text/javascript"></script>
<script src="js/mesh/jquery.svgdom.min.js" type="text/javascript"></script>
<script src="js/mesh/jquery.svg.min.js" type="text/javascript"></script>
<script src="js/mesh/viewport.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/mesh/data/state-profiles.js"></script>
<script type="text/javascript" src="js/stateChooser.stateProfiles.js"></script>
</body>
</html>