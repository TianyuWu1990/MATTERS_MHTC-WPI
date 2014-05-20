<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="tbl">
   <tr>
      <th>Id</th>
      <th>Metric Name</th>
      <th>Visible</th>
      <th>Calculated</th>
      <th>Type</th>
      <th></th>
   </tr>
	<c:forEach items="${jv_metrics}" var="metric">
	<tr>
		<td>${metric.id}</td>
		<td id="editmetricname_${metric.id}">${metric.name}</td>
		<td id="editmetricvisible_${metric.id}">${metric.visible}</td>
		<td id="editmetriccalculated_${metric.id}">${metric.calculated}</td>
		<td id="editmetrictype_{id}">${metric.id}</td>
		<td><a href="#" id="editmetric_${metric.id}" class="editCategCLS">Edit</a></td>
	</tr>
	</c:forEach>
</table>