<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin_header.jsp" %> 
<%@ include file="admin_navigation_bar.jsp" %>

        <div id="page-wrapper">
        	<div class="row">
        		<div class="col-lg-12">
        			<h1 class="page-header">Welcome to MATTERS: Administration Center!</h1>
        		</div>
        	</div>  
        	<div class="row">
        		<div class="col-lg-12">
                	<p>Welcome to the MATTERS: Administration Center! This tool provides provides administration access to the MATTERS 
                	Dashboard with capabilities, such as:</p>
                	<ul>
                		<li>Adding a new datasource manually</li>
                		<li>Exploring the contents of the database</li>
                		<li>Mananging the current pipelines in place, as well as adding new ones</li>
                		<li>Creating a schedule for the pipelines to run</li>
                		<li>Reporting services to check the status of each pipeline</li>
                	</ul>
                	<p>These functions can be selected from the navigation bar buttons on the left. If you are unsure about how to perform a task, 
                	please click on the <i class="fa fa-user fa-fw"></i> icon located at the top right of the page, and select <b>Help</b>.
              		</p>
              		<p>To logout, please select the same <i class="fa fa-user fa-fw"></i> icon and select <b>Logout</b>. This will bring you back to the
              		main MATTERS Dashboard.
              		</p>
                </div>
             </div>
        </div>     

<%@ include file="admin_footer.jsp" %> 

