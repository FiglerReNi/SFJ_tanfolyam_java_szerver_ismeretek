<!DOCTYPE html>
<html>
	<head>		
		<meta charset="UTF-8">
		<title>Index JSP oldalunk</title>
	</head>
	<body>
	 Ez egy igazi JSP!
	 <%@ page contentType="text/html; charset=UTF-8" %>
	 <% out.println("Ez itt az IP címed: " + request.getRemoteAddr()); %>
	</body>
</html>