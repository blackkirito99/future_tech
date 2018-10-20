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
        <div style="display: flex; flex: 15; margin: 0.5%; align-items: center; justify-content: center;">
          
        </div>
			</div>

			<div class="content" style="display: flex; flex: 0.9; flex-direction: row; background-color: black; align-items: center;">

        <form action="register" method="get" style="display: flex; flex: 1; flex-direction: column; background-color: white; margin: 0.5%; border-radius: 4px; height: 98%;">
					<div style="display: flex; flex: 7; flex-direction: column; align-items: center; overflow-x: hidden; overflow-y: auto;">
						<img src="http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-up-icon.png" style="max-width: 160px; max-height: 160px; border-radius: 8px;"/>
						<p>Register</p>
						<input name="username" placeholder="username" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
						<input name="password" placeholder="password" type="password" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
						<select name="type" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;">
							<option value="CU">Customer</option>
							<option value="RT">Retailer</option>
						</select>
						<input name="first_name" placeholder="first name" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
						<input name="last_name" placeholder="last name" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
						<input name="email_address" placeholder="email address" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
						<input name="address" placeholder="address" style="margin: 0.5%; padding: 0.5%; border-radius: 4px; width: 98%;"/>
					</div>
					<div style="display: flex; flex: 1; align-items: center; justify-content: center;">
						<button type="submit" name="command" value="register post" style="color: white; background-color: black; border: none; border-radius: 8px; padding: 2%; height: 80%; width: 98%; margin: 0.5%;">Register</button>
					</div>
        </form>

			</div>
		</div>
	</body>
</html>
