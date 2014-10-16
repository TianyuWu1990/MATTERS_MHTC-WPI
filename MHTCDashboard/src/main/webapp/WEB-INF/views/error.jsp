<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<!DOCTYPE html>

<head>
<title>Error!</title>
</head>
<body>
	<h1>Oops...</h1>
	<table width="100%" border="1">
		<tr valign="top">
			<td width="40%"><b>Error:</b></td>
			<td>${e.message}</td>
		</tr>
		<tr valign="top">
			<td><b>URI:</b></td>
			<td>${e.errorData.requestURI}</td>
		</tr>
		<tr valign="top">
			<td><b>Status code:</b></td>
			<td>${e.statusCode}</td>
		</tr>
		<tr valign="top">
			<td><b>Stack trace:</b></td>
			<td><c:forEach var="trace"
					items="${e.stackTrace}">
					<p>${trace}</p>
				</c:forEach></td>
		</tr>
	</table>
</body>
</html>