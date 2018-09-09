<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page
	import="java.io.*, java.util.*, domain.User"
	language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>About</title>
		<link rel='stylesheet' href='resources/bootstrap.min.css'/>
	</head>
	<body >
		<div class="container" style="display: flex; flex: 1; flex-direction: column; position: absolute; margin: 0; padding: 0; top: 0; bottom: 0; left: 0; right: 0; background-color: white; width: 100%; heigh: 100%; overflow-y: hidden; overflow-x: hidden;">

			<div class="header" style="display: flex; flex: 0.1; flex-direction: row; background-image: linear-gradient(crimson, orangered); width: 100%;">
				<div style="display: flex; flex: 1; align-items: center; justify-content: center;">

				</div>
				<div style="display: flex; flex: 8; width: 100%; text-align: center; align-items: center;">
					<h1 style="width: 100%; color: white;">About</h1>
				</div>
				<div style="display: flex; flex: 1; align-items: center; justify-content: center;">

				</div>
			</div>
			
			<div class="content" style="display: flex; flex: 0.9; flex-direction: column; overflow-y: auto; align-items: center;">
				<div style="display: flex; flex: 1; flex-direction: column; padding: 8px; background-color: gainsboro; margin: 8px 0; border-radius: 8px; height: 160px; width: 98%; align-items: center; justify-content: center;">
					<h2>SWEN90007 Software Design and Architecture</h2>
					<h2>2018 Semester 2</h2>
					<h3>Team 28</h3>
					<h4>Yuwei Bao | Ivan Ken Weng Chee</h4>
				</div>
			</div>
		</div>
	</body>
</html>