<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

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
  			  <a href="/mhtc/" rel="home" class="main-logo">

  			  </a>
  			</h1>
  		</div>
  
  		<div class="head-sponsors"><img src="img/header-sponsors.jpg" alt="header-sponsors" width="" height="" /></div>
  
  		<nav id="site-navigation" class="main-navigation" role="navigation">
  			<h1 class="menu-toggle">Menu</h1>
  			<div class="menu-close"></div>
			<div class="menu" style="z-index: 9999;">
				<ul>
					<li class="menu-item"><a href="/mhtc">Home</a></li>
					<li class="menu-item"><a href="/mhtc/explore">Explore</a></li>
					<li class="menu-item"><a href="/mhtc/profile">States</a></li>
					<li class="menu-item"><a href="/mhtc/about">About</a></li>

					<li class="menu-item"><a href="/mhtc/feedback">Feedback</a></li>

					
					<sec:authorize access="hasRole('ADMIN')">
				    	<li class="menu-item"><a href="/mhtc/admin">Admin Panel</a></li>
				   	</sec:authorize>
					
					<sec:authorize access="isAuthenticated()">
						<li class="menu-item"><a href="/mhtc/logout/">Logout</a></li>
					</sec:authorize>
					<sec:authorize access="!isAuthenticated()">
						<li class="menu-item"><a href="/mhtc/login">Login</a></li>
					</sec:authorize>
				</ul>
			</div>
  		</nav><!-- #site-navigation -->
  		
  	</div><!-- .header-inner -->
		
	</header><!-- .site-header -->

