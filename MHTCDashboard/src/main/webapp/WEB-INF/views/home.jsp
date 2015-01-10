<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta charset="utf-8" />
<link href="css/nv.d3.css" rel="stylesheet">
<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<link href="css/local_style.css" rel="stylesheet">
<link href="css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="css/dataTables.css" rel="stylesheet">
<link href="css/style.css" id="base-style"  rel="stylesheet">
<link href="css/style-responsive.css" id="base-style-responsive" rel="stylesheet">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/jquery.multiselect.css" />

<script src="js/d3.v3.min.js"></script>
<script src="js/nv.d3.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<script src="js/raphael.js"></script>
<script src="js/dataquery.js"></script>
<script src="js/jquery.usmap.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script src="js/dataTables.js"></script>
<script src="js/state.js"></script>
<script src="js/metric.js"></script>
<script src="js/chart.js"></script>
<script src="js/appstate.js"></script>

<!-- start: JavaScript for Metro Dashboard-->
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery-migrate-1.0.0.min.js"></script>
<script src="js/jquery-ui-1.10.0.custom.min.js"></script>
<script src="js/jquery.ui.touch-punch.js"></script>
<script src="js/modernizr.js"></script>
<script src="js/jquery.cookie.js"></script>
<script src='js/fullcalendar.min.js'></script>
<script src='js/jquery.dataTables.min.js'></script>
<script src="js/excanvas.js"></script>
<script src="js/jquery.flot.js"></script>
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
<!-- end: JavaScript-->

<script src="js/jquery.multiselect.js"></script>

<%
	/*
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
	<script src="js/jquery.multiple.select.js"></script>
	<link rel="stylesheet" href="css/sumoselect.css"></link> 
	<script src="js/jquery.multiple.select.js"></script>
	<script src="js/jquery.flot.js"></script>
	<script src="http://d3js.org/d3.v3.min.js"></script>
	 */
%>

