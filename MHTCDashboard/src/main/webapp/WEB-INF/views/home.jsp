<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta charset="utf-8" />
<link href="css/nv.d3.css" rel="stylesheet">
<!--  <script src="http://d3js.org/d3.v3.min.js"></script> -->
<script src="js/d3.v3.min.js"></script>

<script src="js/nv.d3.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<%
	/*<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css"> */
%>
<script src="js/raphael.js"></script>
<script src="js/color.jquery.js"></script>
<script src="js/dataquery.js"></script>
<script src="js/jquery.usmap.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script src="js/state.js"></script>
<script src="js/metric.js"></script>
<script src="js/chart.js"></script>
<script src="js/appstate.js"></script>

<%
	/*<script src="js/jquery.multiple.select.js"></script>
	<link rel="stylesheet" href="css/sumoselect.css"></link> */
%>
<link rel="stylesheet" href="css/local_style.css">

<title>MATTERS: Massachusetts' Technology, Talent and Economy
	Reporting System</title>

<!--  NEW DESIGN -->

<link id="bootstrap-style" href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
<link id="base-style" href="css/style.css" rel="stylesheet">
<link id="base-style-responsive" href="css/style-responsive.css"
	rel="stylesheet">
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext'
	rel='stylesheet' type='text/css'>


<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-migrate-1.0.0.min.js"></script>

<script src="js/jquery-ui-1.10.0.custom.min.js"></script>

<script src="js/jquery.ui.touch-punch.js"></script>

<script src="js/modernizr.js"></script>

<script src="js/bootstrap.min.js"></script>

<script src="js/jquery.cookie.js"></script>

<script src='js/fullcalendar.min.js'></script>

<script src='js/jquery.dataTables.min.js'></script>

<script src="js/excanvas.js"></script>
<%
	/* <script src="js/jquery.flot.js"></script> */
%>
<script src="js/jquery.flot.pie.js"></script>
<script src="js/jquery.flot.stack.js"></script>
<script src="js/jquery.flot.resize.min.js"></script>

<script src="js/jquery.chosen.min.js"></script>

<script src="js/jquery.uniform.min.js"></script>

<script src="js/jquery.cleditor.min.js"></script>

<script src="js/jquery.noty.js"></script>

<script src="js/jquery.elfinder.min.js"></script>

<script src="js/jquery.raty.min.js"></script>

<script src="js/jquery.iphone.toggle.js"></script>

<script src="js/jquery.uploadify-3.1.min.js"></script>

<script src="js/jquery.gritter.min.js"></script>

<script src="js/jquery.imagesloaded.js"></script>

<script src="js/jquery.masonry.min.js"></script>

<script src="js/jquery.knob.modified.js"></script>

<script src="js/jquery.sparkline.min.js"></script>

<script src="js/counter.js"></script>

<script src="js/retina.js"></script>

<script src="js/custom.js"></script>





<!--  NEW DESIGN

 -->

<link rel="stylesheet" href="css/multiple-select.css" />
<script src="js/jquery.multiple.select.js"></script>

