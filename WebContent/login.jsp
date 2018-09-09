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
		<title>Login</title>
		<link rel='stylesheet' href='resources/bootstrap.min.css'/>
	</head>
	<body >
		<div class="container" style="display: flex; flex: 1; flex-direction: column; position: absolute; margin: 0; padding: 0; top: 0; bottom: 0; left: 0; right: 0; background-color: white; width: 100%; heigh: 100%; overflow-y: hidden; overflow-x: hidden;">
		
			<div class="header" style="display: flex; flex: 0.1; flex-direction: row; background-image: linear-gradient(crimson, orangered);">
				<div style="display: flex; flex: 1;"></div>
				<div style="display: flex; flex: 8; width: 100%; text-align: center; align-items: center;">
					<h1 style="width: 100%; color: white;">Login</h1>
				</div>
				<div style="display: flex; flex: 1;"></div>
			</div>
			
			<div class="content" style="display: flex; flex: 0.9; flex-direction: column; overflow-y: auto; align-items: center;">
				<div style="display: flex; flex: 1; flex-direction: column; padding: 8px; background-color: gainsboro; margin: 8px 0; border-radius: 8px; width: 99%; justify-content: center; align-items: center;">
					<input placeholder="username" style="width: 60%; border: 2px solid crimson; border-radius: 8px; padding: 16px;"/>
					<br>
					<input placeholder="password" style="width: 60%; border: 2px solid crimson; border-radius: 8px; padding: 16px;"/>
					<br>
					<form action="users" method="get" style="width: 60%; align-items: center; justify-content: center;">
						<button type="submit" class="button" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 100%;">login</button>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>
