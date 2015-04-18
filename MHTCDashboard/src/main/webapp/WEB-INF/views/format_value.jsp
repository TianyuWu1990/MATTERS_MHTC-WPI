

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
	<c:when test="${stat_type == 'rank'}">
		<fmt:formatNumber value="${stat_value}" type="number" maxFractionDigits="0" />
	</c:when>
	<c:when test="${stat_type == 'integer'}">
		<fmt:formatNumber value="${stat_value}" type="number" maxFractionDigits="0" />
	</c:when>
	<c:when test="${stat_type == 'currency'}">
		<fmt:formatNumber value="${stat_value}" type="currency" maxFractionDigits="2" />
	</c:when>
	<c:when test="${stat_type == 'percentage'}">
		<fmt:formatNumber value="${stat_value}" type="percent" maxFractionDigits="2" />
	</c:when>
	<c:otherwise>
		<c:out value="${stat_value}" />
	</c:otherwise>
</c:choose>