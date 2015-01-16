$(function() {
	/*
	 * Handles populating drop down for subcategories in Database Explorer
	 */
	$('select#category').change(function() {
		var value = $("select#category").val();
		
		$.getJSON('getSubCategories', {"categoryid":value}, function(data) {
			// Get the <select> tag
			var options = $("#subcatdd");
			
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
		
		$('td#subcat').show();
	});
	
	/*
	 * Handles populating latest run, year selector after subcategory is chosen
	 */
	$('select#subcatdd').change(function() {
		var value = $("select#subcatdd").val();
		
		// If looking for metrics belonging to categories, not subcategories
		if ($.isEmptyObject(value)) {
			value = $("select#category").val();
		}
				
		// DataTable implementation
		if ($.fn.dataTable.isDataTable('#categoryTable')) {
			$('#categoryTable').DataTable().destroy();
		}
		
		$('#categoryTable').DataTable({
			"ajax": {
				"url": "admin_dbexplorer/getMetricData?categoryid="+value,
				"dataSrc": ""
			}
		});
		
		$('#dbrows').show();
		
	});
	
	if ($.fn.dataTable.isDataTable('#metricTable')) {
		$('#metricTable').DataTable().destroy();
	}
	
	$('#metricTable').DataTable({
		"ajax": {
			"url": "admin_dbexplorer/getDetailedMetricData",
			"dataSrc": ""
		}
	});
	
	// Controls tab functionality
	$('#databaseTab a[href="#bySource"]').click(function (e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	$('#databaseTab a[href="#byCategory"]').click(function (e) {
		e.preventDefault();
		$(this).tab('show');
	});

});


