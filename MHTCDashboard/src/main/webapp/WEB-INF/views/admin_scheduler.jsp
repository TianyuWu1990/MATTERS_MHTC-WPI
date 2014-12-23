<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="admin_header.jsp" %> 
<style>
	.input-group {
		padding-top: 10px;
		width: 300px;
	}
</style>
<%@ include file="admin_navigation_bar.jsp" %> 

<script src="adminPanel/js/admin_scheduler.js"></script>

		
        <div id="page-wrapper">

			<%@ include file="admin_scheduler_modal_basic.jsp" %> 
			<%@ include file="admin_scheduler_modal_cron.jsp" %> 
			
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
        			<button data-toggle="modal" data-target="#addSchedModal" id="addSchedBtn" class="btn btn-sm btn-success" type="button"><i class="glyphicon glyphicon-calendar"></i> New Schedule</button>
					<button data-toggle="modal" data-target="#addCronSchedModal" id="addCronSchedBtn" class="btn btn-sm btn-success" type="button"><i class="glyphicon glyphicon-repeat"></i> New Cron Schedule</button>
			
					<c:choose>
					    <c:when test="${inStandbyMode}">
					       <button id="pauseSchedBtn" class="btn btn-sm btn-primary" type="button"><i class="glyphicon glyphicon-play"></i> Restart the Scheduler</button>
					    </c:when>
					    <c:otherwise>
					        <button id="pauseSchedBtn" class="btn btn-sm btn-warning" type="button"><i class="glyphicon glyphicon-pause"></i> Pause the Scheduler</button>
					    </c:otherwise>
					</c:choose>				
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
				
				<div class="panel panel-default"><div id="schedule-panel" class="panel-heading">		
					<div class="paused-overlay" style="visibility: ${inStandbyMode ? 'visible' : 'hidden'}"><h1>Scheduler paused</h1></div>
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
			        			<c:forEach var="schedule" items="${schedList}">
			        			
				        			<tr>
				          				<td>${schedule.getSched_name()}</td>
				          				<td>${schedule.getSched_date() }</td>
				          				<td>${schedule.getSched_job() }</td>
				          				<td>${schedule.getSched_description() }</td> 
				          				<td>
				          					<a href="admin_scheduler_stop?job_name=${schedule.getJob_name()}"><button class="btn btn-xs btn-danger" type="button"><i class="glyphicon glyphicon-off"></i> Stop / Remove</button> </a> 
				          				</td> 			
				        			</tr>
			        			</c:forEach>
		        			</tbody>
		        		</table>
	        		</p>
        		</div> </div>
        	</div>
        </div>
        
     
<%@ include file="admin_footer.jsp" %> 

