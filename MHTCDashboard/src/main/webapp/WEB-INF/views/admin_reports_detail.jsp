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
                    <h1 class="page-header">Logs for pipeline job <span id="pipelineName">${job}</span></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Pipeline Log Explorer
                            
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable-wrapper">
                                <table class="table table-striped table-bordered table-hover" id="reports-tbl">
                                    <thead>
                                        <tr>
                                            <th>Log ID</th>
                                            <th>Origin</th>
                                            <th>Code</th>
                                            <th>Message</th>
                                            <th>Date and Time</th>
                                            <th>Priority</th>
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
			"url": "admin_get_logs?job=${job}",
			"dataSrc": ""
		},
		"aoColumns": [
		            { "mData": "id" },
		            { "mData": "origin" },
		            { "mData": "code" },
		            { "mData": "moment" }, 
		            { "mData": "message" },
		            { "mData": "priority" },        
		],
		"createdRow": function ( row, data, index ) {
			var $cell = $('td', row).eq(4);
			var classToAdd = "";
			switch (data.status) {
				case "success": classToAdd = "alert-success"; break;
				case "warning": classToAdd = "alert-warning"; break;
				case "error": classToAdd = "alert-danger"; break;
				default: classToAdd = ""; break;
			}
			$cell.addClass(classToAdd);
        }
	});
});
</script>