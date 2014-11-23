$("#addSchedBtn").click(function(e) {
	e.preventDefault();
	$("#largeModal").modal("show");
	d = new Date();
	$("#run-date-picker").datetimepicker({
		showToday: true,
		minDate: new Date()
	});
})

$(function() {

});