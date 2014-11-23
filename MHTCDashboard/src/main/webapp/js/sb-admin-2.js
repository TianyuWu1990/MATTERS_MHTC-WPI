$(function() {

    $('#side-menu').metisMenu();

});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse')
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse')
        }

        height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    })
});

$(function() {
	// To dynamically set the class="active" for the navigation bar
	// Will extract /mhtc/admin
	var pathname = window.location.pathname;
	// Will extract just admin from above
	var filename = pathname.substring(pathname.lastIndexOf('/') + 1);
	
	// Now select the <li> with the ID from filename
	// and add the class active to it
	$('#'+filename+' a').addClass("active");
});

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
    $('#dataTables-examples').DataTable();
} );

/*
 * Handles populating drop down for subcategories in Database Explorer
 */
$('#category').change(function() {
	var value = $("select#category").val();
	
	$.getJSON('admin_dbexplorer/'+value, function(data) {
		// Get the <select> tag
		var options = $("#subcatdd");
		
		// Remove all previous entries
		options.find('option').remove();
		
		// Add default <option>
		options.append('<option value="">-- Select a subcategory --</option>');
		
		// Add each entry from data object to <select>
		for (key in data) {
			options.append($("<option />").val(data[key]).text(key));
		}
	});
	
	$('#subcat').show();
});

/**
 * Scheduler 
 */
$(function() {
	$("#largeModal").modal();
});
