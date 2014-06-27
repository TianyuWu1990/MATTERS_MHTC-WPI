<!-- 
	Displays the last two columns of a row used in the data table, the source and selection button columns
	Requires input parameter named "data_value" of type edu.wpi.mhtc.dashboard.model.data.DataSource
 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<td><a href="http://${fn:escapeXml(data_value.metric.urlFrom)}" target="_blank"><c:out value="${data_value.metric.sourceName}"></c:out></a></td>
<td>
	<div class="btn-group btn-group-sm">
		<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
			<span class="glyphicon glyphicon-chevron-down"></span>
		</button>
		<ul class="dropdown-menu" role="menu">
			<li><a href="#" data-toggle="modal" data-target="#myModal" onClick="as.showMultiGraphOnPeers(${fn:escapeXml(data_value.metric.id)})">Compare Peer States</a></li>
			<li><a href="#" onClick="as.toggleMultiSelect(${fn:escapeXml(data_value.metric.id)});">Compare to Select States</a></li>
			<li><a href="#" data-toggle="modal" data-target="#myModal" onClick="as.showMultiGraphOnTopTen(${fn:escapeXml(data_value.metric.id)})">Compare Top Ten States</a></li>
			<li><a href="#" data-toggle="modal" data-target="#myModal" onClick="as.showMultiGraphOnBottomTen(${fn:escapeXml(data_value.metric.id)})">Compare Bottom Ten States</a></li>
			<li><a data-toggle="modal" data-target="#myModal" onClick="as.showGraph(${fn:escapeXml(data_value.metric.id)})">Display Selected State Data</a></li>
			<li class="divider"></li>
		</ul>
	</div>
</td>
