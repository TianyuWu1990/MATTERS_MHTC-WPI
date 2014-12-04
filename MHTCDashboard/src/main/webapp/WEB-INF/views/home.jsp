<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta charset="utf-8"/>
 <link href="css/nv.d3.css" rel="stylesheet">
<!--  <script src="http://d3js.org/d3.v3.min.js"></script> -->
 <script src="js/d3.v3.min.js"></script>

 <script src="js/nv.d3.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<% /*<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css"> */%>
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

<% /*<script src="js/jquery.multiple.select.js"></script>
<link rel="stylesheet" href="css/sumoselect.css"></link> */%>
 <link rel="stylesheet" href="css/local_style.css">

<title>MATTERS: Massachusetts' Technology, Talent and Economy Reporting System</title>

<!--  NEW DESIGN -->

<link id="bootstrap-style" href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
	<link id="base-style" href="css/style.css" rel="stylesheet">
	<link id="base-style-responsive" href="css/style-responsive.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>

   
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
	<% /* <script src="js/jquery.flot.js"></script> */%>
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
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<% /* <a class="brand" href="index.html"><span>Metro</span></a> */%>
								
				<!-- start: Header Menu -->
				<div class="nav-no-collapse header-nav">
					<ul class="nav pull-right">
						<li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white warning-sign"></i>
							</a>
							<ul class="dropdown-menu notifications">
								<li class="dropdown-menu-title">
 									<span>You have 11 notifications</span>
									<a href="#refresh"><i class="icon-repeat"></i></a>
								</li>	
                            	<li>
                                    <a href="#">
										<span class="icon blue"><i class="icon-user"></i></span>
										<span class="message">New user registration</span>
										<span class="time">1 min</span> 
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="icon green"><i class="icon-comment-alt"></i></span>
										<span class="message">New comment</span>
										<span class="time">7 min</span> 
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="icon green"><i class="icon-comment-alt"></i></span>
										<span class="message">New comment</span>
										<span class="time">8 min</span> 
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="icon green"><i class="icon-comment-alt"></i></span>
										<span class="message">New comment</span>
										<span class="time">16 min</span> 
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="icon blue"><i class="icon-user"></i></span>
										<span class="message">New user registration</span>
										<span class="time">36 min</span> 
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="icon yellow"><i class="icon-shopping-cart"></i></span>
										<span class="message">2 items sold</span>
										<span class="time">1 hour</span> 
                                    </a>
                                </li>
								<li class="warning">
                                    <a href="#">
										<span class="icon red"><i class="icon-user"></i></span>
										<span class="message">User deleted account</span>
										<span class="time">2 hour</span> 
                                    </a>
                                </li>
								<li class="warning">
                                    <a href="#">
										<span class="icon red"><i class="icon-shopping-cart"></i></span>
										<span class="message">New comment</span>
										<span class="time">6 hour</span> 
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="icon green"><i class="icon-comment-alt"></i></span>
										<span class="message">New comment</span>
										<span class="time">yesterday</span> 
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="icon blue"><i class="icon-user"></i></span>
										<span class="message">New user registration</span>
										<span class="time">yesterday</span> 
                                    </a>
                                </li>
                                <li class="dropdown-menu-sub-footer">
                            		<a>View all notifications</a>
								</li>	
							</ul>
						</li>
						<!-- start: Notifications Dropdown -->
						<li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white tasks"></i>
							</a>
							<ul class="dropdown-menu tasks">
								<li class="dropdown-menu-title">
 									<span>You have 17 tasks in progress</span>
									<a href="#refresh"><i class="icon-repeat"></i></a>
								</li>
								<li>
                                    <a href="#">
										<span class="header">
											<span class="title">iOS Development</span>
											<span class="percent"></span>
										</span>
                                        <div class="taskProgress progressSlim red">80</div> 
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
										<span class="header">
											<span class="title">Android Development</span>
											<span class="percent"></span>
										</span>
                                        <div class="taskProgress progressSlim green">47</div> 
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
										<span class="header">
											<span class="title">ARM Development</span>
											<span class="percent"></span>
										</span>
                                        <div class="taskProgress progressSlim yellow">32</div> 
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="header">
											<span class="title">ARM Development</span>
											<span class="percent"></span>
										</span>
                                        <div class="taskProgress progressSlim greenLight">63</div> 
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
										<span class="header">
											<span class="title">ARM Development</span>
											<span class="percent"></span>
										</span>
                                        <div class="taskProgress progressSlim orange">80</div> 
                                    </a>
                                </li>
								<li>
                            		<a class="dropdown-menu-sub-footer">View all tasks</a>
								</li>	
							</ul>
						</li>
						<!-- end: Notifications Dropdown -->
						<!-- start: Message Dropdown -->
						<%/* <li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white envelope"></i>
							</a>
							<ul class="dropdown-menu messages">
								<li class="dropdown-menu-title">
 									<span>You have 9 messages</span>
									<a href="#refresh"><i class="icon-repeat"></i></a>
								</li>	
                            	<li>
                                    <a href="#">
										<span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	6 min
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>  
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
										<span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	56 min
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>  
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
										<span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	3 hours
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>  
                                    </a>
                                </li>
								<li>
                                    <a href="#">
										<span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	yesterday
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>  
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
										<span class="avatar"><img src="img/avatar.jpg" alt="Avatar"></span>
										<span class="header">
											<span class="from">
										    	Dennis Ji
										     </span>
											<span class="time">
										    	Jul 25, 2012
										    </span>
										</span>
                                        <span class="message">
                                            Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                        </span>  
                                    </a>
                                </li>
								<li>
                            		<a class="dropdown-menu-sub-footer">View all messages</a>
								</li>	
							</ul>
						</li> 
						<!-- end: Message Dropdown -->
						<li>
							<a class="btn" href="#">
								<i class="halflings-icon white wrench"></i>
							</a>
						</li>
						<!-- start: User Dropdown -->
						<li class="dropdown">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white user"></i> Dennis Ji
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li class="dropdown-menu-title">
 									<span>Account Settings</span>
								</li>
								<li><a href="#"><i class="halflings-icon user"></i> Profile</a></li>
								<li><a href="login.html"><i class="halflings-icon off"></i> Logout</a></li>
							</ul>
						</li> */%>
						<!-- end: User Dropdown -->
					</ul>
				</div>
				<!-- end: Header Menu -->
				
			</div>
		</div>
	</div>
	
	<div class="container-fluid-full">
		<div class="row-fluid">
				
			<!-- start: Main Menu -->
			<div id="sidebar-left" class="span2">
				<div class="nav-collapse sidebar-nav">
				
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li>
							<a class="dropmenu" href="blank0.html"><span class="hidden-tablet"> State Profile</span>
							</a>
							<ul>
								 <c:forEach items="${jv_stats_profile}" var="stat1">
 								<li><a class="submenu" > <span class="hidden-tablet">
 								<input type="checkbox"  id="check${stat1.metric.id}" checked >
									
									 ${stat1.metric.name}</span></a>
									
									
								</li>
								</c:forEach>
							</ul>	
						</li>
						<li>
							<a class="dropmenu" href="blank1.html"><span class="hidden-tablet"> National Ranking</span>
							</a>
							<ul>
								 <c:forEach items="${jv_stats_national}" var="stat2">
 								<li><a class="submenu" > <span class="hidden-tablet">
									<input type="checkbox"  id="check${stat2.metric.id}" >
									 ${stat2.metric.name}</span></a>
									
									
								</li>
								</c:forEach>
							</ul>	
						</li>
						<li>
							<a class="dropmenu" href="blank2.html"><span class="hidden-tablet"> Talent Ranking</span>
							</a>
							<ul>
								 <c:forEach items="${jv_stats_talent}" var="stat3">
 								<li><a class="submenu" > <span class="hidden-tablet">
									<input type="checkbox"  id="check${stat3.metric.id}" >
									 ${stat3.metric.name}</span></a>
									
									
								</li>
								</c:forEach>
							</ul>	
						</li>
						<li>
							<a class="dropmenu" href="blank3.html"><span class="hidden-tablet"> Cost Ranking</span>
							</a>
							<ul>
								 <c:forEach items="${jv_stats_cost}" var="stat4">
 								<li><a class="submenu" ><span class="hidden-tablet">
									<input type="checkbox"  id="check${stat4.metric.id}" >
									 ${stat4.metric.name}</span></a>
									
									
								</li>
								</c:forEach>
							</ul>	
						</li>
						<li>
							<a class="dropmenu" href="blank4.html"><span class="hidden-tablet">Economy Ranking </span>
							</a>
							<ul>
								 <c:forEach items="${jv_stats_economy}" var="stat5">
 								<li><a class="submenu" > <span class="hidden-tablet">
									<input type="checkbox"  id="check${stat5.metric.id}" >
									 ${stat5.metric.name}</span></a>
									
									
								</li>
								</c:forEach>
							</ul>	
						</li>
						
					</ul>
				</div>
			</div>

       		<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
			<!-- start: Content -->
			<div id="content" class="span10">
			
			
			<% /* <ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="index.html">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Dashboard</a></li>
			</ul> */%>
			
			<div class="row-fluid">
			<div class="pagination pagination-right">
			
				<ul class="nav nav-tabs">
    						<li id="profileTab1"><a href="#profile" data-toggle="tab" onclick="as.graphDeployer(0,'line')" >Lines</a></li>
							<li id="nationalTab1"><a href="#national" data-toggle="tab" onclick="as.showMultipleMetricsStatesYears(-1)">Table</a></li>
							<li id="talentTab1"><a href="#talent" data-toggle="tab" onclick="as.graphDeployer(0,'bar')">Bar</a></li>
							<li id="costTab1"><a href="#heatmaptab" data-toggle="tab" onclick="as.showHeatMapGraphReloaded(0,'#mbodyHeatMap',-1)">Heatmap</a></li>
							
						</ul>
					</div>
			</div>
			 <div class="row-fluid">
				<table>
					<tr>
						<td><div id="MultipleMetricTitle" style="height:60px;"><strong>Choose a metric from the left menu</strong></div></td>
						
					</tr>
					
				</table>
			</div> 
			<div class="row-fluid">
				
				Please select state here to display data
					
				<select id="ms" multiple="multiple" >
					<c:forEach items="${jv_all_states}" var="stat">
						<c:forEach items="${stat.row}" var="row">
							<option value="${row.id}">${row.name}</option>
						
 							
						</c:forEach>					
					</c:forEach>
	        	</select>
				
			</div>	
			
			<div class="row-fluid">
				<div class="tab-content" >
    
    <div class="tab-pane active" id="profile">
		<div class="box">
			<div class="box-header">
				<h2><i class="halflings-icon list-alt"></i><span class="break"></span>Lines</h2>
				<div class="box-icon">
					<a href="#" class="btn-setting">INFO</a>
				</div>
			</div>
		</div>
		<div class="box-content">
			<h4 class="modal-title" id="graphTitle">State Science and Technology Graph</h4>
			<h4 class="small" id="graphStates">Selected States: Massachusetts, California, Texas</h4>
			<div  id="mbody" >
				<svg style="height: 100%;" ></svg>
			</div>
		</div>
	</div>
   
	<div class="tab-pane fade" id="national">
		<div class="box">
			<div class="box-header">
				<h2><i class="halflings-icon list-alt"></i><span class="break"></span>Table</h2>
				<div class="box-icon">
					<a href="#" class="btn-setting">INFO</a>
				</div>
			</div>
		</div>
		<div class="box-content">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<select id="yearsMultipleQuery" class="btn btn-primary btn-right"></select>			
					<h4 class="modal-title" id="graphTitleMultipleQuery">State Science and Technology Graph</h4>
					<h6 class="modal-title" >NV: No Value for the year selected</h6>
					
				</div>
				<div class="modal-body" id="mbodyMultipleQuery">
					<svg style="height: 400%;" ></svg>
				</div>
	</div>
	<div class="tab-pane fade" id="talent">
		<div class="box">
			<div class="box-header">
				<h2><i class="halflings-icon list-alt"></i><span class="break"></span>Bar</h2>
				<div class="box-icon">
					<a href="#" class="btn-setting">INFO</a>
				</div>
			</div>
		</div>
		<div class="box-content">
			<h4 class="modal-title" id="graphTitleBar">State Science and Technology Graph</h4>
			<h4 class="small" id="graphStatesBar">Selected States: Massachusetts, California, Texas</h4>
			<div  id="mbodyBar" >
				<svg style="height: 400%;" ></svg>
			</div>
		</div>
	</div>
	<div class="tab-pane fade" id="heatmaptab">
		<div class="box">
					<div class="box-header">
						<h2><i class="halflings-icon list-alt"></i><span class="break"></span>Heatmap</h2>
						<div class="box-icon">
							<a href="#" class="btn-setting">INFO</a>
							<% /*<a href="#" class="btn-minimize">TWO</a>
							<a href="#" class="btn-close">THREE</a> */ %>
						</div>
					</div>
					<div class="box-content">
						<table style="width:1400px; height:650px;">
						<tr>
							<td align="left" nowrap="true">
								<table>
									<tr>
										
										<td align="left" nowrap="true"><h4 class="modal-title" >Timeline:&nbsp;&nbsp;
										 </td>
										
										<td align="left" nowrap="true"><div id="timeline"></div></td>
										<td  nowrap="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td  valign="top" align="right">
											<button type="button" id="playbuttonanimation" class="btn btn-default">Play</button>
										</td>
								
										<td valign="top" align="right">
											<button type="button" id="stopbuttonanimation" class="btn btn-default" disabled="true">Stop </button>
							    		</td>
									</tr>
									 
								</table>
							</td>
						</tr>
						
						
					<tr><td align="middle" >
					    <table style="width:100%; height:650px;overflow:auto;">
					    	<tr><td align="center"><h2><div id="stateTitle">Massachusetts</div></h2></td></tr>
					    	<tr>
					    		<td valign="top"><div id="mbodyHeatMap" align="center"  style="height:650px;" ></div></td>
					    		<td valign="top" align="left">
					    		<table>
					    		
					    		<tr><td><div id="verticalheatmapmeter"></div></td></tr>
					    		</table></td>
					    		
					  		 </tr>
					    </table>
					</td></tr></table>	
					</div>
				</div>
		
		
	</div>
