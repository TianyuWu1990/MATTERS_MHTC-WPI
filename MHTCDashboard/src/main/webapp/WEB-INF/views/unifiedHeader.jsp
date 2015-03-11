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

