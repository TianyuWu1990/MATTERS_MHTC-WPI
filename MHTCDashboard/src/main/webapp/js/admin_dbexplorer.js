$(function() {
	// For Database Explorer
	
	$('#subcat').change(function() {
		$('#subcattable').show();
	});

	$('#year').change(function() {
		$('#dbrows').show();
	});
});

$(function() {
	/*
	 * Handles populating drop down for subcategories in Database Explorer
	 */
	$('select#category').change(function() {
		var value = $("select#category").val();
		
		$.getJSON('admin_dbexplorer/getSubCategories', {"categoryid":value}, function(data) {
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
		
		var tbody = $("#test > tbody");
		
		/*$.getJSON('admin_dbexplorer/getMetrics', {"categoryid":value}, function(data) {
			for (var i = 0; i < data.length; i++) {
				var st = "";
				st += '<tr>';
				st += '<td>'+data[i]["StateName"]+'</td>';
				st += '<td>'+data[i]["MetricName"]+'</td>';
				st += '<td>'+data[i]["Value"]+'</td>';
				st += '<td>'+data[i]["Year"]+'</td>';
				st += '</tr>';
				tbody.append(st);
			}
		});*/
		
		// DataTable implementation
		$('#test').DataTable({
			"ajax": {
				"url": "admin_dbexplorer/getMetrics?categoryid="+value,
				"dataSrc": ""
			}
		});
		
		$('#dbrows').show();
		
	});
});


