$(function() {
	/*
	 * Handles populating drop down for subcategories in Manual Upload
	 */
	$('form#uploadFile select#parentcategory').change(function() {
		var value = $("form#uploadFile select#parentcategory").val();
		
		$.getJSON('getSubCategories', {"categoryid":value}, function(data) {
			// Get the <select> tag
			var options = $("form#uploadFile #category");
			
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

});