</div>
</div>
						
			
			
			
		
			
       

	</div><!--/.fluid-container-->
	
			<!-- end: Content -->
		</div><!--/#content.span10-->
		</div><!--/fluid-row-->
		
	<div class="modal hide fade" id="myModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>Settings</h3>
		</div>
		<div class="modal-body">
			<p>Here settings can be configured...</p>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">Close</a>
			<a href="#" class="btn btn-primary">Save changes</a>
		</div>
	</div>
	
	<div class="clearfix"></div>
	
	<footer>

		<p>
			<span style="text-align:left;float:left">&copy; 2013 <a href="http://jiji262.github.io/Bootstrap_Metro_Dashboard/" alt="Bootstrap_Metro_Dashboard">Bootstrap Metro Dashboard</a></span>
			
		</p>

	</footer>

          
                 
       

       <%  /* <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><div id="MultipleMetricTitle">Choose a metric from the left menu</div></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
            	<div class="row show-grid">
                	<div class="col-md-6 col-md-offset-6">
                		<ul class="nav nav-tabs">
    						<li id="profileTab"><a href="#profile" data-toggle="tab" onclick="as.changeColorPeerStates('#profiletab a')" >State Profile</a></li>
							<li id="nationalTab"><a href="#national" data-toggle="tab" onclick="as.changeColorPeerStates('#nationaltab a')">National Rankings</a></li>
							<li id="talentTab"><a href="#talent" data-toggle="tab" onclick="as.changeColorPeerStates('#talenttab a')">Talent Metrics</a></li>
							<li id="costTab"><a href="#cost" data-toggle="tab" onclick="as.changeColorPeerStates('#costtab a')">Cost Metrics</a></li>
							<li id="economyTab"><a href="#economy" data-toggle="tab" onclick="as.changeColorPeerStates('#economytab a')">Economy Metrics</a></li>
						</ul>
					</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            
 			<div class="row">
 			Please select state here to display data
					
				<select id="ms" multiple="multiple">
					<c:forEach items="${jv_all_states}" var="stat">
						<c:forEach items="${stat.row}" var="row">
 							<option value="${row.id}">${row.name}</option>
						</c:forEach>					
					</c:forEach>
	        	</select>
 			</div>
         
            
            
         
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div> */%>
    <!-- /#wrapper -->

    


	<!-- a graph -->
<%  /* 	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
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
					
					
					 <table style="width:100%">
					 	<tr>
					 		<td valign="top" align="left">
					 			<table><tr><td>
				 					<h4 class="modal-title" id="graphTitleHeatMap">State Science and Technology U.S. Heatmap</h4>
									
									<h4 class="modal-title" id="graphStatesHeatMapPos0">Selected States: All States</h4>
									<h4 class="modal-title" id="graphStatesHeatMapPos1"  style="height:2%;">Ranking</h4>
								</td>
								
								</tr></table>
							</td>
							
							<td  valign="top" align="right">
								<table>
									<tr>
										<td></td>
										
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
										
										<td align="left" nowrap="true"><h4 class="modal-title" >Timeline:&nbsp;&nbsp; </td>
									
										<td align="left" nowrap="true">
										
										<div id="timeline"></div></td>
									</tr>
									 <tr>
										<td></td>	
										<td align="right" nowrap="true"><div id="heatmapmeter"></div></td>
										
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
	*/%>
		<script src="js/load.js"></script> 
	
	
</body>
</html>
