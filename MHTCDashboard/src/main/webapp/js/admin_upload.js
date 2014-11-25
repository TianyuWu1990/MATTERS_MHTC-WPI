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
			options.append('<option value="">Uncategorized</option>');
			
			if (!$.isEmptyObject(data)) {
				// Add each entry from data object to <select>
				for (key in data) {
					options.append($("<option />").val(data[key]).text(key));
				}
			}
	
		});
		
	});
	
	/**
	 * In case someone doesn't select a subcategory for the upload form, it will default to the main category
	 */
	$('form#uploadFile').submit(function() {
		if ($('form#uploadFile select#category').val() == '') {
			$('form#uploadFile select#category').val($('form#uploadFile select#parentcategory').val());
		}
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
			options.append('<option value="">Uncategorized</option>');
			
			if (!$.isEmptyObject(data)) {
				// Add each entry from data object to <select>
				for (key in data) {
					options.append($("<option />").val(data[key]).text(key));
				}
			}
	
		});
		
	});
	
	$("button#addCategory").click(function() {
		$.ajax({
			type: "POST",
			url: "admin_addCategory",
			data: $('form#admin_addCategory').serialize(),
			success: function() {
				$('div#addCategorySuccess').show();
				$("div#categoryModal").modal('hide');
			},
			error: function(xhr, status, error) {
				console.log(xhr);
				console.log(status);
				console.log(error);
			}
		});
	});
	
	$('button#addMetric').click(function() {		
		$.ajax({
			type: "POST",
			url: "admin_addMetric",
			data: $('form#admin_addMetric').serialize(),
			success: function() {
				$('div#addMetricSuccess').show();
				$('div#metricModal').modal('hide');
			},
			error: function(xhr, status, error) {
				console.log(xhr);
				console.log(status);
				console.log(error);
			}
		});
	});

});