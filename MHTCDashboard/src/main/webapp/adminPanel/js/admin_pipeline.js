$(function() {
	/*
	 * Handles populating drop down for subcategories in Manual Upload
	 */
	$('form#uploadPipeline #parentcategory').change(function() {
		var value = $("form#uploadPipeline select#parentcategory").val();
		
		$.getJSON('getSubCategories', {"categoryid":value}, function(data) {
			// Get the <select> tag
			var options = $("form#uploadPipeline #subcategory");
			
			// Remove all previous entries
			options.find('option').remove();
			
			// Add default <option>
			options.append('<option value="">-- Select a subcategory --</option>');
			options.append('<option value="">No subcategory</option>');
			
			if (!$.isEmptyObject(data)) {
				// Add each entry from data object to <select>
				for (key in data) {
					options.append($("<option />").val(data[key]).text(key));
				}
			}
	
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
		
		$.getJSON('getSubCategories', {"categoryid":value}, function(data) {
			// Get the <select> tag
			var options = $("form#admin_addMetric #category");
			
			// Remove all previous entries
			options.find('option').remove();
			
			// Add default <option>
			options.append('<option value="">-- Select a subcategory --</option>');
			options.append('<option value="">No subcategory</option>');
			
			if (!$.isEmptyObject(data)) {
				// Add each entry from data object to <select>
				for (key in data) {
					options.append($("<option />").val(data[key]).text(key));
				}
			}
	
		});
		
	});
	
	// DataTable implementation
	if ($.fn.dataTable.isDataTable('#pipelineTbl')) {
		$('#pipelineTbl').DataTable().destroy();
	}
	
	var table = $('#pipelineTbl').DataTable({
		"processing": true,
		"ajax": {
			"url": "admin_pipeline/getPipelineData",
			"dataSrc": ""
		},
		"aoColumns": [
			            { "mData": "pipelinename" },
			            { "mData": "pipelinedesc" },
//			            { "mData": "path" },
			            { "mData": "filename" },
			            { "mData": null,
			            	"sClass": "center",
			            	"bSortable": false,
			            	"sDefaultContent": "<button type='button' class='btn btn-link btn-sm edit'><i class='fa fa-pencil-square-o fa-2x'></i></button>"
			            }
		]
	});
	
	$('#pipelines').show();
	
	$('#pipelineTbl tbody').on("click", "button.edit", function() {
		// Get the pipeline name of the one user wants to delete/edit
		var pipelineName = $(this).closest("tr").find('td:first').html();
	});
			
});