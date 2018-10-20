<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page
	import="java.io.*, java.util.*, domain.User, auth.AppUtils"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User</title>
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
			<%
				User user = (User) request.getAttribute("user");
			%>
        <div style="display: flex; flex: 3; flex-direction: column; background-color: white; margin: 0.5%; border-radius: 1%; height: 98%; align-items: center;">
          <div style="display: flex; flex: 6; flex-direction: column; background-color: white; margin: 0 0.5% 0.5% 0.5%; border-radius: 4px; width: 98%; align-items: center;">
						<img src="${user.getAvatar()}" style="max-width: 160px; max-height: 160px; border-radius: 8px;"/>
						<p>${user.getUsername()}</p>
						<p>${user.getType()}</p>
					</div>
				<form action="login" method="get" style="display: flex; flex: 1; flex-direction: column; background-color: white; margin: 0 0.5% 0.5% 0.5%; border-radius: 4px; width: 98%; align-items: center; justify-content: center;">
            		<button type="submit" name="command" value="login delete" style="color: white; background-color: black; border: none; border-radius: 8px; padding: 2%; height: 80%; width: 98%;">Logout</button>
  				</form>
        </div>

        <form action="user" method="get" style="display: flex; flex: 5; flex-direction: column; background-color: white; margin: 0.5%; border-radius: 4px; height: 98%; overflow-x: hidden; overflow-y: auto;">
        	<div style="display: flex; flex: 6; flex-direction: column; align-items: left;">
	        	<p>My details</p>
				<p>First Name: </p>
				<input name="first_name" value="${user.getFirstName()}" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
				<p>Last Name: </p>
				<input name="last_name" value="${user.getLastName()}" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
				<p>Email Address: </p>
				<input name="email" value="${user.getEmail()}" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
				<%if(AppUtils.isValidCustomer()){ %>
				<p>Address: </p>
				<input name="address" value="${user.getAddress()}" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
				<%} %>
			</div>
			<div style="display: flex; flex: 1; flex-direction: column; align-items: center; justify-content: center;">
				<button type="submit" name="command" value="user put" style="color: white; background-color: black; border: none; border-radius: 8px; padding: 2%; height: 80%; width: 98%;">Update Details</button>
			</div>
        </form>

			</div>
		</div>
	</body>
</html>
