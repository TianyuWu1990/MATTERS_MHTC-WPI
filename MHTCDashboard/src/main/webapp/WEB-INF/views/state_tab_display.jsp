<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<script src="js/funct.js"></script>
<div id="sidebar" class="col-md-12">

<ul class="nav nav-tabs">
    <li id="profileTab"><a href="#profile" data-toggle="tab" onclick="as.changeColorPeerStates('#profiletab a')" >State Profile</a></li>
	<li id="nationalTab"><a href="#national" data-toggle="tab" onclick="as.changeColorPeerStates('#nationaltab a')">National Rankings</a></li>
	<li id="talentTab"><a href="#talent" data-toggle="tab" onclick="as.changeColorPeerStates('#talenttab a')">Talent Metrics</a></li>
	<li id="costTab"><a href="#cost" data-toggle="tab" onclick="as.changeColorPeerStates('#costtab a')">Cost Metrics</a></li>
	<li id="economyTab"><a href="#economy" data-toggle="tab" onclick="as.changeColorPeerStates('#economytab a')">Economy Metrics</a></li>
	
</ul>

<!-- Tab panes -->
<div class="tab-content" >
    
    <div class="tab-pane fade" id="profile">
		<table class="table table-hover" style="font-size: 13px;" >
			<thead>
				<th>Index / Survey</th>
				<th>Ranking / Data</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Profile-tbody">
			    <c:set var="style_values" scope="session" value="'#profiletab a'"/>
			    
				<c:set var="data_values" scope="session" value="${jv_stats_profile}" />
				<c:import url="profile_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>
		</table>
	</div>
   
	<div class="tab-pane fade" id="national">
		<table class="table table-hover" style="font-size: 13px;">
			<thead>
				<th>Index / Survey</th>
				<th>Ranking / Data</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="National-tbody" >
			    <c:set var="style_values" scope="session" value="'#nationaltab a'"/>
				<c:set var="data_values" scope="session" value="${jv_stats_national}" />
				<c:import url="rank_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>
		</table>
	</div>
	<div class="tab-pane fade" id="talent">
		<table class="table table-hover" style="font-size: 13px;">
			 <thead>
				<th>Index / Survey</th>
				<th>Ranking / Data</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Talent-tbody" >
			    <c:set var="style_values" scope="session" value="'#talenttab a'"/>
				<c:set var="data_values" scope="session" value="${jv_stats_talent}" />
				<c:import url="rank_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>
			 <% /* <thead>
				<th>Index / Survey</th>
				<th>Trend</th>
				<th>Value</th>
				<th>Year</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Talent-tbody">
			    <c:set var="style_values" scope="session" value="'#talenttab a'"/>
				<c:set var="data_values" scope="session" value="${jv_stats_talent}" />
				<c:import url="simple_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody> */%>
		</table>
	</div>
	<div class="tab-pane fade" id="cost">
		<table class="table table-hover" style="font-size: 13px;">
			<thead>
				<th>Index / Survey</th>
				<th>Ranking / Data</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Cost-tbody" >
			    <c:set var="style_values" scope="session" value="'#costtab a'"/>
				<c:set var="data_values" scope="session" value="${jv_stats_cost}" />
				<c:import url="rank_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>
			<% /*  <thead>
				<th>Index / Survey</th>
				<th>Trend</th>
				<th>Value</th>
				<th>Year</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Cost-tbody">
			    <c:set var="style_values" scope="session" value="'#costtab a'"/>
				<c:set var="data_values" scope="session" value="${jv_stats_cost}" />
				<c:import url="simple_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>*/%>
		</table>
	</div>
	<div class="tab-pane fade" id="economy">
		<table class="table table-hover" style="font-size: 13px;" >
			<thead>
				<th>Index / Survey</th>
				<th>Ranking / Data</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Economy-tbody" >
			    <c:set var="style_values" scope="session" value="'#economytab a'"/>
				<c:set var="data_values" scope="session" value="${jv_stats_economy}" />
				<c:import url="rank_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>
			<% /*<thead>
				<th>Index / Survey</th>
				<th>Trend</th>
				<th>Value</th>
				<th>Year</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Economy-tbody">
			    <c:set var="style_values" scope="session" value="'#economytab a'"/>
				<c:set var="data_values" scope="session" value="${jv_stats_economy}" />
				<c:import url="simple_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>*/%>
		</table>
	</div>
	
</div>
</div>
<div id="multiSelecter" style="position: relative; left: 0px; top: 0px; display: none;" class="col-md-12">
<p class="lead">
	<button class="btn btn-info" onclick="as.toggleMultiSelect(0,0);">
		<h6>
			<span class="glyphicon glyphicon-circle-arrow-left"></span> Back
		</h6>
	</button>
	&nbsp;&nbsp;&nbsp;&nbsp; Select States to Compare
