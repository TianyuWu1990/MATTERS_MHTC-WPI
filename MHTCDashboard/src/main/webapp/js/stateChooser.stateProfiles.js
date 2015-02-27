$(document).ready(function() {
	$("#stateChooserTitle").click(function() {
		$("#stateChooser").slideToggle();
	});
	
	$(document).click(function (e) {
		var chooser = $("#stateChooser");
		var title = $("#stateChooserTitle");
		
		if (!chooser.is(e.target) && !title.is(e.target)
				&& chooser.has(e.target).length === 0 && title.has(e.target).length === 0)
		{
			$("#stateChooser").slideUp();
		}
	});
	
	$(".stateChoice").click(function (e) {
		var stateName = $(this).html();

		window.location.search = "?name=" + stateName;
	});
	
});