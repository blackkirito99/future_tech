<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page
	import="java.io.*, java.util.*, datasource.ProductLockingMapper, domain.CartItem, domain.Product, domain.ShoppingCart, datasource.UnitOfWork"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cart</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	</head>
	<body >
		<div class="container" style="display: flex; flex: 1; flex-direction: column; position: absolute; margin: 0; padding: 0; top: 0; bottom: 0; left: 0; right: 0; background-color: white; width: 100%; heigh: 100%; overflow-y: hidden; overflow-x: hidden;">

			<div class="header" style="display: flex; flex: 0.1; flex-direction: row; background-color: black; width: 100%;">
        <form action="index" method="get" style="display: flex; flex: 3; margin: 0.5%; align-items: center; justify-content: center;">
          <button type="submit" style="display: flex; flex: 1; background-color: black; border: none;">
          	<img src="images/FutureTech.png" alt="logo" style="max-width: 100%; max-height: 100%;"/>
          </button>
        </form>
        <form action="search" method="get" style="display: flex; flex: 15; margin: 0.5%; align-items: center; justify-content: center;">
          <input name="query" placeholder="Search for a product" style="margin: 0.5% 0 0.5% 0.5%; padding: 0.5%; border: none; border-radius: 4px 0 0 4px; width: 96%; height: 70%;"/>
		  <button type="submit" name="command" value="search post" style="margin: 0.5% 0.5% 0.5% 0; background-color: white; border: none; border-radius: 0 4px 4px 0; width: 4%; height: 70%;">
           	<i class="glyphicon glyphicon-search"></i>
          </button>
        </form>
        <div style="display: flex; flex: 2; flex-direction: row; margin: 0.5%; align-items: center; justify-content: center;">
        	<form action="login" method="get" style="display: flex; flex: 1; height: 100%; align-items: center; justify-content: center;">
	          <button type="submit" name="command" value="login get" style="margin: 0.5%; background-color: white; border: none; border-radius: 4px; width: 98%; height: 70%;">
	            <i class="glyphicon glyphicon-user"></i>
	          </button>
	        </form>
	        <form action="cart" method="get" style="display: flex; flex: 1; height: 100%; align-items: center; justify-content: center;">
	          <button type="submit" name="command" value="cart get" style="margin: 0.5%; background-color: white; border: none; border-radius: 4px; width: 98%; height: 70%;">
	            <i class="glyphicon glyphicon-shopping-cart"></i>
	          </button>
        	</form>
		</div>
			</div>

			<div class="content" style="display: flex; flex: 0.9; flex-direction: row; background-color: black; align-items: center;">
        <div style="display: flex; flex: 5; flex-direction: column; margin: 0; height: 100%;">
  				<div style="display: flex; flex: 1; flex-direction: column; background-color: white; margin: 0.5%; border-radius: 4px; width: 98%;">
            <div style="display: flex; flex: 9; flex-direction: column; margin: 0 0.5% 0.5% 0.5%; overflow-x: hidden; overflow-y: auto;">
              <div>
              <%
					List<CartItem>cartItems = (List<CartItem>)request.getAttribute("cartItems");
					request.setAttribute("cartItems", cartItems);
					UnitOfWork.getCurrent();
				%>
              <c:forEach items="${cartItems}" var="cartItem">
								<div class="cart item card" style="display: flex; flex-direction: row; text-align: center; background-color: gainsboro; border-radius: 8px; padding: 8px; margin: 8px; width: 98%;">
									<div style="display: flex; flex: 2; height: 160px; align-items: center; justify-content: center;">
										<img src='${ProductLockingMapper.getInstance().find(Integer.toString(cartItem.getProductID()), "").getImage()}' style="max-width: 160px; max-height: 160px; border-radius: 8px;"/>
									</div>
									<div style="display: flex; flex: 8; flex-direction: column; justify-content: center; text-align: left;">
										<p>Name: ${ProductLockingMapper.getInstance().find(cartItem.getProductID()).getName()}</p>
										<p>Quantity: ${cartItem.getQuantity()}</p>
										<p>Price per Item: ${ProductLockingMapper.getInstance().find(cartItem.getProductID()).getPrice()}</p>
										<p>Total price : ${ProductLockingMapper.getInstance().find(cartItem.getProductID()).getPrice() * cartItem.getQuantity()}</p>
									</div>
									<div style="display: flex; flex: 2; flex-direction: column; justify-content: center;">
										<form style="display: flex; flex: 1; margin: 2%;" action="cart" method="get">
											<input type="hidden" name="id" value="${cartItem.getProductID()}"/>
											<button type="submit" name="command" value="cart put" style="background-color: white; border: none; border-radius: 8px; padding: 4px; width: 80%;">+</button>
										</form>
										<form style="display: flex; flex: 1; margin: 2%;" action="cart" method="get">
											<input type="hidden" name="id" value="${cartItem.getProductID()}"/>
											<button type="submit" name="command" value="cart delete" style="background-color: white; border: none; border-radius: 8px; padding: 4px; width: 80%;">-</button>
										</form>
										<form style="display: flex; flex: 1; margin: 2%;" action="product" method="get">
											<input type="hidden" name="id" value="${cartItem.getProductID()}"/>
											<button type="submit" name="command" value="product get" style="background-color: white; border: none; border-radius: 8px; padding: 4px; width: 80%;">View Details</button>
										</form>
									</div>
								</div>
              </c:forEach>
              </div>
            </div>
            <form style="display: flex; flex: 1; align-items: center; justify-content: center;" action="checkout" method="get">
				<button type="submit" name="command" value="checkout get" style="color: white; background-color: black; border: none; border-radius: 8px; padding: 2px; height: 80%; width: 98%; margin: 0.5%;">Checkout</button>
			</form>
  				</div>
        </div>

			</div>
		</div>
	</body>
</html>
