$(function() {
	$('#reports-tbl').DataTable({
		"processing": true,
		"ajax": {
			"url": "admin_get_logs?job=",
			"dataSrc": ""
		},
		"columns": [
					{ "data": "job" },
		            { "data": "message" },
		            { "data": "logCount" }, 
		            { "data": "job" }
     
		],
		"createdRow": function ( row, data, index ) {
			console.log(data);
			var $cell = $('td', row).eq(3);
			$cell.html("<a href=\"admin_reports_detail?job=" +  data['job'] +" \"> View </a>");
        }
	});
});