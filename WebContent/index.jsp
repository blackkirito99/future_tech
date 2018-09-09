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
		<title>Home</title>
		<link rel='stylesheet' href='resources/bootstrap.min.css'/>
	</head>
	<body >
		<div class="container" style="display: flex; flex: 1; flex-direction: column; position: absolute; margin: 0; padding: 0; top: 0; bottom: 0; left: 0; right: 0; background-color: white; width: 100%; heigh: 100%; overflow-y: hidden; overflow-x: hidden;">
		
			<div class="header" style="display: flex; flex: 0.1; flex-direction: row; background-image: linear-gradient(crimson, orangered);">
				<div style="display: flex; flex: 1;">
					<form action="login" method="get" style="display: flex; flex: 1; align-items: center; justify-content: center;">
						<button type="submit" style="background-color: tomato; color: white; border-radius: 8px; border: none; padding: 16px; height: 50px; width: 80%;">Login</button>
					</form>
				</div>
				<div style="display: flex; flex: 8; width: 100%; text-align: center; align-items: center;">
					<h1 style="width: 100%; color: white;">Home</h1>
				</div>
				<div style="display: flex; flex: 1;"></div>
			</div>
			
			<div class="content" style="display: flex; flex: 0.9; flex-direction: column; overflow-y: auto; background-image: linear-gradient(orangered, orange); align-items: center; justify-content: center;">
				<br>
				<img src="https://cdn1.iconfinder.com/data/icons/social-productivity-line-art-4/128/shopping-cart2-512.png" style="width: 240px; height: 240px;"/>
				<h1 style="color: white;">Future Tech</h1>
				<h3 style="color: white;">Online Enterprise Retail Platform</h3>
				<br>
				<form action="products" method="get" style="align-items: center; justify-content: center;"><button type="submit" name="products" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 99%;">All Products</button></form>
				<br>
				<form action="about" method="get" style="align-items: center; justify-content: center;"><button type="submit" name="about" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 99%;">About</button></form>
			</div>
			
		</div>
	</body>
</html>
