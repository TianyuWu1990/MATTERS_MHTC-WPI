	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-static-top" role="navigation"
		style="margin-bottom: 0">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value="admin/"/>">MATTERS: Administration Center</a>
		</div>
		<!-- /.navbar-header -->
	
		<ul class="nav navbar-top-links navbar-right">
			<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-user fa-fw"></i><i class="fa fa-caret-down"></i></a>
				<ul class="dropdown-menu dropdown-user">
					<li>
						<a href="<c:url value="admin/logout"/>"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
					</li>
				</ul> <!-- /.dropdown-user -->
			</li>
			<!-- /.dropdown -->
		</ul>
		<!-- /.navbar-top-links -->
		
		<!-- Sidebar Navigation -->
		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav nav-sidebar" id="side-menu">
					<li class="divider"></li>
					<li><a class="active" href="<c:url value="admin/"/>"><i class="fa fa-dashboard fa-fw"></i>  Admin Dashboard</a></li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side -->
	</nav>