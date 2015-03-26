$(function() {
	/*
	 * Handles populating drop down for subcategories in Manual Upload
	 */
	$('form#uploadFile select#parentcategory').change(function() {
		var value = $("form#uploadFile select#parentcategory").val();
		
		$.getJSON('admin/getSubCategories', {"categoryid":value}, function(data) {
			// Get the <select> tag
			var options = $("form#uploadFile #category");
			
			// Remove all previous entries
			options.find('option').remove();
			
			// Add default <option>
			options.append('<option value="">-- Select a subcategory --</option>');
			
			if (!$.isEmptyObject(data)) {
				// Add each entry from data object to <select>
				data.forEach(function(arrayItem) {
					options.append($("<option />").val(arrayItem.id).text(arrayItem.name));
				})
			}
	
		});
		
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
	
	/**
	 * Populate the list of metrics for the selected subcategory
	 */
	$('form#uploadFile select#category').change(function() {
		var value = $('form#uploadFile select#category').val();
		var text = $('form#uploadFile select#category option:selected').text();
		
		var $div = $('#displayMetrics');
		
		$.getJSON('admin/metrics', {"categoryid":value}, function(data) {
			var divContents = "The possible metrics for category \""+text+"\" are:\n<ul>";

			data.forEach(function(arrayItem) {
				divContents = divContents + "<li>" + arrayItem.name + "</li>";
			});
			
			divContents = divContents + "</ul>";
						
			$div.html(divContents);
		});
		

	});
	
	$("input[name='overwrite']").click(function() {
		
		var thisCheck = $(this);
		
		if (thisCheck.is(":checked")) {
			bootbox.dialog({
				message: "You've selected to <b>overwrite</b> the data that already exists in the database for each metric, year and state tuple with" +
						" the data in the manually uploaded file you selected.<br /><br /> If you are unsure if you want to do this, please confirm via the <b>" +
						"Database Explorer</b>.",
				title: "<i class='fa fa-exclamation-triangle fa-fw'></i> Hold on a second! Are you sure you want to do that?",
				buttons: {
					main: {
						label: "OK",
						classname: "btn-primary"
					}
				}
			});
		}
		
	});
	
	$('#uploadData').click(function() {
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
		var allowedExtensions = new Array("xlsx", "xls");
		
		// Make sure uploaded file is in list of allowable file types
		if ($.inArray(fileExt, allowedExtensions) == -1) {
			$statusDiv.text("The file you attempted to upload is not allowed!");
			
			$statusAlert.html($statusDiv);
			return false;
		}
	});

});