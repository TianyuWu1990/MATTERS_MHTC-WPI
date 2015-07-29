<% /*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
 %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="img/MHTC_Favicon.jpg" rel="shortcut icon" >
		
		<!-- Library CSS -->
		<link href="css/nv.d3.css" rel="stylesheet">
		<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
		<link href="css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet">
		<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
		<link href="css/dataTables.css" rel="stylesheet">
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext'
			rel='stylesheet' type='text/css'>
			
		<!-- Custom CSS -->
		<link href="css/mesh/base.css" rel="stylesheet">
		<link href="css/mesh/main.css" rel="stylesheet">
		<link href="css/mesh/main-responsive.css" rel="stylesheet">
		
		<link href="css/style.css" id="base-style" rel="stylesheet">
		<link href="css/style-responsive.css" id="base-style-responsive" rel="stylesheet">
		<link href="css/style-print.css" id="base-style-print" rel="stylesheet">
			
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
		
		<!-- Library JS -->
		<script src="js/d3.v3.min.js"></script>
		<script src="js/nv.d3.min.js"></script>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
		
		<script src="js/raphael.js"></script> <!-- Dependency for raphael.js -->
		<script src="js/jquery.usmap.js"></script>
		
		<script src="js/bootstrap.min.js"></script>
		<script src="js/dataTables.js"></script>
		
		<script type="text/javascript" src="js/rgbcolor.js"></script> 
		<script type="text/javascript" src="js/StackBlur.js"></script>
		<script type="text/javascript" src="js/canvg.js"></script> 
		
		<!-- Custom JS -->
		<script src="js/dataquery.js"></script>
		<script src="js/state.js"></script>
		<script src="js/metric.js"></script>
		<script src="js/chart.js"></script>
		<script src="js/appstate.js"></script>
		
		<title>MATTERS</title>
	
	</head>


	<body>
	
	<jsp:include page="unifiedHeader.jsp"/>

	<div class="container-fluid-full" style="z-index: 1;" id="mainContentDiv">
				<!-- start: left sidebar -->
				<div id="sidebar-left" class="sidebar open">
					<div class="column" id="metricSelectionCol">
						<div class="title" >
							<span>Select Metrics</span>
						</div>
						<div class="sidebar-content accordion-menu" id="metricListWrapper">
							<ul>
								<li>
									<a class="metricHeader" >
									National Ranking 
										<i class="fa fa-chevron-down" id="hoverstyle">
										</i>
									</a>
								
									<ul id="nationalProfileList" class="metricList">
										<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all" id="select">
											<a style="text-align:right;">Select All</a>
										</li>
										<c:forEach items="${jv_stats_national}" var="stat2">
											<li>
												<a class="metricOption" id="${stat2.metric.id}" title="${stat2.metric.desc}"
												data-toggle="popover"  
												data-content="<a href='${stat2.metric.urlFrom}' target='_blank'>Source: ${stat2.metric.sourceName}</a>"> 
													<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
													${stat2.metric.name} 
												</a>
											</li>
										</c:forEach>
									</ul>
								</li>
								<li>
									<a class="metricHeader">
										Talent Metrics 
										<i class="fa fa-chevron-down" id="hoverstyle">
										</i>
									</a>
									<ul id="talentProfileList" class="metricList">
										<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all" id="select">
											<a style="text-align:right;">Select All</a>
										</li>
										<c:forEach items="${jv_stats_talent}" var="stat3">
											<li>
												<a class="metricOption" id="${stat3.metric.id}" title="${stat3.metric.desc}"
												data-toggle="popover"  
												data-content="<a href='${stat3.metric.urlFrom}' target='_blank'>Source: ${stat3.metric.sourceName}</a>"> 
													<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
													${stat3.metric.name}
												</a>
											</li>
										</c:forEach>
									</ul>
								</li>
								<li>
									<a class="metricHeader">
										Cost Metrics
										<i class="fa fa-chevron-down" id="hoverstyle">
										</i>
									</a>
									<ul id= "costProfileList" class="metricList">
										<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all" id="select">
											<a style="text-align:right;">Select All</a>
										</li>
										<c:forEach items="${jv_stats_cost}" var="stat4">
											<li>
												<a class="metricOption" id="${stat4.metric.id}" title="${stat4.metric.desc}"
												data-toggle="popover"  
												data-content="<a href='${stat4.metric.urlFrom}' target='_blank'>Source: ${stat4.metric.sourceName}</a>">
													<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
													${stat4.metric.name}
												</a>
											</li>
										</c:forEach>
									</ul>
								</li>
								<li>
									<a class="metricHeader">
										Economy Metrics
										<i class="fa fa-chevron-down" id="hoverstyle">
										</i>
									</a>
									<ul id="economyProfileList" class="metricList">
										<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all" id="select">
											<a style="text-align:right;">Select All</a>
										</li>
										<c:forEach items="${jv_stats_economy}" var="stat5">
											<li>
												<a class="metricOption" id="${stat5.metric.id}" title="${stat5.metric.desc}"
												data-toggle="popover" 
												 
												data-content="<a href='${stat5.metric.urlFrom}' target='_blank'>Source: ${stat5.metric.sourceName}</a>"> 
													<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
													${stat5.metric.name}
												</a>
											</li>
										</c:forEach>
									</ul>
								</li>
							</ul>
						</div>
					</div>
					<div class="column" id="stateSelectionCol">
						<div class="title">
							<span>Select States</span>
						</div>
						<div class="sidebar-content single-section">
							<ul id="stateSelection">
								<li class="selectUnselectAllStates" data-toggle="tooltip" title="Click to select/deselect all" id="select">
									<a style="text-align:right;">
										Select All
									</a>
								</li>
								<li>
								
									<a class="selectPeerStates" id="${row.id}" title="15 MATTERS Key Technology States"
									data-toggle= "popover"
									data-content= "<a href= '<c:url value="/methodology"/>' target= '_blank'> Learn More </a>" >
										<i class="fa fa-circle"></i>
										
										Peer States
									</a>
								</li>
								<li class="stateFilter">
									<input type="text" placeholder="Filter states...">
								</li>
								<c:forEach items="${jv_all_states}" var="stat">
									<c:forEach items="${stat.row}" var="row">
										<li class="stateSelectionOptionWrapper" id="${row.id}">
									       <a class="stateSelectionOption" id="${row.id}"> 			
									        	<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
												${row.name} (${row.abbr})
									        </a>
										</li>
									</c:forEach>
								</c:forEach>	
							</ul>
						</div>
					</div>	
				</div>
			  	<!--  end: left sidebar -->
		    
				<!-- start: Content -->
			
				<div id="content">
					<div class="pagination pagination-right">
						<div id="metrics-trigger-wrapper">
							<label id="metrics-trigger" class="side-icon trigger-button">Metrics</label>
						</div>
						<div id="states-trigger-wrapper">
							<label id="states-trigger" class="side-icon trigger-button">States</label>
						</div>
						<!-- Left side of the pagination header. -->
						<div class="pagination-header-left">
						
							<a id="toggle-sidebar" data-toggle="tooltip" title="Click to collapse menus"> 
								<i class="fa fa-caret-left fa-2x"></i>
							</a>
						</div>

						<ul id="viz-tabs">					
							<li class="graph-tab active" id="table-tab" >
								<a href="#table" data-toggle="tab" title="Table View" onclick="as.visualizationDeployer(as.visualizations.TABLE);">
									<i></i>
								</a>
							</li>
							<li class="graph-tab" id="heatmap-tab">
								<a href="#heatmaptab" data-toggle="tab" title="Heap Map" onclick="as.visualizationDeployer(as.visualizations.HEATMAP);">
									<i></i>
								</a>
							</li>
							<li class=" graph-tab " id="line-tab">
								<a href="#line" data-toggle="tab" title="Line Chart" onclick="as.visualizationDeployer(as.visualizations.LINE);">
									<i></i>
								</a>
							</li>
							<li class="graph-tab" id="bar-tab">
								<a href="#bar" data-toggle="tab" title="Bar Graph" onclick="as.visualizationDeployer(as.visualizations.BAR);"> 
									<i></i>
								</a>
							</li>
							
						</ul>
						
					</div>
					
					<div id="viewWrapper">
						<div id="vizView" style="display: none;">
	
							<!--  Start Back/Forward Buttons -->
							<div id="metricCycleButtons" style="display:none;">
								<table>
									<tr>
										<td>
											<button class="backButton" disabled="disabled" style="display:none;" data-toggle="tooltip" title="Display the previous metric." >
												<i class="fa fa-chevron-left"></i>
											</button>
										</td>
										<td>
											<div id="MultipleMetricTitle" >
												Choose a metric from the left menu
											</div>
										</td>
										<td>
											<button  class="nextButton" disabled="disabled" style="display:none;" data-toggle="tooltip" title="Display the next metric.">
												<i class="fa fa-chevron-right"></i>
											</button>
										</td>	
									</tr>
								</table>
							</div>
							<!--  End Back/Forward Buttons -->	
							
							<div class="tab-content">
								<!-- Line Graph -->
								<div class="tab-pane fade" id="line">
									<div class="box-content">
										<div id="mbody" style="margin-right: 5px;">
											<svg style="height: 90%;"></svg>
										</div>
										<div class="download" style="position: absolute; bottom: 0px; left: 50px; padding-bottom: 20px;">
											<button title="Save the visualization as a PNG" class="btn btn-danger" type="button" onclick="as.savePNG();">
												<i class="fa fa-file-image-o" style="color: white !important;"></i> 
											</button>
											<span>Export as PNG</span>
										</div> 
									</div>
								</div>
								
								<!-- Table -->
								<div class="tab-pane active" id="table">
									<div class="box-content">
										<table>
											<tr>
												<td id="optionalTableTitle" data-toggle="popover"></td>
												<td id="timelinetable"></td>
											</tr>
											<tr>
												<td id="mbodyMultipleQuery"></td>
											</tr>
										</table>
										
										 <div class="download" style="padding-bottom: 20px;">
											<button title="Save selected data as an Excel file." class="btn btn-danger" type="button" onclick="as.exportExcelData();">
												<i class="fa fa-file-excel-o" style="color: white !important;"></i> 
											</button>
											<span>Export as Excel Spreadsheet</span>
										</div> 
									</div>
								</div>
								
								<!-- Bar Chart -->
								<div class="tab-pane fade" id="bar">
									<div class="box-content">
										<div id="mbodyBar"></div>
										<div class="download" style="position: absolute; bottom: 0px; left: 50px; padding-bottom: 20px;">
											<button title="Save the visualization as a PNG." class="btn btn-danger" type="button" onclick="as.savePNG();">
												<i class="fa fa-file-image-o" style="color: white !important;"></i> 
											</button>
											<span>Export as PNG</span>
										</div>
									</div>
								</div>
								
								<!-- Heatmap -->		
								<div class="tab-pane fade" id="heatmaptab">
									<div class="box-content">			
										<div id="heatmap-wrapper">
											<div id="heatmap-content-wrapper">
												<div id="heatmap-timeline"></div>
												<div id="heatmap-inner-wrapper">
												
													<div id="heatmap-info" class="heatmap-infobox">
														<div class="heatmap-infobox-inner" id="heatmap-details">
															<div class="heatmap-infobox-title">Details</div>
															<div class="heatmap-infobox-content" style="overflow: hidden;">
																<div class="heatmap-detailTitle" style="padding-left: 0px; padding-top: 0px;">Top Ranked</div> 
																<span id="heatmap-generalinfo-first" class="heatmap-detailVal"></span><br/>
																
																<div class="heatmap-detailTitle" style="padding-left: 0px; padding-top: 0px;">Bottom Ranked</div> 
																<span id="heatmap-generalinfo-last" class="heatmap-detailVal"></span><br/>
																
																<div class="heatmap-detailTitle" style="padding-left: 0px; padding-top: 0px;">MA Rank</div>
																<span id="heatmap-generalinfo-ma" class="heatmap-detailVal"></span>
															</div>
														</div>
														<div class="heatmap-infobox-inner" id="heatmap-legend">
															<div class="heatmap-infobox-title">Legend</div>
															<div id="heatmap-legend-legend" class="heatmap-infobox-content">
															
															</div>
														</div>
													</div>
													
													<div id="heatmap-actual"></div>
													
													
													<div id="heatmap-tooltip" style="display: none;">
														<span id="heatmap-specificDetails-name"></span>
														
														<div class="heatmap-detailTitle">Rank</div>
														<span id="heatmap-specificDetails-rank" class="heatmap-detailVal"></span><br/>
														<span id="heatmap-value-block">
														<div class="heatmap-detailTitle">Value</div>
														<span id="heatmap-specificDetails-value" class="heatmap-detailVal"></span><br/>
														</span>
														<div id="heatmap-specificDetails-peer" style="font-style:italic; padding-left: 10px; padding-top: 5px; color: #7b0020;">Peer State</div>
													</div>
													
													
												</div>
											</div>
											<div id="heatmap-error" style="display:none;">
												No data available for the selected metric.
											</div>
										</div>
										<div class = "download-wrapper">
										<div class="download" style="position: absolute; bottom: 0px; left: 50px; padding-bottom: 20px;">
														<button title="Save the visualization as a PDF." class="btn btn-danger" type="button" onclick="as.printPDF();">
														<i class="fa fa-file-image-o" style="color: white !important;"></i> 
														</button>
														<span>Export as PDF</span>
										</div>
										</div>
									</div>
									
								</div>
							</div>
						</div>
						
						<!-- Error Reporting -->
						<div id="errorView" style="display: none;">				
							<div id="errorMsgWrapper">
								<i class="fa fa-exclamation-triangle fa-2x"></i>
								<span id="errorMsg"></span>
							</div>
						</div>
						<!-- End Error Reporting -->	
						
						<div id="startupMsg">
							<div id="startupMsgWrapper">
								<div id="startupMsgAct">
								<div id="noJSError" style="color: #680017; font-weight: bolder;">
									WARNING: You must have JavaScript enabled to use this page.
									<br/>
								</div>
								
								<b>
								Welcome to MATTERS Data Explorer!</b><br/><br/>
								
								To start, please select metrics and states from the menu to the left.<br/>
					 			<div>
					 			Use the buttons in the red bar above to view your selection in different ways.
					 			</div>				 			
								</div>
							</div>
						</div>
					</div>
				</div>	
				
				<div id="printCanvasWrapper" style="display:none;">
					<canvas id="printCanvas">
					</canvas>
					
					<img id="printCanvasLogo" src="./css/img/MATTERS_Logo.jpg"></img>
				</div>
				
		</div>
		<!-- end: Content -->

	<footer id="colophon" class="site-footer" role="contentinfo">
		<div class="site-info container">
			<p class="copy">
				&copy; 2015. Worcester Polytechnic Institute. All Rights Reserved.<br />
				Sponsored by Mass High Technology Council
				
			</p>
		</div>
		<!-- .site-info -->
	</footer>
	<!-- #colophon -->
		<script src="js/load.js"></script>
		<script src="js/jquery.history.js" type="text/javascript"></script>
	</body>
	
	<!-- Initialize popovers -->
	<script></script>
</html>

