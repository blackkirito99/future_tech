<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page
	import="java.io.*, java.util.*"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Users</title>
		<link rel='stylesheet' href='resources/bootstrap.min.css'/>
	</head>
	<body >
		<div class="container" style="display: flex; flex: 1; flex-direction: column; position: absolute; margin: 0; padding: 0; top: 0; bottom: 0; left: 0; right: 0; background-color: white; width: 100%; heigh: 100%; overflow-y: hidden; overflow-x: hidden;">

			<div class="header" style="display: flex; flex: 0.1; flex-direction: row; background-image: linear-gradient(crimson, orangered);">
				<div style="display: flex; flex: 1;"></div>
				<div style="display: flex; flex: 8; width: 100%; text-align: center; align-items: center;">
					<h1 style="width: 100%; color: white;">Users</h1>
				</div>
				<div style="display: flex; flex: 1;"></div>
			</div>

			<div class="content" style="display: flex; flex: 0.9; flex-direction: column; overflow-y: auto;">
				<div>
					<%
					List users = (List)request.getAttribute("users");
					request.setAttribute("users", users);
					%>
				<c:forEach items="${users}" var="user">
					<div class="user card" style="text-align: center; background-color: gainsboro; border-radius: 8px; padding: 8px; margin: 8px; width: 23.5%; display: inline-block;">
						<div style="height: 160px; width: 100%; align-items: center; justify-content: center;">
							<img src="${user.getAvatar()}" style="max-width: 160px; max-height: 160px; border-radius: 8px;"/>
						</div>
						<h3>${user.getFirstName().concat(user.getLastName())}</h3>
						<h5>${user.getEmail()}</h5>
						<form action="user" method="get"><button type="submit" name="id" value="${user.getUserID()}" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 80%;">View Details</button></form>
					</div>
				</c:forEach>
				</div>
			</div>
		</div>
	</body>
</html>
