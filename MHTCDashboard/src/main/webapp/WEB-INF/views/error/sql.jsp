<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../admin_header.jsp" %> 
<%@ include file="../admin_navigation_bar.jsp" %> 

        <div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">
						<span class="fa-stack fa-lg">
							<i class="fa fa-database fa-stack-1x"></i>
							<i class="fa fa-ban fa-stack-2x text-danger"></i>
						</span> 
						Uh oh! Something went wrong...
					</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h3>SQL Error:</h3>
					<ul>
						<li>Message: ${sqlException.message}</li>
						<li>SQL State Code: ${sqlException.SQLState }</li>
					</ul>
					<p>Please contact your database administrator regarding this issue.</p>
				</div>
			</div>
			<!-- <div class="row">
				<div class="col-lg-12">
					<h2>Error Stacktrace:</h2>
					<c:forEach items="${sqlException.stackTrace}" var="trace">
						${trace}
					</c:forEach>
				</div>
			</div>  -->			
		</div> <!-- /#page-wrapper -->

<%@ include file="../admin_footer.jsp" %>