</p>
<table>
	<tr>
		<td>
			<div class="btn-group" data-toggle="buttons">
				<table class="table" align="center">
					<c:forEach items="${jv_peer_states}" var="stat">
						<tr>
							<c:forEach items="${stat.row}" var="row">
								<c:choose>
								<c:when test="${row.abbr == jv_current_state}">
									<td><label class="activestatelabel multiselectbutton">
											<c:out value="${row.name}"/>
									</label></td>
								</c:when>
								<c:otherwise>
									<td><label class="statebutton multiselectbutton"  id="chk${fn:escapeXml(row.abbr)}" >
											 <c:out value="${row.name}"></c:out>
									</label></td>
								</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</div>
		</td>
		<td valign="top">
			<button class="btn btn-info" onClick="as.SelectUnSelectAllTabs(1)" id="selectall" disabled="true">Select All</button>
			<button class="btn btn-info" onClick="as.SelectUnSelectAllTabs(2)" id="unselectall" disabled="true">Unselect All</button>
			<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
			<button data-toggle="modal" data-target="#myModal" class="btn btn-info" onClick="as.showMultiGraphOnSelected()">Compare</button>
			</td>
		</tr>
	</table>
</div>

<div id="multiSelecterMetricState" style="position: relative; left: 0px; top: 0px; display: none;" class="col-md-12">
<p class="lead">
	<button class="btn btn-info" onclick="as.toggleMultiSelect(0,-1);">
		<h6>
			<span class="glyphicon glyphicon-circle-arrow-left"></span> Back
		</h6>
	</button>
	&nbsp;&nbsp;&nbsp;&nbsp; Select States to Compare
</p>
<table>
	<tr>
		<td valign="top" nowrap="true" align="left">
			<table>
				<tr>
					<td valign="top" nowrap="true" align="left">
						<button class="btn btn-info" onClick="as.SelectUnSelectAllTabs(3)" id="selectallmultiplemetric"  disabled="true">Select All States</button>
						<button class="btn btn-info" onClick="as.SelectUnSelectAllTabs(4)" id="unselectallmultiplemetric" disabled="true">Unselect All States</button>
					</td>
					
					
				
				</tr>
				
			</table></td>
			<td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;</td>
			<td valign="top" nowrap="true" align="right">
				<button data-toggle="modal" data-target="#myModal" class="btn btn-info" onClick="as.showMultiGraphOnSelected()">Compare</button>
			</td>
	</tr>
	<tr>
		<td valign="top" nowrap="true" align="left">		
			<div >
				<table align="center">
				    <tr><td>Metrics (National, Talent,Cost, Economy)</td></tr>
					<tr>
						<td valign="top" align="left">
							<select  id='multiplemetrics' name="multiplemetrics" multiple="multiple" size=130 style='height:400px; width:340px;'>
 				 				<c:forEach items="${jv_stats_national}" var="stat">
       			 					<option value="${stat.metric.id}"}>${stat.metric.name}</option>
    							</c:forEach>
    							<c:forEach items="${jv_stats_talent}" var="stat">
       			 					<option value="${stat.metric.id}"}>${stat.metric.name}</option>
    							</c:forEach>
    							<c:forEach items="${jv_stats_cost}" var="stat">
       			 					<option value="${stat.metric.id}"}>${stat.metric.name}</option>
    							</c:forEach>
    							<c:forEach items="${jv_stats_economy}" var="stat">
       			 					<option value="${stat.metric.id}"}>${stat.metric.name}</option>
    							</c:forEach>
							</select>
			       		</td>
			     </tr>
				</table>
			</div>
		</td>
		<td>&nbsp;&nbsp;</td>
		<td valign="top" align="left">
			<div >
			<table>
				<tr><td>Selected metrics to compare</td></tr>
				<tr><td  valign="top"  align="left">
					<select  id='selectedmultiplemetrics' multiple="multiple" size=130 style='height:400px; width:340px;' disabled="true" ></select>
				</td></tr>
			</table>
			</div>
		</td>
		<td>&nbsp;&nbsp;</td>
		<td valign="top" align="left">
			<table>
				<tr>
					<td valign="top" nowrap="true" align="left">Select year</td>
				</tr>
				<tr>
					<td valign="top" nowrap="true" align="left">
					<select  id='yearmultiplemetric' name="yearmultiplemetric" multiple="multiple" size="10" style='width:70px;'>
						<%
						GregorianCalendar cal = new GregorianCalendar();
						int current_year=cal.get(Calendar.YEAR);
						int bottom_year=cal.get(Calendar.YEAR)-20;
						for(int i=current_year;i>=bottom_year;i--){
							%><option value="<%=i %>"><%=i %></option><%
								
						}
						%>
						
					
					</select>
					</td>
				</tr>
				
				<% /**<tr>
					<td valign="middle" nowrap="true" align="left"><button data-toggle="modal" data-target="#myModal" class="btn btn-info" onClick="as.showMultiGraphOnSelected()">Compare</button></td>
				</tr> */%>
			</table>
		</td>
		</tr>
		<tr><td>&nbsp;&nbsp;</td></tr>	<tr><td>&nbsp;&nbsp;</td></tr>	<tr><td>&nbsp;&nbsp;</td></tr>
	</table>
	
</div>
