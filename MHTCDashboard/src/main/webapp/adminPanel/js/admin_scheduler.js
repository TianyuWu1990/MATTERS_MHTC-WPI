$(function() {
	var pipelineJobs;
	var pipeline_selector_cron = $("#addCronSchedModal select[name='sched_job']");
	var pipeline_selector = $("#addSchedModal select[name='sched_job']");
	
	$.get("admin_pipeline/getPipelineData", function(data) {
		pipelineJobs = data;
	});
	
	
	$("#addSchedBtn").click(function(e) {
		$("#addSchedModal").modal("show");
		d = new Date();
		$("#run-date-picker").datetimepicker({
			showToday: true,
			minDate: new Date(),
			useSeconds: true
		});
		
		// Populate Talend pipelines;
		jQuery.each(pipelineJobs, function(index, value) {
			console.log(pipelineJobs);
			pipeline_selector.append('<option value="' + value.pipelinename + '">' + value.pipelinename + '</option>');
		});
		
	});
	
	$("#addCronSchedBtn").click(function(e) {
		$("#addCronSchedModal").modal("show");
		
		// Populate Talend pipelines;
		jQuery.each(pipelineJobs, function(index, value) {
			pipeline_selector_cron.append('<option value="' + value.pipelinename + '">' + value.pipelinename + '</option>');
		});
	});
	
	$("#pauseSchedBtn").click(function(e) {
		var $this = $(this);
		$.get("admin_scheduler_toggle", function(data) {	
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
});