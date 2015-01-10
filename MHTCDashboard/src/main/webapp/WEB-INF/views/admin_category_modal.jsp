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
									<i class="fa fa-times"></i> Close
								</button>
								<button type="submit" class="btn btn-primary">
									<i class="fa fa-check"></i> Submit
								</button>	        				
							</div>
	        			</div>
	        		</div>
	        	</form>
        	</div> <!-- Modal -->