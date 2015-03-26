$(function() {
	/*
	 * Handles populating drop down for subcategories in Manual Upload
	 */
	$('form#uploadPipeline #parentcategory').change(function() {
		var value = $("form#uploadPipeline select#parentcategory").val();
		
		$.getJSON('admin/getSubCategories', {"categoryid":value}, function(data) {
			// Get the <select> tag
			var options = $("form#uploadPipeline #subcategory");
			
			// Remove all previous entries
			options.find('option').remove();
			
			// Add default <option>
			options.append('<option value="">-- Select a subcategory --</option>');
			
			if (!$.isEmptyObject(data)) {
				// Add each entry from data object to <select>
				data.forEach(function(arrayItem) {
					options.append($("<option />").val(arrayItem.id).text(arrayItem.name));
				});
			}
	
		});
		
	});
	
	/**
	 * Populates the metric dropdown after subcategory has been selected
	 */
	$('form#uploadPipeline #subcategory').change(function() {
		var value = $('form#uploadPipeline select#subcategory').val();
		
		$.getJSON('admin/metrics', {"categoryid":value}, function(data) {
			// Get the <select> tag
			var options = $("form#uploadPipeline #metric");
			
			// Remove all previous entries
			options.find('option').remove();
			
			// Add default <option>
			options.append('<option value="">-- Select a metric --</option>');
			
			data.forEach(function(arrayItem) {
				options.append($("<option />").val(arrayItem.id).text(arrayItem.name));
			});
			
		});
		
	});
	
	/**
	 * Change around data to send the names of the categories instead of the IDs
	 * Contains form validation as well
	 */
	$('#uploadScript').click(function() {
		
		var $fileInput = $('input[type="file"]');
		
		var $statusAlert = $('#status');
		var $statusDiv = $('<div class="alert alert-danger" role="alert" />');
		
		// Make sure user has selected a file to upload
		if ($fileInput.val() == '') {			
			$statusDiv.text("You haven't selected a file to upload!");
			
			$statusAlert.html($statusDiv);
			return false;
		}
		
		var file = $fileInput[0].files[0];		
		var fileName = file.name;
		var fileSize = file.size;
		
		// Make sure it's not a blank file
		if (fileSize <= 0) {
			$statusDiv.text("The file you uploaded is empty!");
			
			$statusAlert.html($statusDiv);
			return false;
		}
		
		var fileExt = fileName.split('.').pop();
		var allowedExtensions = new Array("zip");
		
		// Make sure uploaded file is in list of allowable file types
		if ($.inArray(fileExt, allowedExtensions) == -1) {
			$statusDiv.text("You must upload a file with the .zip extension!");
			
			$statusAlert.html($statusDiv);
			return false;
		}
							
		var parentText = $('form#uploadPipeline select#parentcategory option:selected').text();
		var childText = $('form#uploadPipeline select#subcategory option:selected').text();
		
		$('form#uploadPipeline select#parentcategory option:selected').val(parentText);
		$('form#uploadPipeline select#subcategory option:selected').val(childText);
		
	});
	
	$('form#admin_addMetric select#parentcategory').change(function() {
		var value = $('form#admin_addMetric select#parentcategory').val();
		
		$.getJSON('admin/getSubCategories', {"categoryid":value}, function(data) {
			// Get the <select> tag
			var options = $("form#admin_addMetric #category");
			
			// Remove all previous entries
			options.find('option').remove();
			
			// Add default <option>
			options.append('<option value="">-- Select a subcategory --</option>');
			
			if (!$.isEmptyObject(data)) {
				// Add each entry from data object to <select>
				data.forEach(function(arrayItem) {
					options.append($("<option />").val(arrayItem.id).text(arrayItem.name));
				});
			}
	
		});
		
	});
	
	// DataTable implementation
	if ($.fn.dataTable.isDataTable('#pipelineTbl')) {
		$('#pipelineTbl').DataTable().destroy();
	}
	
	var $table = $('#pipelineTbl').DataTable({
		"processing": true,
		"ajax": {
			"url": "admin_pipeline/getPipelineData",
			"dataSrc": ""
		},
		"columns": [
			            { "data": "pipelineName" },
			            { "data": "pipelineDesc" },
			            { "data": "filename" },
			            { "data": "dateAdded" },
			            { "data": "uploadedBy"},
			            { "data": null,
			            	"sClass": "center",
			            	"bSortable": false,
			            	"sDefaultContent": "<button type='button' class='btn btn-link btn-sm delete'><i class='fa fa-times fa-2x'></i></button>"
			            },
			            { "data": null,
			            	"sClass": "center",
			            	"bSortable": false,
			            	"sDefaultContent": "<button type='button' class='btn btn-success run'>Run</button>"
			            }
		],
		"order": [[3, "desc"]]
	});
	
	$('#pipelines').show();
	
	$('#pipelineTbl tbody').on("click", "button.run", function() {
		var $row = $(this).closest("tr");
		
		// Get the pipeline name of the one user wants to delete/edit
		var pipelineName = $row.find('td:first').html();

		
		$.post("admin/execute", {pipelineName:pipelineName}, function(data) {
			$("#deletePipelineSuccess").after('<div class="alert alert-success" role="alert">'+data+'</div>');
		});
		
	});
	
	$('#pipelineTbl tbody').on("click", "button.delete", function() {
		var $row = $(this).closest("tr");
		
		// Get the pipeline name of the one user wants to delete/edit
		var pipelineName = $row.find('td:first').html();
				
		bootbox.confirm("Are you sure you want to delete \""+pipelineName+"\" pipeline?", function(result) {
			if (result == true) {
				
				$.post("admin_pipeline/delete", {pipelineName:pipelineName}, function(data) {
					$table.row($row).remove().draw();

					var $deleteSuccess = $('#deletePipelineSuccess');
					
					var $successDiv = $('<div class="alert alert-success" role="alert" />');

					var successMessage = '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>You\'ve successfully delete pipeline "' +
										pipelineName + '"!';
					
					$deleteSuccess.html($successDiv.html(successMessage));
				});
						
			}
		});
	});
			
});