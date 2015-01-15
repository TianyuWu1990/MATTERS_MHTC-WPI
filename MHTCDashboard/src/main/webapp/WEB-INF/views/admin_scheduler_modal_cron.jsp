<div class="modal fade" id="addCronSchedModal" tabindex="-1" role="dialog"
	aria-labelledby="addCronSchedModal" aria-hidden="true">
	<form action="admin_scheduler_add" method="POST">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Add a Pipeline Job Cron Scheduler</h4>
				</div>
				<div class="modal-body">

					<!-- Schedule job -->
					<div class="input-group">
						<strong>Schedule Job</strong><br /> <select class="form-control" name="sched_job">
							<option value="0">Talend</option>
						</select>
					</div>
					<!-- Schedule name -->
					<div class="input-group">
						<strong>Schedule name</strong><br /> <input type="text" name="sched_name" class="form-control">
					</div>



					<!-- Schedule calendar -->
					<div class="input-group">
						<strong>Cron expression (<a href="http://www.cronmaker.com" target="_blank">CronMaker</a>)</strong><br />
						<div class="input-group" id="cron-picker" style="width: 500px;"></div>
						<input type="text" name="sched_date" class="form-control">
						<input type="hidden" name="sched_cron" value="true">
					</div>


					<!-- Schedule job -->
					<div class="input-group">
						<strong>Schedule Description</strong><br />
						<textarea name="sched_description" class="form-control"
							style="width: 548px; height: 80px;"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<i class="fa fa-times"></i> Close
					</button>
					<button type="submit" class="btn btn-primary">
						<i class="fa fa-check"></i> Schedule!
					</button>
				</div>
			</div>
		</div>
	</form>
</div>