<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page
	import="java.io.*, java.util.*, domain.Product"
	language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
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
			<%
				Product product = (Product)request.getAttribute("product");
				request.setAttribute("product", product);
			%>
			<div class="header" style="display: flex; flex: 0.1; flex-direction: row; background-image: linear-gradient(crimson, orangered); width: 100%;">
				<div style="display: flex; flex: 1; align-items: center; justify-content: center;">
					<form action="login" method="get" style="display: flex; flex: 1; align-items: center; justify-content: center;">
						<button type="submit" style="background-color: tomato; color: white; border-radius: 8px; border: none; padding: 16px; height: 50px; width: 80%;">Login</button>
					</form>
				</div>
				<div style="display: flex; flex: 8; width: 100%; text-align: center; align-items: center;">
					<h1 style="width: 100%; color: white;">${product.getName()}</h1>
				</div>
				<div style="display: flex; flex: 1; align-items: center; justify-content: center;">
					<form action="cart" method="get" style="display: flex; flex: 1; align-items: center; justify-content: center;">
						<button type="submit" style="background-color: tomato; color: white; border-radius: 8px; border: none; padding: 16px; height: 50px; width: 80%;">My Cart</button>
					</form>
				</div>
			</div>
			
			<div class="content" style="display: flex; flex: 0.9; flex-direction: column; overflow-y: auto; align-items: center;">
				<div style="display: flex; flex: 4; padding: 8px; background-color: gainsboro; margin: 8px 0; border-radius: 8px; height: 160px; width: 98%; align-items: center; justify-content: center;">
					<img src="${product.getImage()}" style="max-width: 160px; max-height: 160px; border-radius: 8px;"/>
				</div>
				<div style="display: flex; flex: 4.5; flex-direction: column; padding: 8px; background-color: gainsboro; margin: 0 0 8px 0; border-radius: 8px; width: 98%; align-items: center; justify-content: center;">
					<table style="text-align: center; border: 2px solid crimson; width: 100%;">
						<tr><td>Name</td><td>${product.getName()}</td></tr>
						<tr><td>Brand</td><td>${product.getBrand()}</td></tr>
						<tr><td>Category</td><td>${product.getCategory()}</td></tr>
						<tr><td>Remaining</td><td>${product.getStockNumber()}</td></tr>
						<tr><td>Price</td><td>${product.getPrice()}</td></tr>
					</table>
					<p>Description</p>
				</div>
				<div style="display: flex; flex: 1.5; padding: 8px; background-color: gainsboro; margin: 0 0 8px 0; border-radius: 8px; width: 98%; align-items: center; justify-content: center;">
					<button type="submit" value="${product.getProductID()}" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 80%;">Add to Cart</button>
				</div>
			</div>
		</div>
	</body>
</html>