<title>MATTERS: Massachusetts' Technology, Talent and Economy
	Reporting System</title>

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
					<img src="css/img/MHTC_Logo.jpg" alt="Mass High Technology Council" >
					
					<ul class="nav pull-right" id="main-menu">
						<li id="explore"><a href="#">Explore</a></li>
						<li id="about"><a>About</a></li>
						<sec:authorize access="hasRole('ADMIN')">
                            	<li id="admin"><a href="admin">Admin Panel</a></li>
                        </sec:authorize>
                        
                        <sec:authorize access="!isAuthenticated()">
							<li class="dropdown">
								<a class="dropdown-toggle" id="dLabel" data-toggle="dropdown" data-target="#" href="">
									<i class="fa fa-user"></i>&nbsp;Login <span class="caret"></span>
								</a>
								<div class="dropdown-menu" role="menu" aria-labelledby="dLabel" style="padding: 0px;">
									<form method="post" class="signin" action="http://www.alessioatzeni.com/wp-content/tutorials/jquery/Signin-Dropdown-box-twitter-style/index.html#">
					                <fieldset class="textbox">
					            	<label class="username">
					                <span>Username or email</span>
					                <input id="username" name="username" value="" type="text" autocomplete="on">
					                </label>
					                
					                <label class="password">
					                <span>Password</span>
					                <input id="password" name="password" value="" type="password">
					                </label>
					                </fieldset>
					                
					                <fieldset class="remb">
					                <label class="remember">
					                <input type="checkbox" value="1" name="remember_me">
					                <span>Remember me</span>
					                </label>
					                <button class="submit button" type="button">Login</button>
					                </fieldset>
					                <p align="center">
					               		 <a class="forgot" href="">Forgot your password</a>
					                </p>
					                </form>
								</div>
							</li>
						</sec:authorize>
						
                        <sec:authorize access="isAuthenticated()">
							<li id="login"><a href="logout/">Logout</a></li>
						</sec:authorize>
					</ul>
					<!-- /.navbar-collapse -->
				</div>
				<!-- /.container-fluid -->
			

		<div style="background:#7b0020; margin:0px; padding:7px;">
		<p align="center" valign="middle" style="font-size:20px;">Massachusetts' Technology, Talent and Economic Reporting System - MATTERS</p></div>
		</div>
	</div>

	<div class="container-fluid-full">
		<div class="row-fluid">

			<!-- start: Main Menu -->
			<div id="sidebar-left" class="span2">
				<div class="nav-collapse sidebar-nav">

					<ul class="nav nav-tabs nav-stacked main-menu">
						<li class=''><a class="dropmenu" href="blank0.html"><span
								class="hidden-tablet"> State Profile</span> </a>
							<ul id="stateMetric" class="listcontent">
								<li class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all"><input type="checkbox" checked style="display:none"><a style="text-align:right;" ">Deselect All</a></li>
								<c:forEach items="${jv_stats_profile}" var="stat1">
									<li><a class="submenu" id="${stat1.metric.id}"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat1.metric.id}" checked> 
												${stat1.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li class=''><a class="dropmenu" href="blank1.html"><span
								class="hidden-tablet"> National Ranking</span> </a>
							<ul id="nationalProfileList">
							<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all"><input type="checkbox"  style="display:none" data-toggle="tooltip" title="Click to select/unselect all"><a style="text-align:right;">Select All</a></li>
								<c:forEach items="${jv_stats_national}" var="stat2">
									<li><a class="submenu"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat2.metric.id}" disabled="disabled"
												> ${stat2.metric.name} 
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li class=''><a class="dropmenu" href="blank2.html"><span
								class="hidden-tablet"> Talent Metrics</span> </a>
							<ul id="talentProfileList" >
							<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all"><input type="checkbox"  style="display:none"><a style="text-align:right;">Select All</a></li>
								<c:forEach items="${jv_stats_talent}" var="stat3">
									<li><a class="submenu"> <span class="hidden-tablet">
												<input type="checkbox" id="check${stat3.metric.id}" disabled="disabled"
												> ${stat3.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li class=''><a class="dropmenu" href="blank3.html"><span
								class="hidden-tablet"> Cost Metrics</span> </a>
							<ul id= "costProfileList">
							<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all"><input type="checkbox"  style="display:none"><a style="text-align:right;">Select All</a></li>
								<c:forEach items="${jv_stats_cost}" var="stat4">
									<li><a class="submenu"><span class="hidden-tablet">
												<input type="checkbox" id="check${stat4.metric.id}" disabled="disabled"
												> ${stat4.metric.name}
										</span></a></li>
								</c:forEach>
							</ul></li>
						<li class=''><a class="dropmenu" href="blank4.html"><span
								class="hidden-tablet">Economy Metrics </span> </a>
							<ul id="economyProfileList">
							<li  class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all"><input type="checkbox"  style="display:none"><a style="text-align:right;">Select All</a></li>
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
				<table width="100%"><tr><td style="width:60%">
					<table >
						<tr style="padding: 0px;"><td nowrap="true">
							<button class="backButton"  id="" disabled="disabled" style="display:none;" data-toggle="tooltip" title="back" ><<</button>&nbsp;
							<button  class="nextButton" id="" disabled="disabled" style="display:none;" data-toggle="tooltip" title="next">>></button>&nbsp;</td>
							<td ><div id="MultipleMetricTitle" ><i><strong>Choose a metric from the left menu</strong></i></div></td>
						</tr>

					</table></td>
					<td style="padding-left:10px;"><div  id="stateSelection" >
							 <p style="font-size:14px;">
							States&nbsp;<select id="ms" multiple="multiple" size="4" style="min-width: 100px;">
							<option value="60" name="Peer">Peer states</option>
							<option value="61" name="Top 10">Top 10 states</option>
							<option value="62" name = "Bottom">Bottom 10 states</option>
							<optgroup label="All States">
							<c:forEach items="${jv_all_states}" var="stat">
								<c:forEach items="${stat.row}" var="row">
									<c:choose>
								        <c:when test="${row.id == (21)}">
								            <option value="${row.id}" name="${row.abbr}" selected>${row.name}(${row.abbr})</option>
								        </c:when>
								        <c:otherwise>
								        	
								            <option value="${row.id}" name="${row.abbr}">${row.name}(${row.abbr})</option>
								        </c:otherwise>
								    </c:choose>
								</c:forEach>
							</c:forEach></optgroup>
						</select></p>
					</div></td>
					</tr></table>
				</div>
				<br>
				<div class="row-fluid" id="graph-area" >
				<div class="pagination pagination-right" >
					<ul class="nav nav-tabs">										
						<li class="graph-tab active" id="table-tab" >
							<a href="#table" data-toggle="tab" title="Table" onclick="as.showMultipleMetricsStatesYears(-1)" valign="middle">
							<i class="fa fa-table fa-2x"></i></a>
						</li>
						<li class=" graph-tab " id="line-tab">
							<a href="#line" data-toggle="tab" title="Line Chart" onclick="as.graphDeployer(0,'line')">
							<i class="fa fa-line-chart fa-2x"></i></a>
						</li>
						<li class="graph-tab" id="bar-tab">
							<a href="#bar" data-toggle="tab" title="Bar Chart" onclick="as.graphDeployer(0,'bar')"> 
							<i class="fa fa-bar-chart fa-2x"></i></a>
						</li>
						<li class="graph-tab" id="heatmap-tab">
							<a href="#heatmaptab" data-toggle="tab" title="Heatmap" onclick="as.showHeatMapGraphReloaded(0,'#mbodyHeatMap',-1)">
							<i class="fa fa-picture-o fa-2x"></i></a>
						</li>
					</ul>
					</div>
					<%/*<div  id="stateSelection" style="margin-top:5px;">
							Select states to display data <select id="ms" multiple="multiple">
							<c:forEach items="${jv_all_states}" var="stat">
								<c:forEach items="${stat.row}" var="row">
									<option value="${row.id}">${row.name}</option>


								</c:forEach>
							</c:forEach>
						</select>
					</div>*/ %>
					<div class="tab-content">

						<div class="tab-pane fade" id="line">
	
							<div class="box-content">
								<h4 class="modal-title" id="graphTitle"></h4>
								<%/*<h4 class="small" id="graphStates"></h4>*/%>
								<div id="mbody" style="margin-right:5px; margin-top:20px;">
									<svg style="height: 90%;"></svg>
								</div>
							</div>
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
								<table style="width:98%"><tr style="width:98%; "><td id="timelinetable">
								</td></tr>
								<tr style="width:98%"><td class="modal-body" id="mbodyMultipleQuery"> </td></tr>
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
											<table align="center">
												<tr>

													<td  nowrap="true"><h4 id="modal-title">Timeline:&nbsp;&nbsp;</h4></td>

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
