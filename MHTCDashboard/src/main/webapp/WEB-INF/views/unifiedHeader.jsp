<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
  
  		<div class="head-sponsors"><a href="http://www.mhtc.org/"><img src="img/header-mhtc.png" alt="header-sponsors" width="" height="" /></a>
  									<a href="http://www.wpi.edu/"><img src="img/header-wpi.png" alt="header-sponsors" width="" height="" /></a></div>
  
  		<nav id="site-navigation" class="main-navigation" role="navigation">
  			<h1 class="menu-toggle">Menu</h1>
  			<div class="menu-close"></div>
			<div class="menu" style="z-index: 9999;">
				<ul>
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
			</div>
  		</nav><!-- #site-navigation -->
  		
  	</div><!-- .header-inner -->
		
	</header><!-- .site-header -->
	<!-- google analytics for matters.mhtc.org -->
	<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-61279483-2', 'auto');
  ga('send', 'pageview');

</script>
