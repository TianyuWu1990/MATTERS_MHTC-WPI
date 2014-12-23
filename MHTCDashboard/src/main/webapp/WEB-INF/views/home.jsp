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
<link href="css/dataTables.css" rel="stylesheet">
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
<script type="text/javascript" charset="utf8" src="js/dataTables.js"></script>




<!--  NEW DESIGN

 -->

<link rel="stylesheet" href="css/multiple-select.css" />
<script src="js/jquery.multiple.select.js"></script>

</head>
<body onLoad="loadFunction()">
	<div class="navbar">
		<div class="navbar-inner">
			<nav>
				<div class="container-fluid">
					<a class="btn btn-navbar" data-toggle="collapse"
						data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
					</a> 
					<img src="css/img/MHTC_Logo.jpg" alt="Mass High Technology Council" >

					<ul class="nav pull-right" id="main-menu">
						<li id="explore"><a href="#">Explore</a></li>
						<li id="about"><a>About</a></li>
						<li class="dropdown"><a class="dropdown-toggle" id="dLabel"
							data-toggle="dropdown" data-target="#" href=""> Login <span
								class="caret"></span>
						</a>
							<div class="dropdown-menu" role="menu" aria-labelledby="dLabel"
								style="padding: 0px;">

								<form class="navbar-form navbar-left"
									action="<c:url value='j_spring_security_check' />"
									method="POST" role="search" style="margin: 0px;">
									<input type="text" class="form-control" placeholder="username"
										name="username" id="username">
									<input type="password"
										class="form-control" placeholder="password" name="password"
										id="password">

									<button id="login-button" type="submit" value="submit" class="btn btn-default" style="margin: 0px; width: 100%; height: 40px; background: #333;">Login</button>

									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" style="display: none;" />
								</form>
							</div></li>
					</ul>

 
					<!-- /.navbar-collapse -->
				</div>
				<!-- /.container-fluid -->
			</nav>


		</div>
	</div>

	<div class="container-fluid-full">
		<div class="row-fluid">

			<!-- start: Main Menu -->
			<div id="sidebar-left" class="span2">
				<div class="nav-collapse sidebar-nav">

					<ul class="nav nav-tabs nav-stacked main-menu">
						<li ><a class="dropmenu" href="blank0.html"><span
								class="hidden-tablet"> State Profile</span> </a>
							<ul id="stateMetric" class="listcontent">
								<li class="selectUnselectAll" ><input type="checkbox" checked style="display:none"><a style="text-align:right;" ">Unselect All</a></li>
								<c:forEach items="${jv_stats_profile}" var="stat1">
									<li><a class="submenu" id="${stat1.metric.id}"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat1.metric.id}" checked> 
												${stat1.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li ><a class="dropmenu" href="blank1.html"><span
								class="hidden-tablet"> National Ranking</span> </a>
							<ul id="nationalProfileList">
							<li  class="selectUnselectAll"><input type="checkbox"  style="display:none"><a style="text-align:right;">Select All</a></li>
								<c:forEach items="${jv_stats_national}" var="stat2">
									<li><a class="submenu"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat2.metric.id}" disabled="disabled"
												> ${stat2.metric.name} 
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li ><a class="dropmenu" href="blank2.html"><span
								class="hidden-tablet"> Talent Metrics</span> </a>
							<ul id="talentProfileList" >
							<li  class="selectUnselectAll"><input type="checkbox"  style="display:none"><a style="text-align:right;">Select All</a></li>
								<c:forEach items="${jv_stats_talent}" var="stat3">
									<li><a class="submenu"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat3.metric.id}" disabled="disabled"
												> ${stat3.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li ><a class="dropmenu" href="blank3.html"><span
								class="hidden-tablet"> Cost Metrics</span> </a>
							<ul id= "costProfileList">
							<li  class="selectUnselectAll"><input type="checkbox"  style="display:none"><a style="text-align:right;">Select All</a></li>
								<c:forEach items="${jv_stats_cost}" var="stat4">
									<li><a class="submenu"><span class="hidden-tablet">
												<input type="checkbox" id="check${stat4.metric.id}" disabled="disabled"
												> ${stat4.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li ><a class="dropmenu" href="blank4.html"><span
								class="hidden-tablet">Economy Metrics </span> </a>
							<ul id="economyProfileList">
							<li  class="selectUnselectAll"><input type="checkbox"  style="display:none"><a style="text-align:right;">Select All</a></li>
								<c:forEach items="${jv_stats_economy}" var="stat5">
									<li><a class="submenu"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat5.metric.id}" disabled="disabled"
												> ${stat5.metric.name}<% /*style="display: none;"*/%>
										</span></a></li>
								</c:forEach>
							</ul></li>

					</ul>
				</div>
			</div>

			<!-- start: Content -->
			<div id="content" class="span10">

				<div class="row-fluid" >
				<table width="100%"><tr><td>
					<table >
						<tr style="padding: 0px;"><td nowrap="true">
							<button class="backButton"  id="" disabled="disabled" style="display:none;"><<</button>&nbsp;
							<button  class="nextButton" id="" disabled="disabled" style="display:none;">>></button>&nbsp;</td>
							<td nowrap="true"><div id="MultipleMetricTitle" ><i><strong>Choose a metric from the left menu</strong></i></div></td>
						</tr>

					</table></td>
					<td width="50%" style="padding-left:40px;"><div  id="stateSelection" >
							 <p style="font-size:14px;">
							Select states to display data &nbsp;<select id="ms" multiple="multiple" style="min-width: 100px;">
							<c:forEach items="${jv_all_states}" var="stat">
								<c:forEach items="${stat.row}" var="row">
									<option value="${row.id}">${row.name}</option>
								</c:forEach>
							</c:forEach>
						</select></p>
					</div></td>
					</tr></table>
				</div>
				<br>
				<div class="row-fluid" id="graph-area" >
				<div class="pagination pagination-right" >
										<ul class="nav nav-tabs">										
											<li class="graph-tab active" id="table-tab"><a href="#table" data-toggle="tab" onclick="as.showMultipleMetricsStatesYears(-1)">Table</a></li>
											<li class=" graph-tab " id="line-tab"><a href="#lines" data-toggle="tab" onclick="as.graphDeployer(0,'line')">Lines</a></li>
											<li class="graph-tab" id="bar-tab"><a href="#bar" data-toggle="tab" onclick="as.graphDeployer(0,'bar')">Bar</a></li>
											<li class="graph-tab" id="heatmap-tab"><a href="#heatmaptab" data-toggle="tab" onclick="as.showHeatMapGraphReloaded(0,'#mbodyHeatMap',-1)">Heatmap</a></li>
										</ul>
					</div>
					<%/*<div  id="stateSelection" style="margin-top:5px;">
							Select states to display data <select id="ms" multiple="multiple">
							<c:forEach items="${jv_all_states}" var="stat">
								<c:forEach items="${stat.row}" var="row">
									<option value="${row.id}">${row.name}</option>

<<<<<<< HEAD
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" style="text-align: center">
				<ul class="nav navbar-nav navbar-left">
					<li class="active"><a href="#">Explore</a></li>
					<li><a href="#" data-toggle="modal" data-target="#aboutModal">About</a></li>
				</ul>
				<h1 class="centered title">Massachusetts' Technology, Talent and Economy Reporting System</h1>
				<ul class="nav navbar-nav navbar-right">
					<form class="navbar-form navbar-left" action="<c:url value='admin/login' />" method="POST"
						role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="username" name="username"> <input type="password"
								class="form-control" placeholder="password" name="password">
=======

								</c:forEach>
							</c:forEach>
						</select>
					</div>*/ %>
					<div class="tab-content">

						<div class="tab-pane fade" id="lines">
	
							<div class="box-content">
								<h4 class="modal-title" id="graphTitle"></h4>
								<%/*<h4 class="small" id="graphStates"></h4>*/%>
								<div id="mbody" style="margin-right:5px; margin-top:20px;">
									<svg style="height: 90%;"></svg>
								</div>
							</div>
>>>>>>> origin/dev-webapp-new
						</div>

						<%/*<div class="tab-pane fade" id="national">
						
							<div class="box-content">
								<div id="timelinetable" ></div>
								<%/*<select id="yearsMultipleQuery"
									class="btn btn-primary btn-right"></select>*/ %>
								<%/*<h6 class="modal-title" >NV: No Value for the year selected</h6>
							<div class="modal-body" id="mbodyMultipleQuery" >
								<svg style="height: auto"></svg>
							</div>
							</div>
							
						</div>*/ %>
						<div class="tab-pane active" id="table">
							<div class="box-content">
								<table><tr><td id="timelinetable">
								</td></tr>
								<tr><td><h6 class="modal-title" >NV: No Value for the year selected</h6></td></tr>
								<tr><td class="modal-body" id="mbodyMultipleQuery"> <svg style="height: auto"></svg></td></tr>
								</table>
							</div>
						</div>
						
						<div class="tab-pane fade" id="bar">

							<div class="box-content">
								<h4 class="modal-title" id="graphTitleBar"></h4>
								<%/*<h4 class="small" id="graphStatesBar">Selected States:
									Massachusetts, California, Texas</h4>*/ %>
								<div id="mbodyBar" style="margin-top:20px;">
									<svg style="height: 400%;"></svg>
								</div>
							</div>
						</div>
						<div class="tab-pane fade" id="heatmaptab">
							<div class="box-content" >
								<table style="width: 98%; height: 650px;">
									<tr>
										<td align="left" nowrap="true">
											<table>
												<tr>

													<td  nowrap="true"><h4 class="modal-title">Timeline:&nbsp;&nbsp;</h4></td>

													<td  nowrap="true"><div id="timeline"></div></td>
													<td   style="padding-left:40px">
														<button type="button" id="playbuttonanimation"
															>Play</button>
														<button type="button" id="stopbuttonanimation"
															disabled="true">Stop</button>
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
													<td valign="top"><div id="mbodyHeatMap" text-align="center"
															style=""></div> </td>
													<td valign="top" align="left" style="width:10%; padding-left:10px;"> <div id="verticalheatmapmeter"></div></td>

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
	<!--/fluid-row-->

	<footer>

		<p>
			<span style="text-align: left; float: left">&copy; 2014 <a
				href="" > Worcester Polytechnic Institute. All rights reserved.
					Sponsored by Mass High Technology Council</a></span>

		</p>

	</footer>

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
