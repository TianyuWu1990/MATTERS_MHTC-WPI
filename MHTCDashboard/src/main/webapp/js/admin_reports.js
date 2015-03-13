 /*
  * Copyright (C) 2013 Worcester Polytechnic Institute
  * All Rights Reserved. 
  *  
 */  

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
				case "Success": classToAdd = "alert-success"; break;
				case "Warning": classToAdd = "alert-warning"; break;
				case "Error": classToAdd = "alert-danger"; break;
				default: classToAdd = ""; break;
			}
			$cell.addClass(classToAdd);
        }
	});
});