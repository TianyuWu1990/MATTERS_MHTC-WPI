$(function() {
	var pipelineJobs;
	var pipeline_selector_cron = $("#addCronSchedModal select[name='sched_job']");
	var pipeline_selector = $("#addSchedModal select[name='sched_job']");
	
	$.get("admin_pipeline/getPipelineData", function(data) {
		// Populate Talend pipelines;
		data.forEach(function(arrayItem) {
			pipeline_selector.append('<option value="' + arrayItem.pipelineName + '">' + arrayItem.pipelineName + '</option>');
			pipeline_selector_cron.append('<option value="' + arrayItem.pipelineName + '">' + arrayItem.pipelineName + '</option>');
		});
	});
	
	
	$("#addSchedBtn").click(function(e) {
		$("#addSchedModal").modal("show");
		d = new Date();
		$("#run-date-picker").datetimepicker({
			showToday: true,
			minDate: new Date(),
			format: 'MM/DD/YYYY HH:mm:ss',
			useSeconds: true
		});		
	});
	
	$("#addCronSchedBtn").click(function(e) {
		$("#addCronSchedModal").modal("show");
	});
	
	$("#pauseSchedBtn").click(function(e) {
		var $this = $(this);
		$.get("admin_scheduler_toggle", function(data) {	
			if ($this.hasClass("btn-warning")) {
				$this.removeClass("btn-warning");
				$this.addClass("btn-primary");
				$this.find("i").removeClass("fa-pause");
				$this.find("i").addClass("fa-play");
				$this.html("<i class=\"fa fa-play\"></i> Restart the Scheduler");
				$(".paused-overlay").css("visibility","visible")
			} else {
				$this.addClass("btn-warning");
				$this.removeClass("btn-primary");
				$this.find("i").removeClass("fa-play");
				$this.find("i").addClass("fa-pause");
				$this.html("<i class=\"fa fa-pause\"></i> Pause the Scheduler");	
				$(".paused-overlay").css("visibility","hidden")
			}
		});
	});
});