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
	 */
	$('#uploadScript').click(function() {
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
	if ($.fn.dataTable.isDataTable('#pipelinesTbl')) {
		$('#pipelinesTbl').DataTable().destroy();
	}
	
	$('#pipelinesTbl').DataTable({
		"ajax": {
			"url": "admin_get_pipeline_data",
			"dataSrc": ""
		}
	});
	
	$('#pipelines').show();

});