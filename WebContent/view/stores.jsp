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
		<title>Stores</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	</head>
	<body >
		<div class="container" style="display: flex; flex: 1; flex-direction: column; position: absolute; margin: 0; padding: 0; top: 0; bottom: 0; left: 0; right: 0; background-color: white; width: 100%; heigh: 100%; overflow-y: hidden; overflow-x: hidden;">
			<%
				List stores = (List) request.getAttribute("stores");
			%>

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
  				<div style="display: flex; flex: 6; flex-direction: column; background-color: white; margin: 0.5%; border-radius: 4px; width: 98%;">
            <div style="display: flex; flex: 1; flex-direction: column; margin: 0 0.5% 0.5% 0.5%; overflow-x: hidden; overflow-y: auto;">
              <div>
	              <c:forEach items="${stores}" var="store">
						<form action="store" method="get" class="store card" style="text-align: center; background-color: gainsboro; border-radius: 8px; padding: 8px; margin: 8px; width: 20%; display: inline-block;">
							<input type="hidden" name="store" value="${store}"/>
							<button type="submit" name="command" value="store get" style="border: none; border-radius: 8px; padding: 4px; width: 100%;">
								<div style="height: 160px; width: 100%; align-items: center; justify-content: center;">
			 						<img src="https://image.flaticon.com/icons/svg/123/123403.svg" style="max-width: 160px; max-height: 160px; border-radius: 8px;"/>
			 					</div>
			 					<p>${store}</p>
							</button>
						</form>
					</c:forEach>
				</div>
            </div>
  				</div>
        </div>

			</div>
		</div>
	</body>
</html>
