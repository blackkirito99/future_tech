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
		<title>Products</title>
		<link rel='stylesheet' href='resources/bootstrap.min.css'/>
	</head>
	<body >
		<div class="container" style="display: flex; flex: 1; flex-direction: column; position: absolute; margin: 0; padding: 0; top: 0; bottom: 0; left: 0; right: 0; background-color: white; width: 100%; heigh: 100%; overflow-y: hidden; overflow-x: hidden;">
		
			<div class="header" style="display: flex; flex: 0.1; flex-direction: row; background-image: linear-gradient(crimson, orangered); width: 100%;">
				<div style="display: flex; flex: 1; align-items: center; justify-content: center;">
					<form action="login" method="get" style="display: flex; flex: 1; align-items: center; justify-content: center;">
						<button type="submit" style="background-color: tomato; color: white; border-radius: 8px; border: none; padding: 16px; height: 50px; width: 80%;">Login</button>
					</form>
				</div>
				<div style="display: flex; flex: 8; width: 100%; text-align: center; align-items: center;">
					<h1 style="width: 100%; color: white;">Products</h1>
				</div>
				<div style="display: flex; flex: 1; align-items: center; justify-content: center;">
					<form action="cart" method="get" style="display: flex; flex: 1; align-items: center; justify-content: center;">
						<button type="submit" style="background-color: tomato; color: white; border-radius: 8px; border: none; padding: 16px; height: 50px; width: 80%;">My Cart</button>
					</form>
				</div>
			</div>
			
			<div class="content" style="display: flex; flex: 0.9; flex-direction: column; overflow-y: auto;">
				<div>
					<%
					List products = (List)request.getAttribute("products");
					request.setAttribute("products", products);
					%>
				<c:forEach items="${products}" var="product">
					<div class="product card" style="text-align: center; background-color: gainsboro; border-radius: 8px; padding: 8px; margin: 8px; width: 23.5%; display: inline-block;">
						<div style="height: 160px; width: 100%; align-items: center; justify-content: center;">
							<img src="${product.getImage()}" style="max-width: 160px; max-height: 160px; border-radius: 8px;"/>
						</div>
						<h2>"${product.getName()}"</h2>
						<h4>"${product.getCategory()}"</h4>
						<h3>"${product.getPrice()}"</h3>
						<form action="product" method="get"><button type="submit" name="id" value="${product.getProductID()}" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 80%;">View Details</button></form>
						<br>
						<form action="cart" method="post"><button type="submit" name="id" value="${product.getProductID()}" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 80%;">Add to Cart</button></form>
					</div>
				</c:forEach>
				</div>
			</div>
		</div>
	</body>
</html>
