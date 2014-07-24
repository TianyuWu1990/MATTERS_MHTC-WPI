<!-- 
	Displays a tab data table of rank type.
	Requires input parameter named "data_values" of a list of type edu.wpi.mhtc.dashboard.model.data.DataSource
 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach items="${data_values}" var="stat">
	<tr>
		<td><c:out value="${stat.metric.displayName}"></c:out></td>
		<td>
			<table class="table table-condensed" style="font-size: 13px;">
				<tr>
					<td>Rank</td>
					<c:forEach items="${stat.dataPoints}" var="data">

						<c:set var="stat_value" scope="request" value="${data.value}" />
						<c:set var="stat_type" scope="request" value="${stat.metric.type}" />
						<td><c:import url="format_value.jsp" /></td>
						<c:remove var="stat_value" scope="request" />
						<c:remove var="stat_type" scope="request" />

					</c:forEach>
				</tr>
				<tr>
					<td>Year</td>
					<c:forEach items="${stat.dataPoints}" var="data">
						<td><c:out value="${data.year}"></c:out></td>
					</c:forEach>
				</tr>
			</table>
		</td>
		<c:set var="data_value" scope="request" value="${stat}" />
		<c:import url="data_row_end.jsp" />
		<c:remove var="data_value" scope="request" />
	</tr>
</c:forEach>