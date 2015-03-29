<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(document).ready(function(){
	  	$(".x-close").click(function(){
	  		$(".menu").slideUp(100);
	  		
	  	});
	});
	$(document).ready(function(){
		$(".menu-icon").click(function(){
		  	$(".menu").slideDown();
		});
	});
</script>


<header class="site-header">
	  
	  <div class="container header-inner">

	  	  <div class="super-header">
	
	  	    <div class="super-header-inner">
	
		    <div class="super-signup">
		    </div>
	    	    <div class="super-nav">
	    	    </div>
	    	  </div>
	
	  	  </div>
	  	  <div class="site-branding">
	  			<h1 class="site-title">
	  			  <a href="<c:url value="/"/>" rel="home" class="main-logo">
	
	  			  </a>
	  			</h1>
	  	  </div>
	  	  <div class="head-sponsors" ><a href="http://www.mhtc.org/"><img src="img/header-mhtc.png" alt="header-sponsors" width="" height="" /></a>
	  									<a href="http://www.wpi.edu/"><img src="img/header-wpi.png" alt="header-sponsors" width="" height="" /></a>
	  	  </div> 
	  	 
	  </div><!-- .header-inner -->	  
  	  
  		 <nav id="site-navigation" class="main-navigation" role="navigation">
  			<input type="checkbox" id="menuToggle" style="display:none">
  			 <!-- <h1 for="menuToggle" class="menu-toggle">Menu</h1>-->
  			<label for="menuToggle" class="menu-icon">&#9776;</label>
  			<!--  <div class="menu-close"></div>-->
			<nav class="menu">
				<ul>
					<div class="x-close"></div>
					<li class="menu-item"><a href="<c:url value="/"/>">Home</a></li>
					<li class="menu-item"><a href="<c:url value="/explore"/>">Explore</a></li>
					<li class="menu-item"><a href="<c:url value="/profile"/>">States</a></li>
					<li class="menu-item"><a href="<c:url value="/about"/>">About</a></li>

					<li class="menu-item"><a href="<c:url value="/feedback"/>">Feedback</a></li>

					
					<sec:authorize access="hasRole('ADMIN')">
				    	<li class="menu-item"><a href="<c:url value="/admin"/>">Admin Panel</a></li>
				   	</sec:authorize>
					
					<sec:authorize access="isAuthenticated()">
						<li class="menu-item"><a href="<c:url value="/logout/"/>">Logout</a></li>
					</sec:authorize>
					<sec:authorize access="!isAuthenticated()">
						<li class="menu-item"><a href="<c:url value="/login"/>">Login</a></li>
					</sec:authorize>
				</ul>
			</nav>
  		</nav><!-- #site-navigation -->
  	<!-- .header-inner -->
		
	</header><!-- .site-header -->
<script>
 			(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
 	 		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  			})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  			ga('create', 'UA-61279483-1', 'auto');
  			ga('send', 'pageview');

	</script>
