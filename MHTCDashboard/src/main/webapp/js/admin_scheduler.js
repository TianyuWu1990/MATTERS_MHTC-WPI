$("#addSchedBtn").click(function(e) {
	$("#addSchedModal").modal("show");
	d = new Date();
	$("#run-date-picker").datetimepicker({
		showToday: true,
		minDate: new Date(),
		useSeconds: true
	});
});

$("#addCronSchedBtn").click(function(e) {
	$("#addCronSchedModal").modal("show");
});

$("#pauseSchedBtn").click(function(e) {
	var $this = $(this);
	$.get( "admin_scheduler_toggle", function(data) {	
		if ($this.hasClass("btn-warning")) {
			$this.removeClass("btn-warning");
			$this.addClass("btn-primary");
			$this.find("i").removeClass("glyphicon-pause");
			$this.find("i").addClass("glyphicon-play");
			$this.html("<i class=\"glyphicon glyphicon-play\"></i> Restart the Scheduler");
			$(".paused-overlay").css("visibility","visible")
		} else {
			$this.addClass("btn-warning");
			$this.removeClass("btn-primary");
			$this.find("i").removeClass("glyphicon-play");
			$this.find("i").addClass("glyphicon-pause");
			$this.html("<i class=\"glyphicon glyphicon-pause\"></i> Pause the Scheduler");	
			$(".paused-overlay").css("visibility","hidden")
		}
	});
});