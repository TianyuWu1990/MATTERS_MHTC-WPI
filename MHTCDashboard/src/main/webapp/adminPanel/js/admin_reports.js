$(function() {
	$('#reports-tbl').DataTable({
		"processing": true,
		"ajax": {
			"url": "admin_get_logs",
			"dataSrc": ""
		},
		"aoColumns": [
		            { "mData": "id" },
		            { "mData": "logDateTime" },
		            { "mData": "componentName" },
		            { "mData": "message" },
		            { "mData": "status" }
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