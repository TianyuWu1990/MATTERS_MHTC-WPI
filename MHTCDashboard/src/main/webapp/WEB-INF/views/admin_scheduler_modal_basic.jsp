<div class="modal fade" id="addSchedModal" tabindex="-1" role="dialog"
	aria-labelledby="addSchedModal" aria-hidden="true">
	<form action="admin_scheduler_add" method="POST">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Add a Pipeline Job
						Scheduler</h4>
				</div>
				<div class="modal-body">

					<!-- Schedule job -->
					<div class="input-group">
						<strong>Schedule Job</strong><br /> <select class="form-control"
							name="sched_job">
							<option value="0">Talend</option>
						</select>
					</div>
					<!-- Schedule name -->
					<div class="input-group">
						<strong>Schedule name</strong><br /> <input type="text"
							name="sched_name" class="form-control">
					</div>



					<!-- Schedule calendar -->
					<div class="input-group">
						<strong>Run date</strong><br />
						<div class='input-group date' id='run-date-picker'>
							<input type='text' class="form-control" name="sched_date" /> <span
								class="input-group-addon"><span
								class="glyphicon glyphicon-calendar"></span> </span>
						</div>
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
						<i class="glyphicon glyphicon-remove"></i> Close
					</button>
					<button type="submit" class="btn btn-primary">
						<i class="glyphicon glyphicon-ok"></i> Schedule!
					</button>
				</div>
			</div>
		</div>
	</form>
</div>