<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin_header.jsp" %> 
<%@ include file="admin_navigation_bar.jsp" %> 

    <div id="wrapper">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Database Explorer</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
	        <!-- /.row -->
	        <div class="row">
	        	<div class="col-lg-12">
	        		<p>The <b>Database Explorer</b> provides an in-depth look into the contents of the database.
	        		Please select a category and subcategory, which will reveal further information, such as:
	        		</p>
	        		<ul>
	        			<li>The Talend job name of the pipeline for this category</li>
	        			<li>When the pipeline was last run</li>
	        			<li>Whether it was successful or not</li>
	        		</ul>
	        		<p>From there, you can then select a year for data in this category, which will populate the table
	        		with <b>states</b> along the y-axis, and the various possible <b>metrics</b> along the x-axis.
	        		</p>
	        	</div>
	        </div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<table id="form">
								<tr>
									<td>
										Choose a category:
										<select id="category" name="category">
											<option value="">-- Select a category --</option>
											<c:forEach items="${categories}" var="category">
												<option value="${category.value}">${category.key}</option>
											</c:forEach>
										</select>
									</td>
									<td id="subcat" hidden>
										Choose a subcategory:
										<select id="subcatdd"></select>
									</td>
								</tr>
							</table>
							<div id="subcattable" hidden>
								<table class="table">
									<thead>
										<tr>
											<td>Talend jobname</td>
											<td>Date/time of last run</td>
											<td>Status</td>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>talend_pipeline_1</td>
											<td>January 1st, 1970 12:34:56</td>
											<td class="success"><i class="fa fa-check fa-fw"></i> Success!</td>
										</tr>
									</tbody>
								</table>
								Please select a year to view data for this subcategory from:
								<select id="year">
									<option value="">-- Select a year --</option>
									<option>2009</option>
									<option>2010</option>
									<option>2011</option>
									<option>2013</option>
								</select>
							</div>
						</div>
						<!-- /.panel-heading -->
						<div id="dbrows" class="panel-body" hidden>
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-examples">
									<thead>
										<tr>
											<th class="col-md-2">State</th>
											<th class="col-md-3">Technology and Innovation Rank</th>
											<th class="col-md-2">Year</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>Massachusetts</td>
											<td>42</td>
											<td>2010</td>
										</tr>
										<tr>
											<td>Vermont</td>
											<td>25</td>
											<td>2010</td>
										</tr>
										<tr>
											<td>Texax</td>
											<td>3</td>
											<td>2010</td>
										</tr>
										<tr>
											<td>California</td>
											<td>33</td>
											<td>2010</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- /.table-responsive -->
	
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
	
		</div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="js/plugins/metisMenu/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').dataTable();
    });
    </script>

</body>

</html>





<%@ include file="admin_footer.jsp" %>