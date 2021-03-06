<% /*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
 %>
 
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin_header.jsp" %> 
<%@ include file="admin_navigation_bar.jsp" %> 
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Reports</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Availabe Logs      
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="reports-tbl">
                                    <thead>
                                        <tr>
                                            <th>Pipeline Name</th>
                                            <th>Recent Message</th>
                                            <th>Log counts</th>
                                            <th>View detail</th>
                                        </tr>
                                    </thead>
                                    <tbody>
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

<%@ include file="admin_footer.jsp" %> 

<script type="text/javascript">
$(function() {
	$('#reports-tbl').DataTable({
		"processing": true,
		"ajax": {
			"url": "admin_get_logs?job=",
			"dataSrc": ""
		},
		"aoColumns": [
					{ "mData": "job" },
		            { "mData": "message" },
		            { "mData": "log_count" }, 
		            { "mData": "job" }
     
		],
		"createdRow": function ( row, data, index ) {
			console.log(data);
			var $cell = $('td', row).eq(3);
			$cell.html("<a href=\"admin_reports_detail?job=" +  data['job'] +" \"> View </a>");
        }
	});
});
</script>