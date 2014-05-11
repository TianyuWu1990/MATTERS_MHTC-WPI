<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<ul class="nav nav-tabs">
	<li id="nationalTab"><a href="#national" data-toggle="tab">National Rankings</a></li>
	<li id="talentTab"><a href="#talent" data-toggle="tab">Talent Metrics</a></li>
	<li id="costTab"><a href="#cost" data-toggle="tab">Cost Metrics</a></li>
	<li id="economyTab"><a href="#economy" data-toggle="tab">Economy Metrics</a></li>
</ul>

<!-- Tab panes -->
<div class="tab-content">

	<div class="tab-pane fade" id="national">
		<table class="table" style="font-size: 13px;">
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
		<table class="table" style="font-size: 13px;">
			<thead>
				<th>Index / Survey</th>
				<th>Trend</th>
				<th>Value</th>
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