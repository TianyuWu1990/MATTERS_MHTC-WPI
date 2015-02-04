<% /*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
 %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<html>
	<head>
		<meta charset="utf-8" />
		
		<!-- Library CSS -->
		<link href="css/nv.d3.css" rel="stylesheet">
		<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
		<link href="css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet">
		<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
		<link href="css/dataTables.css" rel="stylesheet">
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext'
			rel='stylesheet' type='text/css'>
			
		<!-- Custom CSS -->
		<link href="css/style.css" id="base-style" rel="stylesheet">
		<link href="css/style-responsive.css" id="base-style-responsive" rel="stylesheet">
		
		<!-- Library JS -->
		<script src="js/d3.v3.min.js"></script>
		<script src="js/nv.d3.min.js"></script>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
		
		<script src="js/raphael.js"></script> <!-- Dependency for raphael.js -->
		<script src="js/jquery.usmap.js"></script>
		
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/dataTables.js"></script>
		
		<script src="js/modernizr.js"></script>
		
		<!-- Custom JS -->
		<script src="js/dataquery.js"></script>
		<script src="js/state.js"></script>
		<script src="js/metric.js"></script>
		<script src="js/chart.js"></script>
		<script src="js/appstate.js"></script>
		
		<title>MATTERS: Massachusetts' Technology, Talent and Economy
			Reporting System</title>
	
	</head>
	<body onLoad="loadFunction()">
		<div class="modal hide fade" id="feedbackModal" tabindex="-1" role="dialog" aria-labelledby="feedbackModal" aria-hidden="true">
			<form action="feedback_post" method="POST" style="margin-bottom: 0px;">
				<div class="modal-dialog modal-md" style="width:100%">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Site Feedback</h4>
						</div>
						<div class="modal-body" style="padding: 15 15 15 15;">
							<div class="input-group">
								<strong>Your accountname</strong><br /> 
								${username}
							</div>
							
							<div class="input-group">
								<strong>Subject</strong><br /> 
								<input style="height:30px" type="text" name="subject" class="form-control">
							</div>
	
							<div class="input-group">
								<strong>Comments</strong><br />
								<div class='input-group date' id='run-date-picker'>
									<textarea class="form-control" name="comments" style="width: 530px; height: 180px;"/></textarea>
								</div>
							</div>
							
							<div style="margin-bottom: 25px;" align="center">
	                           <%
					                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LfXmgATAAAAABM7oYTbs6-XZyU29ozVca5taJIb",
					                                    "6LfXmgATAAAAAP_qkRZBcBBqnb8yRuUKMm9LJYSW", false);
					                out.print(c.createRecaptchaHtml(null, null));
					            %> 
		                   </div>
		
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<i class="fa fa-times"></i> Close
							</button>
							<button type="submit" class="btn btn-primary">
								<i class="fa fa-envelope"></i> Send
							</button>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="navbar">
			<div class="navbar-inner">
					<div class="container-fluid">
						<img class="nav pull-left" src="css/img/MHTC_Logo.jpg" alt="Mass High Technology Council" >
						
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
										<form id="loginForm" method="post" class="signin" action="${pageContext.request.contextPath}/login/">
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
						                
						                <input class="submit button" type="submit" value="Login">
						                <p align="center">
						               		 <a class="forgot" href="<c:url value="/user/forgot" />">Forgot your password</a>
						                </p>
						                <p align="center">
						                	<a class="register" href="<c:url value="/user/register" />">Register</a>
						                </p>
						                </form>
									</div>
								</li>
							</sec:authorize>
							
	                        <sec:authorize access="isAuthenticated()">
	                        	<li id="feedback" onclick="$('#feedbackModal').modal('show');"><a href="#feedback">Feedback</a></li>
								<li id="logout"><a href="logout/">Logout</a></li>
							</sec:authorize>
						</ul>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
	
				<div style="background:#7b0020; margin:0px; padding-top: 20px; padding-bottom: 10px;">
					<p align="center" style="font-size:22px;font-weight:400;"> 
						MATTERS: Massachusetts Technology, Talent, and Economic Reporting System
					</p>
				</div>
			</div>
		</div>
	
		<div class="container-fluid-full">
				<!-- start: left sidebar -->
				<div id="sidebar-left" class="sidebar">
					<div class="column">
						<div class="title">
							<span>Select Metrics</span>
						</div>
						<div class="sidebar-nav">
							<ul class="nav nav-tabs nav-stacked main-menu">
								<li>
									<a class="dropmenu" href="#">
										State Profile 
									</a>
									<ul id="stateMetric" class="listcontent">
										<li class="selectUnselectAll" data-toggle="tooltip" title="Click to select/deselect all" id="deselect">
											<a style="text-align:right;">Deselect All</a>
										</li>
										<c:forEach items="${jv_stats_profile}" var="stat1">
											<li>
												<a class="selected metricOption" id="${stat1.metric.id}"> 
													<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
													${stat1.metric.name}
												</a>
											</li>
										</c:forEach>
									</ul>
								</li>
								<li>
									<a class="dropmenu" href="#">
										National Ranking 
									</a>
									<ul id="nationalProfileList">
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
									<a class="dropmenu" href="#">
										Talent Metrics 
									</a>
									<ul id="talentProfileList" >
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
									<a class="dropmenu" href="#">
										Cost Metrics
									</a>
									<ul id= "costProfileList">
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
									<a class="dropmenu" href="#">
										Economy Metrics
									</a>
									<ul id="economyProfileList">
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
					<div class="column">
						<div class="title">
							<span>Select States</span>
							<a id="close-sidebar-left" href="#">
								<i class="fa fa-caret-left fa-2x"></i>
							</a> 
						</div>
						<div class="sidebar-nav">
							<ul class="nav nav-tabs nav-stacked main-menu">
								<li>
									<ul id="stateSelection" class="listcontent" >
										<li class="selectUnselectAllStates" data-toggle="tooltip" title="Click to select/deselect all" id="deselect">
											<a style="text-align:right;">
												Deselect All
											</a>
										</li>
										<li>
											<a class="selectPeerStates" id="${row.id}">
												Select Peer States
											</a>
										</li>
										<li class="stateFilter">
											<input type="text" placeholder="Filter states...">
										</li>
										<c:forEach items="${jv_all_states}" var="stat">
											<c:forEach items="${stat.row}" var="row">
												<li class="stateSelectionOptionWrapper" id="${row.id}">
													
															<c:choose>
											        			<c:when test="${row.id == (21)}">
											        			<a class="stateSelectionOption selected" id="${row.id}">
											        			</c:when>
											        			<c:otherwise>
											        			<a class="stateSelectionOption" id="${row.id}">
											        			</c:otherwise>
											        		</c:choose>			
											        		<i class="fa fa-check"></i> <!-- This tag displays a check when selected -->
															${row.name} (${row.abbr})
											        </a>
												</li>
											</c:forEach>
										</c:forEach>	
									</ul>
								</li>
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
						
							<!-- Display none at first b/c metrics menu is open by default --> 
							<a href="#" id="open-sidebar-left"> 
								<i class="fa fa-caret-right fa-2x"></i>
							</a>
						</div>
					
						<!--  Right side of the pagination header. -->
						<ul class="nav nav-tabs" id="viz-tabs">					
							<li class="graph-tab active" id="table-tab" >
								<a href="#table" data-toggle="tab" title="Table" onclick="as.showMultipleMetricsStatesYears(-1); as.hideGraphTitle();">
									<i class="fa fa-table fa-2x"></i>
								</a>
							</li>
							<li class=" graph-tab " id="line-tab">
								<a href="#line" data-toggle="tab" title="Line Chart" onclick="as.graphDeployer(0,'line'); as.showGraphTitle();">
									<i class="fa fa-line-chart fa-2x"></i>
								</a>
							</li>
							<li class="graph-tab" id="bar-tab">
								<a href="#bar" data-toggle="tab" title="Bar Chart" onclick="as.graphDeployer(0,'bar'); as.showGraphTitle();"> 
									<i class="fa fa-bar-chart fa-2x"></i>
								</a>
							</li>
							<li class="graph-tab" id="heatmap-tab">
								<a href="#heatmaptab" data-toggle="tab" title="Heatmap" onclick="as.showHeatMapGraphReloaded(0,'#mbodyHeatMap',-1); as.showGraphTitle();">
									<i class="fa fa-picture-o fa-2x"></i>
								</a>
							</li>
							<li class="graph-tab" id="table-tab" >
								<a href="#table" data-toggle="tab" title="Excel Data" onclick="as.exportExcelData()">
								<i class="fa fa-file-excel-o fa-2x"></i></a>
							</li>
						</ul>
					</div>
					
					<div id="vizView">

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
											<td id="timelinetable"></td>
										</tr>
										<tr>
											<td class="modal-body" id="mbodyMultipleQuery"></td>
										</tr>
									</table>
								</div>
							</div>
							
							<!-- Bar Chart -->
							<div class="tab-pane fade" id="bar">
								<div class="box-content">
									<div id="mbodyBar" style="margin-right:100px; style="margin-top:20px;"></div>
								</div>
							</div>
							
							<!-- Heatmap -->		
							<div class="tab-pane fade" id="heatmaptab">
								<div class="box-content">			
									<table style="width: 100%;">
										<tr>
											<td align="left" nowrap="true">
												<table align="center">
													<tr>
														<td nowrap="true"><h4 id="modal-title">Timeline:&nbsp;&nbsp;</h4></td>
														<td nowrap="true"><div id="timeline"></div></td>
														<td style="padding-left: 40px">
															<button type="button" id="playbuttonanimation">Play</button>
															<button type="button" id="stopbuttonanimation" disabled="true">Stop</button>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									<div class="row">
			    						<div class="span10" >
			    							<div id ="mbodyHeatMap" style=""></div>
			    						</div>
			    						<div class="span2" id="verticalheatmapmeter"></div>
			    					</div>
			    							
								</div>
							</div>
						</div>
					</div>
					
					<!-- Error Reporting -->
					<div id="errorView" style="display:none;">
						
						<div id="errorMsgWrapper">
							<i class="fa fa-exclamation-triangle fa-2x"></i>
							<span id="errorMsg"></span>
						</div>
					</div>
					<!-- End Error Reporting -->	
				</div>		
		</div>
		<!-- end: Content -->
		
		<footer>
				<div>
				&copy; 2014 Worcester Polytechnic Institute. All rights reserved.
						Sponsored by Mass High Technology Council
				</div>	
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

