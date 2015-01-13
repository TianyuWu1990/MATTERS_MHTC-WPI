<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../admin_header.jsp" %> 
<%@ include file="../admin_navigation_bar.jsp" %> 

        <div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header"><i class="fa fa-ban fa-2x text-danger"></i> Uh oh! Something went wrong...</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h3>Error Message:</h3>
					<p>${exception.message}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<p> Please contact your system administrator to resolve this issue.</p>
				</div>
			</div>
			<!-- <div class="row">
				<div class="col-lg-12">
					<h2>Error Stacktrace:</h2>
					<c:forEach items="${exception.stackTrace}" var="trace">
						${trace}
					</c:forEach>
				</div>
			</div>  -->			
		</div> <!-- /#page-wrapper -->

<%@ include file="../admin_footer.jsp" %>