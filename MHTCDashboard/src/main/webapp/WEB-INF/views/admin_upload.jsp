<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin_header.jsp" %> 
<%@ include file="admin_navigation_bar.jsp" %> 

		<script src="js/admin_upload.js"></script>

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
        		<div class="col-lg-4">
        			<form role="form" method="POST" enctype="multipart/form-data"
						action="admin/upload/add/">
						
						<div class="form-group">
							<label>Choose a category:</label>
							<select class="form-control" id="parentcategory" name="parentcategory">
								<option value="">-- Select a category --</option>
								<c:forEach items="${categories}" var="category">
									<option value="${category.value}">${category.key}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="form-group" id="subcat" hidden>
							<label>Choose a subcategory:</label>
							<select class="form-control" id="category" name="category"></select>
						</div>
						
						<div class="form-group">
							<label>File Input</label>
							<input type="file" name="file">
						</div>
						
						<button type="submit" class="btn btn-default"><i class="fa fa-upload fa-fw"></i>Upload File</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
        		</div>
        	</div>
        	<hr />
        	<div class="row">
        		<div class="col-lg-12">
        			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#categoryModal">Add Category</button>
        		</div>
        	</div>
        	
        	<div class="modal fade" id="categoryModal" tabindex="-1" role="dialog" aria-labelledby="categoryModal" aria-hidden="true">
        		<form method="POST" action="admin_addCategory">
	        		<div class="modal-dialog">
	        			<div class="modal-content">
	        				<div class="modal-header">
	        					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
	        					<span class="sr-only">Close</span></button>
	        					<h4 class="modal-title" id="categoryModal">Add a new category</h4>
	        				</div>
	        				<div class="modal-body">
	        					<div class="form-group">
	        						<label>Choose the parent category:</label>
	        						<select class="form-control" id="parentcategory" name="parentcategory">
										<option value="">No parent category</option>
										<c:forEach items="${categories}" var="category">
											<option value="${category.value}">${category.key}</option>
										</c:forEach>
									</select>
	        					</div>
	        					<div class="form-group">
	        						<label>Enter a name for the new category:</label>
	        						<input class="form-control" name="categoryName" required>
	        					</div>
	        					<div class="form-group">
	        						<label>Enter the source:</label>
	        						<input class="form-control" name="source" placeholder="i.e. NSF, Tax Foundation, CNBC, etc." required>
	        					</div>
	        				</div>
	        				<div class="modal-footer">
	        				    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        					<button type="submit" class="btn btn-primary">Save changes</button>
	        				</div>
	        			</div>
	        		</div>
	        	</form>
        	</div>
		</div>

<%@ include file="admin_footer.jsp" %> 