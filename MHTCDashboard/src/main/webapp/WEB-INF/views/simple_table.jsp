
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach items="${data_values}" var="stat">
	<tr>
		<td><c:out value="${stat.metric.desc}"></c:out></td>
		<td><span class="trend_icon trend_${stat.trend}"></span></td>

		<c:set var="stat_value" scope="request" value="${stat.recent.value}" />
		<c:set var="stat_type" scope="request" value="${stat.metric.type}" />
		<td><c:import url="format_value.jsp" /></td>
		<c:remove var="stat_value" scope="request" />
		<c:remove var="stat_type" scope="request" />

		<td><c:out value="${stat.recent.year}" /></td>
		<c:set var="data_value" scope="request" value="${stat}" />
		<c:import url="data_row_end.jsp" />
		<c:remove var="data_value" scope="request" />
	</tr>
</c:forEach>