</head>
<body onLoad="loadFunction()">

	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</a>
				<%
					/* <a class="brand" href="index.html"><span>Metro</span></a> */
				%>



			</div>
		</div>
	</div>

	<div class="container-fluid-full">
		<div class="row-fluid">

			<!-- start: Main Menu -->
			<div id="sidebar-left" class="span2">
				<div class="nav-collapse sidebar-nav">

					<ul class="nav nav-tabs nav-stacked main-menu">
						<li><a class="dropmenu" href="blank0.html"><span
								class="hidden-tablet"> State Profile</span> </a>
							<ul>
								<c:forEach items="${jv_stats_profile}" var="stat1">
									<li><a class="submenu"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat1.metric.id}" checked>

												${stat1.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li><a class="dropmenu" href="blank1.html"><span
								class="hidden-tablet"> National Ranking</span> </a>
							<ul>
								<c:forEach items="${jv_stats_national}" var="stat2">
									<li><a class="submenu"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat2.metric.id}">
												${stat2.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li><a class="dropmenu" href="blank2.html"><span
								class="hidden-tablet"> Talent Ranking</span> </a>
							<ul>
								<c:forEach items="${jv_stats_talent}" var="stat3">
									<li><a class="submenu"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat3.metric.id}">
												${stat3.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li><a class="dropmenu" href="blank3.html"><span
								class="hidden-tablet"> Cost Ranking</span> </a>
							<ul>
								<c:forEach items="${jv_stats_cost}" var="stat4">
									<li><a class="submenu"><span class="hidden-tablet">
												<input type="checkbox" id="check${stat4.metric.id}">
												${stat4.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li><a class="dropmenu" href="blank4.html"><span
								class="hidden-tablet">Economy Ranking </span> </a>
							<ul>
								<c:forEach items="${jv_stats_economy}" var="stat5">
									<li><a class="submenu"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat5.metric.id}">
												${stat5.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>

					</ul>
				</div>
			</div>

			<!-- start: Content -->
			<div id="content" class="span10">


				<%
					/* <ul class="breadcrumb">
								<li>
									<i class="icon-home"></i>
									<a href="index.html">Home</a> 
									<i class="icon-angle-right"></i>
								</li>
								<li><a href="#">Dashboard</a></li>
							</ul> */
				%>
				<div class="row-fluid" >
					<table >
						<tr>
							<td><div id="MultipleMetricTitle" style="height: 60px;">
									<strong>Choose a metric from the left menu</strong>
								</div></td>

						</tr>

					</table>
				</div>
				<div class="row-fluid">
					<div id ="stateSelection" >
					Select states to display data <select id="ms" multiple="multiple">
						<c:forEach items="${jv_all_states}" var="stat">
							<c:forEach items="${stat.row}" var="row">
								<option value="${row.id}">${row.name}</option>


							</c:forEach>
						</c:forEach>
					</select>
					</div>
				</div>
				<br>
				<% /*<div class="row-fluid">
				
					<div class="pagination pagination-right">
						<ul class="nav nav-tabs">
							<li id="graphTab"><a href="#profile" data-toggle="tab"
								onclick="as.graphDeployer(0,'line')">Lines</a></li>
							<li id="graphTab"><a href="#national" data-toggle="tab"
								onclick="as.showMultipleMetricsStatesYears(-1)">Table</a></li>
							<li id="graphTab"><a href="#talent" data-toggle="tab"
								onclick="as.graphDeployer(0,'bar')">Bar</a></li>
							<li id="graphTab"><a href="#heatmaptab" data-toggle="tab"
								onclick="as.showHeatMapGraphReloaded(0,'#mbodyHeatMap',-1)">Heatmap</a></li>

						</ul>
					</div>
				</div>*/%>
				<div class="row-fluid" id="talenttab" style="display:none;"><a>heatmapcolor</a></div>
				
				

				<div class="row-fluid">
					<div class="tab-content">

						<div class="tab-pane active" id="profile">
							<div class="box">
								<div class="box-header">
									<div class="box-icon">
										<a href="#profile" data-toggle="tab" onclick="as.graphDeployer(0,'line')">Lines</a>
										<a href="#national" data-toggle="tab" onclick="as.showMultipleMetricsStatesYears(-1)">Table</a>
										<a href="#talent" data-toggle="tab" onclick="as.graphDeployer(0,'bar')">Bar</a>
										<a href="#heatmaptab" data-toggle="tab" onclick="as.showHeatMapGraphReloaded(0,'#mbodyHeatMap',-1)">Heatmap</a>
									</div>
								</div>
							</div>
							<div class="box-content">
								<h4 class="modal-title" id="graphTitle">State Science and
									Technology Graph</h4>
								<h4 class="small" id="graphStates">Selected States:
									Massachusetts, California, Texas</h4>
								<div id="mbody">
									<svg style="height: 100%;"></svg>
								</div>
							</div>
						</div>

						<div class="tab-pane fade" id="national">
							<div class="box">
								<div class="box-header">
									<div class="box-icon">
										<a href="#profile" data-toggle="tab" onclick="as.graphDeployer(0,'line')">Lines</a>
										<a href="#national" data-toggle="tab" onclick="as.showMultipleMetricsStatesYears(-1)">Table</a>
										<a href="#talent" data-toggle="tab" onclick="as.graphDeployer(0,'bar')">Bar</a>
										<a href="#heatmaptab" data-toggle="tab" onclick="as.showHeatMapGraphReloaded(0,'#mbodyHeatMap',-1)">Heatmap</a>
									</div>
								</div>
							</div>							
							<div class="box-content">
								<%
									/* <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> */
								%>
								<table>
									<tr>

										<td align="left" nowrap="true"><div id="timelinetable"></div></td>
									</tr>
								</table>
								<select id="yearsMultipleQuery"
									class="btn btn-primary btn-right"></select>
								<h4 class="modal-title" id="graphTitleMultipleQuery">State
									Science and Technology Graph</h4>
								<h6 class="modal-title">NV: No Value for the year selected</h6>

							</div>
							<div class="modal-body" id="mbodyMultipleQuery">
								<svg style="height: 400%;"></svg>
							</div>
						</div>
						<div class="tab-pane fade" id="talent">
							<div class="box">
								<div class="box-header">
									<div class="box-icon">
										<a href="#profile" data-toggle="tab" onclick="as.graphDeployer(0,'line')">Lines</a>
										<a href="#national" data-toggle="tab" onclick="as.showMultipleMetricsStatesYears(-1)">Table</a>
										<a href="#talent" data-toggle="tab" onclick="as.graphDeployer(0,'bar')">Bar</a>
										<a href="#heatmaptab" data-toggle="tab" onclick="as.showHeatMapGraphReloaded(0,'#mbodyHeatMap',-1)">Heatmap</a>
									</div>
								</div>
							</div>
							<div class="box-content">
								<h4 class="modal-title" id="graphTitleBar">State Science
									and Technology Graph</h4>
								<h4 class="small" id="graphStatesBar">Selected States:
									Massachusetts, California, Texas</h4>
								<div id="mbodyBar">
									<svg style="height: 400%;"></svg>
								</div>
							</div>
						</div>
						<div class="tab-pane fade" id="heatmaptab">
							<div class="box">
								<div class="box-header">
									<div class="box-icon">
										<a href="#profile" data-toggle="tab" onclick="as.graphDeployer(0,'line')">Lines</a>
										<a href="#national" data-toggle="tab" onclick="as.showMultipleMetricsStatesYears(-1)">Table</a>
										<a href="#talent" data-toggle="tab" onclick="as.graphDeployer(0,'bar')">Bar</a>
										<a href="#heatmaptab" data-toggle="tab" onclick="as.showHeatMapGraphReloaded(0,'#mbodyHeatMap',-1)">Heatmap</a>
									</div>
								</div>
							</div>
								<div class="box-content">
									<table style="width: 1400px; height: 650px;">
										<tr>
											<td align="left" nowrap="true">
												<table>
													<tr>

														<td align="left" nowrap="true"><h4
																class="modal-title">Timeline:&nbsp;&nbsp;</h4></td>

														<td align="left" nowrap="true"><div id="timeline"></div></td>
														<td nowrap="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
														<td valign="top" align="right">
															<button type="button" id="playbuttonanimation"
																class="btn btn-default">Play</button>
														</td>

														<td valign="top" align="right">
															<button type="button" id="stopbuttonanimation"
																class="btn btn-default" disabled="true">Stop</button>
														</td>
													</tr>

												</table>
											</td>
										</tr>


										<tr>
											<td align="center">
												<table style="width: 100%; height: 650px; overflow: auto;">
													<tr>
														<td align="center"><h2>
																<div id="stateTitle">Massachusetts</div>
															</h2></td>
													</tr>
													<tr>
														<td valign="top"><div id="mbodyHeatMap"
																align="center" style="height: 650px;"></div></td>
														<td valign="top" align="left">
															<table>

																<tr>
																	<td><div id="verticalheatmapmeter"></div></td>
																</tr>
															</table>
														</td>

													</tr>
												</table>
											</td>
										</tr>
									</table>
								</div>
							</div>


						</div>
					</div>
				</div>








			</div>
			<!--/.fluid-container-->

			<!-- end: Content -->
		</div>
		<!--/#content.span10-->
	</div>
	<!--/fluid-row-->

	<div class="modal hide fade" id="myModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>Settings</h3>
		</div>
		<div class="modal-body">
			<p>Here settings can be configured...</p>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">Close</a> <a href="#"
				class="btn btn-primary">Save changes</a>
		</div>
	</div>

	<div class="clearfix"></div>

	<footer>

		<p>
			<span style="text-align: left; float: left">&copy; 2013 <a
				href="http://jiji262.github.io/Bootstrap_Metro_Dashboard/"
				alt="Bootstrap_Metro_Dashboard">Bootstrap Metro Dashboard</a></span>

		</p>

	</footer>
	<script src="js/load.js"></script>



</body>
</html>
