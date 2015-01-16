<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin_header.jsp" %> 
<%@ include file="admin_navigation_bar.jsp" %> 

<% // Page specific modals %>
<%@ include file="admin_category_modal.jsp" %> 
<%@ include file="admin_metric_modal.jsp" %> 

		<script src="adminPanel/js/admin_upload.js" type="text/javascript"></script>

        <div id="page-wrapper">
        	<div class="row">
        		<div class="col-lg-12">
        			<h1 class="page-header">Manual Upload</h1>
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-lg-12">
        			<p>The <b>Manual Upload</b> page provides an easy way for administrators to upload a datasource that isn't
        			readily available for automation into the system. The constraints for this upload are as follows:</p>
        			<ul>
        				<li>The datasource to be uploaded must be an Excel spreadsheet (.xlsx or .xls extension)</li>
        				<li>The category/subcategory and metric names must be already in the database</li>
        				<li>The datasource to be uploaded must be consistent with the Unified Format model (for an example, please click <a href="#">here</a>)</li>
        			</ul>
        			<p>If for some reason the upload is unsuccessful, error reporting will be available.</p>
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-lg-12" id="status">
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-lg-12">
        			<c:if test="${database_duplicate_key}">
        				<div class="alert alert-danger">
        					The data you are trying to upload already exists in the database!
        				</div>
        			</c:if>
        			<c:if test="${upload_file_success}">
        				<div class="alert alert-success">
        					You've successfully uploaded ${filename}!
        				</div>
        			</c:if>
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-lg-4">
        			<form id="uploadFile" role="form" method="POST" enctype="multipart/form-data"
						action="admin/upload/add/">
						
						<div class="form-group">
							<label>Choose a category:</label>
							<select class="form-control" id="parentcategory">
								<option value="">-- Select a category --</option>
								<c:forEach items="${categories}" var="category">
									<option value="${category.value}">${category.key}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="form-group" id="subcat">
							<label>Choose a subcategory:</label>
							<select class="form-control" id="category" name="category">
								<option value="">-- Select a subcategory --</option>
							</select>
						</div>
						
						<div class="form-group">
							<label>File Input</label>
							<input type="file" name="file">
						</div>
						
						<button type="submit" class="btn btn-default" id="uploadData"><i class="fa fa-upload fa-fw"></i>Upload File</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
        		</div>
        		<div class="col-lg-6" id="displayMetrics">
        		</div>
        	</div>
        	<hr />
        	<div class="row">
        		<div class="col-lg-12">
        			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#categoryModal">Add Category</button>
        			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#metricModal">Add Metric</button>
        		</div>
        	</div>
     		<br />
            <div class="row">
        		<div class="col-lg-12">
        	        <c:if test="${category_success_add}">
		        			<div class="alert alert-success alert-dismissible" id="addCategorySuccess">
		                    	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		        				You've successfully added a category to the database!
		        			</div>
		        	</c:if>
		        	
        			<c:if test="${metric_success_add}">
		        			<div class="alert alert-success alert-dismissible" id="addMetricSuccess">
		                    	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		        				You've successfully added a metric to the database!
		        			</div>
	        		</c:if>        		
		        </div>
        	</div>   	        	
		</div>

<%@ include file="admin_footer.jsp" %> 