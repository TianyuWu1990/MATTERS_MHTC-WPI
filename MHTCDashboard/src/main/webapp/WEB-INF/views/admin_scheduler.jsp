<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin_header.jsp" %> 
<style>
	.input-group {
		padding-top: 10px;
		width: 300px;
	}
</style>
<%@ include file="admin_navigation_bar.jsp" %> 
		
        <div id="page-wrapper">

			<div class="modal fade" id="largeModal" tabindex="-1" role="dialog"
				aria-labelledby="largeModal" aria-hidden="true">
				<form action="admin_scheduler_add" method="POST">
				<div class="modal-dialog modal-md">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Add a Pipeline Job Scheduler</h4>
						</div>
						<div class="modal-body">

							<!-- Schedule job -->
							<div class="input-group">
								<strong>Schedule Job</strong><br />
								<select class="form-control" name="sched_job">
									<option value="0">Talend</option>
								</select>
							</div>
							<!-- Schedule name -->
							<div class="input-group">
							  <strong>Schedule name</strong><br/>
							  <input type="text" name="sched_name" class="form-control">
							</div>
							
							
							
							<!-- Schedule calendar -->
							<div class="input-group">
							  	<strong>Run date</strong><br/>
				                <div class='input-group date' id='run-date-picker'>
				                    <input type='text' class="form-control" name="sched_date"/>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
				                    </span>
				                </div>
							</div>							
							
							
							<!-- Schedule job -->
							<div class="input-group">
							  <strong>Schedule Description</strong><br/>
							  <textarea name="sched_description" class="form-control" style="width: 548px; height: 80px;"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal"><i class="glyphicon glyphicon-remove"></i> Close</button>
							<button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-ok"></i> Schedule!</button>
						</div>
					</div>
				</div>
				</form>
			</div>
			<div class="row">
        		<div class="col-lg-12">
        			<h1 class="page-header">Scheduler</h1>
        		</div>
        		
		        <div class="row">
		        	<div class="col-lg-12">
	        			<p>The <b>Scheduler</b> provides a way to schedule a pipeline job to run on a determined interval/point of time.
		        	</div>
		        </div>   
		           
		             		
        		<p>
        			<button data-toggle="modal" data-target="#addSchedModal" id="addSchedBtn" class="btn btn-sm btn-success" type="button"><i class="glyphicon glyphicon-plus"></i> New Schedule</button>
				</p>
				<c:choose>
				    <c:when test="${success_add}">
				       <div role="alert" class="alert alert-success">Schedule added.</div>
				    </c:when>
				    <c:when test="${success_stop}">
				        <div role="alert" class="alert alert-success">Schedule deleted.</div>
				    </c:when>
				</c:choose>
				<div class="row">
					
				</div>
				
				<div class="panel panel-default"><div class="panel-heading">		
	        		<p id="schedule_list">
		        		<table class="table">
		        			<thead>
		        			<tr>
		        				<th>Schedule name</th>
		        				<th>Schedule time</th>
		        				<th>Pipeline Job</th>
		        				<th>Job Description</th>
		        				<th>Action</th>
		        			</tr>
		        			</thead>
		        			<tbody>
		        			</tr>
		          				<td>IPEDS</td>
		          				<td>Every year</td>
		          				<td>IPEDS Wrapper</td>
		          				<td>2014 - Degree granting status</td> 
		          				<td>
		          					<button class="btn btn-xs btn-danger" type="button"><i class="glyphicon glyphicon-off"></i> Stop</button>        					
		          				</td> 			
		        			<tr>
		        			
		        			<c:forEach var="schedule" items="${schedList}">
		        			
			        			<tr>
			          				<td>${schedule.getSched_name()}</td>
			          				<td>${schedule.getSched_date() }</td>
			          				<td>${schedule.getSched_job() }</td>
			          				<td>${schedule.getSched_description() }</td> 
			          				<td>
			          					<a href="admin_scheduler_stop?job_name=${schedule.getJob_name()}"><button class="btn btn-xs btn-danger" type="button"><i class="glyphicon glyphicon-off"></i> Stop</button> </a> 
			          				</td> 			
			        			</tr>
		        			</c:forEach>
		        			</tbody>
		        		</table>
	        		</p>
        		</div> </div>
        	</div>
        </div>
        
     
<script src="js/admin-scheduler.js"></script>
<%@ include file="admin_footer.jsp" %> 