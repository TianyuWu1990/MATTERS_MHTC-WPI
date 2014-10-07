<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<html>
<head>
 <link href="css/nv.d3.css" rel="stylesheet">
<!--  <script src="http://d3js.org/d3.v3.min.js"></script> -->
 <script src="js/d3.v3.min.js"></script>

 <script src="js/nv.d3.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script src="js/raphael.js"></script>
<script src="js/color.jquery.js"></script>
<script src="js/dataquery.js"></script>
<script src="js/jquery.usmap.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script src="js/state.js"></script>
<script src="js/metric.js"></script>
<script src="js/chart.js"></script>
<script src="js/appstate.js"></script>


<link rel="stylesheet" href="css/local_style.css">
<title>MATTERS: Massachusetts' Technology, Talent and Economy Reporting System</title>
</head>
<body onLoad="loadFunction()">
	<!-- a graph -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
		<div class="modal-dialog" >
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

					<!-- <button type="button" class="btn btn-primary btn-right" id="graph_toggle">Switch to Bar
					</button> -->
					<select id="chartType" class="btn btn-primary btn-right">
						<option value="line">Line</option>
						<option value="bar">Bar</option>
						<option value="table">Table</option>
						
					</select>

					<h4 class="modal-title" id="graphTitle">State Science and Technology Graph</h4>
					<h4 class="small" id="graphStates">Selected States: Massachusetts, California, Texas</h4>
				</div>
				<div class="modal-body" id="mbody" style="background-color: rgb(61, 38, 244);">
					<svg style="height: 150%;" ></svg>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Multiple metric, multiple states, multiple year query -->
	
	<div class="modal fade" id="myModalMultipleQuery" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
		<div class="modal-dialog" >
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<select id="yearsMultipleQuery" class="btn btn-primary btn-right"></select>			
					<h4 class="modal-title" id="graphTitleMultipleQuery">State Science and Technology Graph</h4>
					<h6 class="modal-title" >NV: No Value for the year selected</h6>
					<%/** <h4 class="small" id="graphStatesMultipleQuery">Selected States: Massachusetts, California, Texas</h4> */%>
				</div>
				<div class="modal-body" id="mbodyMultipleQuery" style="background-color: rgb(61, 38, 244);">
					<svg style="height: 150%;" ></svg>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<!--US. MAP -->
	<div class="modal fade" id="myModalHeatMap" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
		<div class="modal-dialog" >
			<div class="modal-content" >
				<div class="modal-header">
					
					<%/* <table><tr><td><select id="AllPeerTopBottomHeatMap" class="btn btn-primary btn-right">
						<option value="0">All States</option>
						<option value="1">Peer States</option>
						<option value="2">Top Ten States</option>
						<option value="3">Bottom Ten States</option>
						
					</select></td>
					
					<td>
					</td></tr></table>*/%>
					 <table style="width:100%">
					 	<tr>
					 		<td valign="top" align="left">
					 			<table><tr><td>
				 					<h4 class="modal-title" id="graphTitleHeatMap">State Science and Technology U.S. Heatmap</h4>
									<%/* <h4 class="modal-title" id="graphCatHeatMap"></h4> */%>
									<h4 class="modal-title" id="graphStatesHeatMapPos0">Selected States: All States</h4>
									<h4 class="modal-title" id="graphStatesHeatMapPos1"  style="height:2%;">Ranking</h4>
								</td>
								
								</tr></table>
							</td>
							
							<td  valign="top" align="right">
								<table>
									<tr>
										<td></td>
										<%/**<td valign="top" align="right">
											<select id="yearHeatMap" class="btn btn-primary btn-right"></select>
										</td>**/ %>
									</tr>
									 <tr>
										<td  valign="top" align="left">
											<button type="button" id="playbuttonanimation" class="btn btn-default">Play</button>
											
										    
										</td>
										<td valign="top" align="right">
											<button type="button" id="stopbuttonanimation" class="btn btn-default" disabled="true">Stop </button>
										
									</tr>
								</table>
							</td>
							<td valign="top" align="right">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></td> 
						</tr>
						</table >
						<table>
						<tr>
							<td align="right" nowrap="true">
								<table>
									<tr>
										<% /**** TIMELINE**/ %>
										<td align="left" nowrap="true"><h4 class="modal-title" >Timeline:&nbsp;&nbsp; </td>
									
										<td align="left" nowrap="true">
										
										<div id="timeline"></div></td>
									</tr>
									 <tr>
										<td></td>	
										<td align="right" nowrap="true"><div id="heatmapmeter"></div></td>
										<% /**** TIMELINE **/ %>
									</tr>
								</table>
							</td>
						</tr>
						
						
					</table>
				</div>
				<div class="modal-body" id="mbodyHeatMap" style="background-color: #A7A9A9; height:650px;">
				
					<svg style="width: 100%; height: 100%;" ></svg>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="aboutModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
	
	<nav class="navbar navbar-default" role="navigation" id="navbarid">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"> <img src="css/img/logo.png" height="50px"
					style="position: relative; top: -13px">
				</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" style="text-align: center">
				<ul class="nav navbar-nav navbar-left">
					<li class="active"><a href="#">Explore</a></li>
					<li><a href="#" data-toggle="modal" data-target="#aboutModal">About</a></li>
				</ul>
				<h1 class="centered title">Massachusetts' Technology, Talent and Economy Reporting System</h1>
				<ul class="nav navbar-nav navbar-right">
					<form class="navbar-form navbar-left" action="<c:url value='j_spring_security_check' />" method="POST"
						role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="username" name="username"> <input type="password"
								class="form-control" placeholder="password" name="password">
						</div>
						<button type="submit" value="submit" class="btn btn-default">Login</button>

						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div class="container">
		<div class="row">
			
			<h2 class="text-center">
				<div id="stateTitle">Massachusetts</div>
			</h2>
			
			
			<div class="col-md-7" id="state_container">
					<c:import url="state_tab_display.jsp" />
				
			</div>
			<div class="col-md-5">
				<table><tr><td align="right">
				<button class="btn btn-info" id="multiplequeryTab"  onClick="as.toggleMultiSelect(76,-1);">
					 Multiple Selection
					
				</button>
				
				</td>
				<td valign="top" nowrap="true" align="right">	
					<button class="btn btn-info" onClick="as.SelectUnSelectAllTabs(3)" id="selectallmultiplemetric"  disabled="true">All States</button>
				</td>
				  <td valign="top" nowrap="true" align="right">&nbsp;
					<button class="btn btn-info" onClick="as.SelectPeerStates()" id="selectpeerstates" disabled="true">Peer States</button>
				</td>
			    <td valign="top" nowrap="true" align="right">&nbsp;
					<button class="btn btn-info" onClick="as.SelectUnSelectAllTabs(4)" id="unselectallmultiplemetric" disabled="true">Unselect States</button>
				</td>
				
				
				</tr></table>
				<h2 class="text-center">
					<div id="mapTitle">Click to Select a State</div>
				</h2>
				<table>
					<tr>
						<td valign="top"><div id="map" style="width: 600px; height: 375px;"></div>
						<div id="legend" style="float: right">
							<table>
								<tr>
									<td><div class="legendbox boxblue" id="boxcolorpeerstates"></div></td>
									<td>Peer State</td>
								</tr>
							</table>
							<table id="normallegend">
								<tr>
									<td><div class="legendbox boxgreen"></div></td>
									<td>Selected State</td>
								</tr>
							</table>
							<table id="multilegend" class="hidden">
								<tr>
									<td><div class="legendbox boxgreen"></div></td>
									<td>Selected State</td>
								</tr>
								<tr>
									<td><div class="legendbox boxyellow"></div></td>
									<td>Current State</td>
								</tr>
							</table>
						</div>
						</td>
						<td valign="top" >&nbsp;&nbsp;<div id="verticalheatmapmeter"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="footer">
		<div class="container">
			<p class="text-muted centered">
				<br />&copy;2014 Worcester Polytechnic Institute. All rights reserved.<br /> Sponsored by Mass High Technology
				Council
			</p>
		</div>
	</div>
	
		<script src="js/load.js"></script>
	
</body>
</html>
