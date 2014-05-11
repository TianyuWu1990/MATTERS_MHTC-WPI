<!-- 
	Displays the last two columns of a row used in the data table, the source and selection button columns
	Requires input parameter named "data_value" of type edu.wpi.mhtc.dashboard.model.data.DataSource
 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<td><a href="http://${fn:escapeXml(data_value.urlFrom)}" target="_blank"><c:out value="${data_value.sourceName}"></c:out></a></td>
<td>
	<div class="btn-group btn-group-sm">
		<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
			<span class="glyphicon glyphicon-chevron-down"></span>
		</button>
		<ul class="dropdown-menu" role="menu">
			<li><a href="#" data-toggle="modal" data-target="#myModal" onClick="showMultiGraphOnPeers(${fn:escapeXml(data_value.id)})">Compare to Peer States</a></li>
			<li><a href="#" onClick="toggleMultiSelect(${fn:escapeXml(data_value.id)});">Compare to Select States</a></li>
			<li><a href="#" data-toggle="modal" data-target="#myModal" onClick="showMultiGraphOnTopTen(${fn:escapeXml(data_value.id)})">Compare to Top Ten</a></li>
			<li><a href="#" data-toggle="modal" data-target="#myModal" onClick="showMultiGraphOnBottomTen(${fn:escapeXml(data_value.id)})">Compare to Bottom Ten</a></li>
			<li><a data-toggle="modal" data-target="#myModal" onClick="showGraph(${fn:escapeXml(data_value.id)})">View Graph</a></li>
			<li class="divider"></li>
		</ul>
	</div>
</td>
