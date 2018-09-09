<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page
	import="java.io.*, java.util.*, datasource.ProductMapper, domain.CartItem, domain.Product, domain.ShoppingCart, datasource.CartItemUnitOfWork"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Shopping Cart</title>
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
					<h1 style="width: 100%; color: white;">Shopping Cart</h1>
				</div>
				<div style="display: flex; flex: 1; align-items: center; justify-content: center;">
					
				</div>
			</div>

			<div class="content" style="display: flex; flex: 0.8; flex-direction: column; overflow-y: auto;">
				<%
					List<CartItem>cartItems = (List<CartItem>)request.getAttribute("cartItems");
					request.setAttribute("cartItems", cartItems);
					CartItemUnitOfWork.getCurrent();
				%>
				<c:forEach items="${cartItems}" var="cartItem">
					<div class="cart item card" style="display: flex; flex: 1; flex-direction: row; background-color: gainsboro; border-radius: 8px; padding: 8px; margin: 8px; width: 99%;">
						<div style="display: flex; flex: 1; align-items: center; justify-content: center;">
							<img src="${ProductMapper.getProduct(cartItem.getProductID()).getImage()}" style="max-width: 72px; max-height: 72px; border-radius: 8px;"/>
						</div>
						<div style="display: flex; flex: 7; flex-direction: column; justify-content: center;">
							<h4>${ProductMapper.getProduct(cartItem.getProductID()).getName()}</h4>
							<h4>${cartItem.getQuantity()}</h4>
						</div>
						<div style="display: flex; flex: 2; flex-direction: row; align-items: center; justify-content: center;">
							<form action="cart" method="post" style="display: flex; flex: 1;"><button type="submit" name="delete" value="${cartItem.getProductID()}" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 90%;">-</button></form>
							<form action="cart" method="post" style="display: flex; flex: 1;"><button type="submit" name="put" value="${cartItem.getProductID()}" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 90%;">+</button></form>
						</div>
					</div>
				</c:forEach>
			</div>

			<div class="footer" style="display: flex; flex: 0.1; flex-direction: row; background-image: linear-gradient(crimson, orangered); width: 100%;">
				<form action="checkout" method="get" style="display: flex; flex: 1; padding: 8px; align-items: center; justify-content: center;"><button type="submit" name="checkout" value="${cartItem.getProductID()}" style="color: white; background-color: tomato; border: none; border-radius: 8px; padding: 16px; width: 99%;">Checkout</button></form>
			</div>
		</div>
	</body>
</html>
