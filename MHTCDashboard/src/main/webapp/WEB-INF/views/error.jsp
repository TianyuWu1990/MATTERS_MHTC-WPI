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
			<td>${exception.message}</td>
		</tr>
		<tr valign="top">
			<td><b>URI:</b></td>
			<td>${url}</td>
		</tr>
		<tr valign="top">
			<td><b>Status code:</b></td>
			<td></td>
		</tr>
		<tr valign="top">
			<td><b>Stack trace:</b></td>
			<td><c:forEach items="${exception.stackTrace}" var="trace">
					<p>${trace}</p>
				</c:forEach>
			</td>
		</tr>
	</table>
</body>
</html>