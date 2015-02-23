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
		
		<link href="css/style.css" id="base-style" rel="stylesheet">
		<link href="css/style-responsive.css" id="base-style-responsive" rel="stylesheet">
		
		
		
		<!-- Library JS -->
		<script src="js/d3.v3.min.js"></script>
		<script src="js/nv.d3.min.js"></script>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
		
		<script src="js/raphael.js"></script> <!-- Dependency for raphael.js -->
		<script src="js/jquery.usmap.js"></script>
		
		<script src="js/bootstrap.min.js"></script>
		<script src="js/dataTables.js"></script>
		
		<script src="js/modernizr.js"></script>
		
		<!-- Custom JS -->
		<script src="js/dataquery.js"></script>
		<script src="js/state.js"></script>
		<script src="js/metric.js"></script>
		<script src="js/chart.js"></script>
		<script src="js/appstate.js"></script>
		
		<!--[if lt IE 9]
		<script type="text/javascript">
			var isLtIE9 = true;
		</script>
		<![endif]-->
		
		<title>MATTERS</title>
	
	</head>

	<body>
	
	<jsp:include page="unifiedHeader.jsp"/>

	<div id="preContentBar"></div>

	<div id="globalErrorDiv" style="display:none;">
		<div id="globalErrorMsgWrapper">
			<i class="fa fa-exclamation-triangle fa-2x"></i>
			<span id="globalErrorMsg">
			Sorry! Your browser is not currently supported by MATTERS. 
			
			<br/><br/>
			
			Please upgrade your browser or switch to a supported browser, 
			such as <a href="http://www.google.com/chrome/">Chrome</a> 
			or <a href="https://www.mozilla.org/en-US/firefox/new/">Firefox</a>.
			</span>
		</div>
	</div>

	<div class="container-fluid-full" style="z-index: 1;" id="mainContentDiv">
				<!-- start: left sidebar -->
				<div id="sidebar-left" class="sidebar open">
					<div class="column" id="metricSelectionCol">
						<div class="title">
							<span>Select Metrics</span>
						</div>
						<div class="sidebar-content accordion-menu" id="metricListWrapper">
							<ul>
								<li>
									<a class="metricHeader" href="#">
										National Ranking 
									</a>
									<ul id="nationalProfileList" class="metricList">
										<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all" id="select">
											<a style="text-align:right;">Select All</a>
										</li>
										<c:forEach items="${jv_stats_national}" var="stat2">
											<li>
												<a class="metricOption" id="${stat2.metric.id}"> 
													<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
													${stat2.metric.name} 
												</a>
											</li>
										</c:forEach>
									</ul>
								</li>
								<li>
									<a class="metricHeader" href="#">
										Talent Metrics 
									</a>
									<ul id="talentProfileList" class="metricList">
										<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all" id="select">
											<a style="text-align:right;">Select All</a>
										</li>
										<c:forEach items="${jv_stats_talent}" var="stat3">
											<li>
												<a class="metricOption" id="${stat3.metric.id}"> 
													<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
													${stat3.metric.name}
												</a>
											</li>
										</c:forEach>
									</ul>
								</li>
								<li>
									<a class="metricHeader" href="#">
										Cost Metrics
									</a>
									<ul id= "costProfileList" class="metricList">
										<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all" id="select">
											<a style="text-align:right;">Select All</a>
										</li>
										<c:forEach items="${jv_stats_cost}" var="stat4">
											<li>
												<a class="metricOption" id="${stat4.metric.id}"> 
													<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
													${stat4.metric.name}
												</a>
											</li>
										</c:forEach>
									</ul>
								</li>
								<li>
									<a class="metricHeader" href="#">
										Economy Metrics
									</a>
									<ul id="economyProfileList" class="metricList">
										<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all" id="select">
											<a style="text-align:right;">Select All</a>
										</li>
										<c:forEach items="${jv_stats_economy}" var="stat5">
											<li>
												<a class="metricOption" id="${stat5.metric.id}"> 
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
									<a class="selectPeerStates" id="${row.id}">
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
						<!-- Left side of the pagination header. -->
						<div class="pagination-header-left">
						
							<a href="#" id="toggle-sidebar"> 
								<i class="fa fa-caret-left fa-2x"></i>
							</a>
						</div>
					
						<!--  Right side of the pagination header. -->
						<ul id="viz-tabs">					
							<li class="graph-tab active" id="table-tab" >
								<a href="#table" data-toggle="tab" title="Explore the metrics/states you've selected in table format." onclick="as.visualizationDeployer(as.visualizations.TABLE);">
									<i></i>
								</a>
							</li>
							<li class=" graph-tab " id="line-tab">
								<a href="#line" data-toggle="tab" title="Explore the metrics/states you've selected in a line chart." onclick="as.visualizationDeployer(as.visualizations.LINE);">
									<i></i>
								</a>
							</li>
							<li class="graph-tab" id="bar-tab">
								<a href="#bar" data-toggle="tab" title="Explore the metrics/states you've selected in a bar chart." onclick="as.visualizationDeployer(as.visualizations.BAR);"> 
									<i></i>
								</a>
							</li>
							<li class="graph-tab" id="heatmap-tab">
								<a href="#heatmaptab" data-toggle="tab" title="Explore a heatmap of the United States to see how each state performs with the metrics you've selected." onclick="as.visualizationDeployer(as.visualizations.HEATMAP);">
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
										<div id="mbody" style="margin-right: 5px; margin-top: 20px;">
											<svg style="height: 90%;"></svg>
										</div>
									</div>
								</div>
								
								<!-- Table -->
								<div class="tab-pane active" id="table">
									<div class="box-content">
										<table>
											<tr>
												<td id="optionalTableTitle"></td>
												<td id="timelinetable"></td>
											</tr>
											<tr>
												<td id="mbodyMultipleQuery"></td>
											</tr>
										</table>
										
										<!-- <div>
											<button id="excelDownloadBtn" style="display:none;" class="btn btn-success" type="button" onclick="as.visualizationDeployer(as.visualizations.EXCEL);">
												<i class="fa fa-file-excel-o" style="color: white !important;"></i> 
												Download table as Excel spreadsheet
											</button>
										</div>-->
									</div>
								</div>
								
								<!-- Bar Chart -->
								<div class="tab-pane fade" id="bar">
									<div class="box-content">
										<div id="mbodyBar" style="margin-top:20px;"></div>
									</div>
								</div>
								
								<!-- Heatmap -->		
								<div class="tab-pane fade" id="heatmaptab">
									<div class="box-content">			
										<div id="heatmap-wrapper">
											<div id="heatmap-timeline"></div>
											<div id="heatmap-inner-wrapper">
											
												<div id="heatmap-details" class="heatmap-infobox">
													<div class="heatmap-infobox-inner">
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
												</div>
												
												<div id="heatmap-actual"></div>
												
												<div id="heatmap-controls" class="heatmap-infobox">
													<div class="heatmap-infobox-inner">
														<div class="heatmap-infobox-title">Legend</div>
														<div id="heatmap-legend-legend" class="heatmap-infobox-content">
														
														</div>
													</div>
												</div>
												<div id="heatmap-tooltip" style="display: none;">
													<span id="heatmap-specificDetails-name"></span>
													
													<div class="heatmap-detailTitle">Rank</div>
													<span id="heatmap-specificDetails-rank" class="heatmap-detailVal"></span><br/>
													
													<div class="heatmap-detailTitle">Value</div>
													<span id="heatmap-specificDetails-value" class="heatmap-detailVal"></span><br/>
													
													<div id="heatmap-specificDetails-peer" style="font-style:italic; padding-left: 10px; padding-top: 5px; color: #7b0020;">Peer State</div>
												</div>
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
							<div id="startupMsgAct"><b>
							Welcome to MATTERS Data Explorer!</b><br/><br/><br/>
							
							To start, please select metrics and states from the menu to the left.<br/><br/><br/>
				 			<div>
				 			Use the buttons in the red bar above to view your selection in different ways.
				 			</div>
							</div>
						</div>
					</div>
				</div>		
		</div>
		<!-- end: Content -->

	<footer id="colophon" class="site-footer" role="contentinfo">
		<div class="site-info container">
			<p class="copy">
				2015. Worcester Polytechnic Institute. All Rights Reserved.<br />
				Sponsored by Mass High Technology Council
			</p>

		</div>
		<!-- .site-info -->
	</footer>
	<!-- #colophon -->

	<%
		/*<div class="modal fade in" id="aboutModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		 <div class="modal-dialog">
		 <div class="modal-content" >
		 <div class="modal-header">
		 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		 <h4 class="modal-title" id="myModalLabel">About The Massachusetts High Technology Council</h4>
		 </div>
		 <div class="modal-body" id="aboutBody">
		 <p>
		 As part of its mission to make Massachusetts the world&apos;s most attractive place in which to live and work, and
		 in which to create, and grow high technology business, the <strong>Massachusetts High Technology Council</strong> has developed the
		 <span class="bluetext">Massachusetts&apos; Technology, Talent and Economy Reporting Systems</span> or <span class="bluetext">MATTERS</span>. <span class="bluetext">MATTERS</span> is a tool designed to
		 help measure and evaluate Massachusetts&apos; current competitive position among leading technology states while
		 providing policy makers with the information critical to developing public policy that attracts and retains
		 business to the state. <br /> <br /> <span class="bluetext">MATTERS</span> is an online system that consolidates a collection of independent
		 national rankings along with a set of key cost, economic and talent metrics into a single source for use by all
		 parties interested in building a successful future for Massachusetts&apos; technology-based business. Both private
		 and public-sector decision makers will have the key information necessary to evaluate and understand
		 Massachusetts&apos; current business position as it compares to peer states and international communities who are
		 working aggressively to attract the same talent, apital and jobs that characterize our Bay State technology
		 economy. Armed with <span class="bluetext">MATTERS</span>&apos; data, business, education and public-policy leaders will chart
		 Massachusetts&apos; future collaboratively by aligning what needs to be improved or protected in order to create a
		 prosperous, competitive business environment fundamental to building long-term economic stability and job growth
		 within the state.
		 </p>
		 <p class="text-muted centered"><br />&copy;2014 Worcester Polytechnic Institute. All rights reserved.<br /> Sponsored by Mass High Technology
		 Council
		 </p>
		 </div>
		 <div class="modal-footer">
		 <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		 </div>
		 </div>
		 </div>
		 </div>
		 <div class="modal-backdrop fade in"></div>
		 <div class="modal-backdrop fade in"></div>
		 */
	%>
		<script src="js/load.js"></script>	
	</body>
</html>

