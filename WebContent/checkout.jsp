<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page
	import="java.io.*, java.util.*, domain.Order"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Checkout</title>
		<link rel='stylesheet' href='resources/bootstrap.min.css'/>
	</head>
	<body>
		<div class="container" style="display: flex; flex: 1; flex-direction: column; position: absolute; margin: 0; padding: 0; top: 0; bottom: 0; left: 0; right: 0; background-color: white; width: 100%; heigh: 100%; overflow-y: hidden; overflow-x: hidden;">
			<%
				Order order = (Order)request.getAttribute("order");
				request.setAttribute("order", order);
			%>
			
			<div class="header" style="display: flex; flex: 0.1; flex-direction: row; background-image: linear-gradient(crimson, orangered); width: 100%;">
				<div style="display: flex; flex: 1; align-items: center; justify-content: center;">
					
				</div>
				<div style="display: flex; flex: 8; width: 100%; text-align: center; align-items: center;">
					<h1 style="width: 100%; color: white;">Checkout</h1>
				</div>
				<div style="display: flex; flex: 1; align-items: center; justify-content: center;">
					
				</div>
			</div>
			
			<div class="content" style="display: flex; flex: 0.9; flex-direction: column; overflow-y: auto;">
				<table style="text-align: center; border: 2px solid crimson; width: 100%;">
					<tr><td>Order Number</td><td>${order.getOrderNum()}</td></tr>
					<tr><td>Total Price</td><td>${order.getTotalPrice()}</td></tr>
					<tr><td>Currency</td><td>${order.getCurrency()}</td></tr>
				</table>
			</div>
			
		</div>
	</body>
</html>
