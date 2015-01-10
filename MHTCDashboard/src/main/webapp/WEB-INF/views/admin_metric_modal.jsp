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
									<i class="fa fa-times"></i> Close
								</button>
								<button type="submit" class="btn btn-primary">
									<i class="fa fa-check"></i> Submit
								</button>	        				
							</div>
	        				</form>
	        			</div>
	        		</div>
        	</div> <!-- Modal -->        	
