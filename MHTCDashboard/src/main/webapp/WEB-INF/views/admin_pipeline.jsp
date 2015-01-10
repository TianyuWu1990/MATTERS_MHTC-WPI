<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin_header.jsp" %> 
<%@ include file="admin_navigation_bar.jsp" %> 

		<script src="adminPanel/js/admin_pipeline.js" type="text/javascript"></script>

        <div id="page-wrapper">
        	<div class="row">
        		<div class="col-lg-12">
        			<h1 class="page-header">Pipeline Manager</h1>
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-lg-12">
        			<p>The <b>Pipeline Manager</b> page provides administrators the ability to upload new Talend pipeline scripts,
        			check on which Talend pipeline scripts have been uploaded, as well as download and delete Talend pipeline scripts
        			on the server. When uploading pipeline scripts, please upload the exported file from Talend in the .zip format!
        			</p>
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-lg-12" id="status">
        		</div>
        	</div>
        	<div class="row">
        		<div class="col-lg-4">
        			<form id="uploadPipeline" role="form" method="POST" enctype="multipart/form-data"
						action="admin_addPipeline">
						
						<div class="form-group">
							<label>Choose a category:</label>
							<select class="form-control" id="parentcategory" name="parentcategory" required>
								<option value="">-- Select a category --</option>
								<c:forEach items="${categories}" var="category">
									<option value="${category.value}">${category.key}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="form-group">
							<label>Choose a subcategory:</label>
							<select class="form-control" id="subcategory" name="subcategory" required>
								<option value="">-- Select a subcategory --</option>
							</select>
						</div>
						
						<div class="form-group">
							<label>Enter a name for the pipeline:</label>
							<input class="form-control" name="pipelineName" placeholder="Enter pipeline name" required>
						</div>
						
						<div class="form-group">
							<label>Enter a brief description of the pipeline:</label>
							<textarea class="form-control" name="pipelineDesc"rows="5" cols="15"></textarea>
						</div>
						
						<div class="form-group">
							<label>File Input</label>
							<input type="file" name="script">
						</div>
						
						<button type="submit" class="btn btn-default" id="uploadScript"><i class="fa fa-upload fa-fw"></i>Upload Script</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
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
        	        <c:choose>
        				<c:when test="${category_success_add}">
		        			<div class="alert alert-success alert-dismissible" id="addCategorySuccess">
		                    	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		        				You've successfully added a category to the database!
		        			</div>
		        		</c:when>
		        	</c:choose>
		        	
        			<c:choose>
        				<c:when test="${metric_success_add}">
		        			<div class="alert alert-success alert-dismissible" id="addMetricSuccess">
		                    	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		        				You've successfully added a metric to the database!
		        			</div>
	        			</c:when>
		        	</c:choose>        		
		        </div>
        	</div>   	
        	
        	<div class="modal fade" id="categoryModal" tabindex="-1" role="dialog" aria-labelledby="categoryModal" aria-hidden="true">
        		<form id="admin_addCategory" action="admin_addCategory" method="POST">
	        		<div class="modal-dialog">
	        			<div class="modal-content">
	        				<div class="modal-header">
	        					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
	        					<span class="sr-only">Close</span></button>
	        					<h4 class="modal-title" id="categoryModal">Add a new category!</h4>
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
								<button type="button" class="btn btn-danger" data-dismiss="modal">
									<i class="glyphicon glyphicon-remove"></i> Close
								</button>
								<button type="submit" class="btn btn-primary">
									<i class="glyphicon glyphicon-ok"></i> Submit
								</button>	        				
							</div>
	        			</div>
	        		</div>
	        	</form>
        	</div> <!-- Modal -->
        	
        	<div class="modal fade" id="metricModal" tabindex="-1" role="dialog" aria-labelledby="metricModal" aria-hidden="true">
	        		<div class="modal-dialog">
	        			<div class="modal-content">
	        				<div class="modal-header">
	        					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
	        					<span class="sr-only">Close</span></button>
	        					<h4 class="modal-title" id="metricModal">Add a new metric!</h4>
	        				</div>
	        				<form id="admin_addMetric" action="admin_addMetric" method="POST">
	        				<div class="modal-body">
	        					<div class="form-group">
	        						<label>Choose the parent category:</label>
	        						<select class="form-control" id="parentcategory" name="parentcategory" required>
										<option value="">-- Select a category --</option>
										<c:forEach items="${categories}" var="category">
											<option value="${category.value}">${category.key}</option>
										</c:forEach>
									</select>
	        					</div>
	        					<div class="form-group" id="subcat">
									<label>Choose a subcategory:</label>
									<select class="form-control" id="category" name="subcategory">
										<option value="">-- Select a subcategory --</option>
									</select>
								</div>
	        					<div class="form-group">
	        						<label>Enter a name for the new metric:</label>
	        						<input class="form-control" name="metricName" required>
	        					</div>
	        					<div class="form-group">
	        						<label>Enter a description for the new metric:</label>
	        						<textarea class="form-control" name="metricDesc" rows="5" cols="15"></textarea>
	        					</div>	        					
	        					<div class="form-group">
	        						<label>Enter the datatype:</label>
	        						<select class="form-control" name="datatype" required>
	        							<option value="">-- Select a datatype --</option>
	        							<c:forEach items="${datatypes}" var="datatype">
											<option value="${datatype}">${datatype}</option>
										</c:forEach>
	        						</select>
	        					</div>
	        					<div class="form-group">
	        						<label>Is this metric calculated from others?</label>
	        						<label class="radio-inline">
	        							<input type="radio" name="isCalculated" value="true" required>Yes
	        						</label>
	        						<label class="radio-inline">
	        							<input type="radio" name="isCalculated" value="false" required>No
	        						</label>
	        					</div>
	        				</div>
	        				<div class="modal-footer">
								<button type="button" class="btn btn-danger" data-dismiss="modal">
									<i class="glyphicon glyphicon-remove"></i> Close
								</button>
								<button type="submit" class="btn btn-primary">
									<i class="glyphicon glyphicon-ok"></i> Submit
								</button>	        				
							</div>
	        				</form>
	        			</div>
	        		</div>
		        	</div> <!-- Modal -->
		
			<div id="pipelines" class="panel-body" hidden>
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover"
						id="pipelineTbl">
						<thead>
							<tr>
								<th>Pipeline Name</th>
								<th>Pipeline Description</th>
								<th>Path to file</th>
								<th>Filename</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<!-- /.table-responsive -->
		
			</div>
	</div>

<%@ include file="admin_footer.jsp" %> 