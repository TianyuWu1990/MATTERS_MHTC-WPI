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

