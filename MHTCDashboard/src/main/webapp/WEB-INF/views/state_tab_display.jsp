<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div id="sidebar" class="col-md-12">

<ul class="nav nav-tabs">
	<li id="nationalTab"><a href="#national" data-toggle="tab">National Rankings</a></li>
	<li id="talentTab"><a href="#talent" data-toggle="tab">Talent Metrics</a></li>
	<li id="costTab"><a href="#cost" data-toggle="tab">Cost Metrics</a></li>
	<li id="economyTab"><a href="#economy" data-toggle="tab">Economy Metrics</a></li>
</ul>

<!-- Tab panes -->
<div class="tab-content" >

	<div class="tab-pane fade" id="national">
		<table class="table" style="font-size: 13px;" >
			<thead>
				<th>Index / Survey</th>
				<th>Ranking / Data</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="National-tbody">
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
				<th>Trend</th>
				<th>Value</th>
				<th>Year</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Talent-tbody">
				<c:set var="data_values" scope="session" value="${jv_stats_talent}" />
				<c:import url="simple_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>
		</table>
	</div>
	<div class="tab-pane fade" id="cost">
		<table class="table" style="font-size: 13px;">
			<thead>
				<th>Index / Survey</th>
				<th>Trend</th>
				<th>Value</th>
				<th>Year</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Cost-tbody">
				<c:set var="data_values" scope="session" value="${jv_stats_cost}" />
				<c:import url="simple_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>
		</table>
	</div>
	<div class="tab-pane fade" id="economy">
		<table class="table" style="font-size: 13px;" >
			<thead>
				<th>Index / Survey</th>
				<th>Trend</th>
				<th>Value</th>
				<th>Year</th>
				<th>Source</th>
				<th></th>
			</thead>
			<tbody id="Economy-tbody">
				<c:set var="data_values" scope="session" value="${jv_stats_economy}" />
				<c:import url="simple_table.jsp" />
				<c:remove var="data_values" scope="session" />
			</tbody>
		</table>
	</div>
</div>
</div>
<div id="multiSelecter" style="position: relative; left: 0px; top: 0px; display: none;" class="col-md-12">
<p class="lead">
	<button class="btn btn-info" onclick="as.toggleMultiSelect(0);">
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
									<td><label class="statebutton multiselectbutton"   id="chk${fn:escapeXml(row.abbr)}">
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
		<td>
			<button data-toggle="modal" data-target="#myModal" class="btn btn-info" onClick="as.showMultiGraphOnSelected()">Compare</button>
			</td>
		</tr>
	</table>
</div>