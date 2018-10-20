<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page
	import="java.io.*, java.util.*, auth.AppUtils, domain.Product"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Product</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	</head>
	<body>
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
				
          <% if (AppUtils.isValidRetailer()) { %>
          <form action="new" method="get" class="content" style="display: flex; flex: 0.9; flex-direction: row; background-color: black; overflow-y: auto; align-items: center;">
          <div style="display: flex; flex: 3; flex-direction: column; background-color: white; margin: 0.5%; border-radius: 1%; height: 98%;">
			<div style="display: flex; flex: 16; flex-direction: column; align-items: center;">
		       <img src="https://image.flaticon.com/icons/png/512/32/32360.png" alt="product.image" style="max-width: 50%; max-height: 50%; border-radius: 8px;"/>
		    </div>
          <div style="display: flex; flex: 3; align-items: center; justify-content: center;">
            <button type="submit" name="command" value="product post" style="color: white; background-color: black; border: none; border-radius: 8px; padding: 2%; height: 80%; width: 98%;">Create</button>
          </div>
          </div>

			<div style="display: flex; flex: 5; flex-direction: column; background-color: white; margin: 0.5% 0.5% 0.5% 0; border-radius: 1%; height: 98%;">
	          <div style="display: flex; flex-direction: column; flex: 4; margin: 0.5%;">
	          	<p><strong>Name:</strong></p>
	            <input name="name"/>
	            <p><strong>Image URL:</strong></p>
	            <input name="image"/>
	          	<p><strong>Brand:</strong></p>
	            <input name="brand"/>
	          	<p><strong>Category:</strong></p>
	          	<%
					List categories = (List) request.getAttribute("categories");
				%>
	            <select name="category"/>
	            	<c:forEach items="${categories}" var="category">
						<option value="${category}">${category}</option>
					</c:forEach>
				</select>
	          	<p><strong>Price:</strong></p>
	            <input name="price"/>
	          	<p><strong>Stock:</strong></p>
	          	<input name="stock"/>
	          </div>

			</div>
			</form>
          <% } else if (AppUtils.isValidRetailer()) { %>
          <form action="product" method="get" class="content" style="display: flex; flex: 0.9; flex-direction: row; background-color: black; overflow-y: auto; align-items: center;">
          <div style="display: flex; flex: 3; flex-direction: column; background-color: white; margin: 0.5%; border-radius: 1%; height: 98%;">
					<div style="display: flex; flex: 16; flex-direction: column;">
		            <img src="${product.getImage()}" alt="product.image" style="max-width: 98%; border-radius: 8px;"/>
		          </div>
	          <div style="display: flex; flex: 4; flex-direction: column; align-items: center; justify-content: center;">
	          	<input type="hidden" name="id" value="${product.getProductID()}" style="display: none;"/>
          		<input type="hidden" name="image" value="${product.getImage()}" style="display: none;"/>
	            <button type="submit" name="command" value="product put" style="color: white; background-color: black; border: none; border-radius: 8px; padding: 2%; height: 80%; width: 98%;">Update Product</button>
	            <button type="submit" name="command" value="product delete" style="color: white; background-color: black; border: none; border-radius: 8px; padding: 2%; height: 80%; width: 98%;">Delete Product</button>
	          </div>
	      </div>
	
				<div style="display: flex; flex: 5; flex-direction: column; background-color: white; margin: 0.5% 0.5% 0.5% 0; border-radius: 1%; height: 98%;">
		        	
			        <div style="display: flex; flex: 4; flex-direction: column; margin: 0 0.5% 0.5% 0.5%; overflow-x: hidden; overflow-y: auto;">
			            <p><strong>Description:</strong></p>
			            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ornare mauris eu risus sodales, sed malesuada augue eleifend. Vestibulum ullamcorper libero at enim rhoncus auctor. Pellentesque tempus, augue quis mollis posuere, sem arcu mattis dolor, non consequat mi enim sit amet est. Quisque at nulla augue. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Maecenas id molestie neque. Aenean ut tellus eu quam hendrerit convallis. Phasellus nisi justo, rutrum sit amet turpis ut, faucibus imperdiet metus. Curabitur ultricies diam eget turpis rutrum, sed porta lorem suscipit. Sed aliquet, risus quis hendrerit posuere, orci ligula congue metus, ac vulputate eros erat at diam. In sodales dictum dolor. Cras eleifend est id urna sagittis sollicitudin. Sed non semper risus. Donec imperdiet nibh in venenatis feugiat. Nunc magna felis, tempus eu sodales ut, aliquet sed odio.</p>
						<p>In dapibus efficitur tortor, id placerat augue elementum at. Pellentesque auctor accumsan justo a sagittis. Phasellus quis accumsan erat. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur pellentesque ut lacus interdum lacinia. Nam pretium arcu sit amet auctor imperdiet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed sed nisi at massa fermentum tempor eu vitae dolor. Maecenas mattis ipsum sed odio blandit vehicula. Donec pharetra, quam ut scelerisque luctus, dolor lacus bibendum sapien, id consectetur orci nulla ut tellus. Pellentesque eu quam commodo, ultricies sem ut, sodales elit.</p>
						<p>Nam aliquet leo eros, id volutpat nibh facilisis ac. In eleifend, elit eget varius sodales, ipsum velit euismod risus, eget bibendum dolor erat vitae nisi. Praesent faucibus mattis tortor, at condimentum ipsum tempus quis. Maecenas egestas diam augue, vitae mattis sem vestibulum quis. Suspendisse non pulvinar nunc, eu dictum augue. Vivamus sit amet nisl at ante vulputate lacinia id id lorem. Nam a tortor pharetra, efficitur ex id, luctus augue. Integer non augue tortor. Cras nec diam ultrices, viverra felis vel, finibus enim. Vestibulum pharetra suscipit ante. Duis auctor, justo sit amet pellentesque volutpat, dui neque rutrum erat, non lobortis neque eros ac ex. Nullam pretium nunc vel justo ornare ultricies. Donec commodo purus vel neque congue, eu congue ante tincidunt.</p>
						<p>Cras ac sodales metus, eget tincidunt erat. Phasellus semper justo in leo ullamcorper vehicula. Quisque cursus vestibulum lorem. Praesent lectus magna, mattis at hendrerit sit amet, placerat vel mauris. Morbi nulla eros, egestas at pellentesque quis, tincidunt ultrices mauris. Integer justo ligula, aliquet sodales erat non, volutpat maximus mi. Curabitur id tempus eros, a vestibulum urna. Proin at porttitor diam. Integer pulvinar nibh sit amet nisl tempus laoreet. Pellentesque congue condimentum eros, ac sagittis orci facilisis non. Aenean congue et erat a vehicula. Nam et scelerisque tortor, eget convallis diam. Nulla vestibulum dui in nisi pharetra, ac condimentum urna aliquet. Nulla ullamcorper nisi elit, non volutpat ipsum aliquam ac.</p>
						<p>Phasellus in mollis leo. Curabitur gravida posuere ligula. Suspendisse cursus dui at nisi suscipit tincidunt. Curabitur lacinia volutpat diam nec convallis. Praesent vehicula velit a risus pulvinar tempus. Etiam nec viverra odio, eget ultrices ex. Donec condimentum eleifend nisi, sit amet placerat mauris elementum consequat. Integer aliquam nec diam et pharetra. Praesent ut nulla id sem scelerisque fermentum. Mauris at nunc sed justo maximus suscipit ac in lorem. Nam id turpis vehicula, tempor magna vel, placerat sem.</p>
					</div>
	
				</div>
			</form>
          <% } else { %>
          	<div class="content" style="display: flex; flex: 0.9; flex-direction: row; background-color: black; overflow-y: auto; align-items: center;">
          <div style="display: flex; flex: 3; flex-direction: column; background-color: white; margin: 0.5%; border-radius: 1%; height: 98%;">
					<div style="display: flex; flex: 16; flex-direction: column;">
		            <img src="${product.getImage()}" alt="product.image" style="max-width: 98%; border-radius: 8px;"/>
		          </div>
          <div style="display: flex; flex: 2; align-items: center; justify-content: center;">
          	<input type="hidden" name="id" value="${product.getProductID()}"/>
            
          </div>
          </div>

			<div style="display: flex; flex: 5; flex-direction: column; background-color: white; margin: 0.5% 0.5% 0.5% 0; border-radius: 1%; height: 98%;">
	          <div style="display: flex; flex-direction: column; flex: 4; margin: 0.5%;">
	          	<p><strong>Name:</strong></p>
	            <p>${product.getName()}</p>
	          	<p><strong>Brand:</strong></p>
	            <p>${product.getBrand()}</p>
	          	<p><strong>Category:</strong></p>
	            <p>${product.getCategory()}</p>
	          	<p><strong>Price:</strong></p>
	            <p>$${product.getPrice()} AUD</p>
	          	<p><strong>Stock:</strong></p>
	            <p>${product.getStockNumber()} remaining</p>
	          </div>
	          <div style="display: flex; flex: 4; flex-direction: column; margin: 0 0.5% 0.5% 0.5%; overflow-x: hidden; overflow-y: auto;">
	            <p><strong>Description:</strong></p>
	            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ornare mauris eu risus sodales, sed malesuada augue eleifend. Vestibulum ullamcorper libero at enim rhoncus auctor. Pellentesque tempus, augue quis mollis posuere, sem arcu mattis dolor, non consequat mi enim sit amet est. Quisque at nulla augue. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Maecenas id molestie neque. Aenean ut tellus eu quam hendrerit convallis. Phasellus nisi justo, rutrum sit amet turpis ut, faucibus imperdiet metus. Curabitur ultricies diam eget turpis rutrum, sed porta lorem suscipit. Sed aliquet, risus quis hendrerit posuere, orci ligula congue metus, ac vulputate eros erat at diam. In sodales dictum dolor. Cras eleifend est id urna sagittis sollicitudin. Sed non semper risus. Donec imperdiet nibh in venenatis feugiat. Nunc magna felis, tempus eu sodales ut, aliquet sed odio.</p>
				<p>In dapibus efficitur tortor, id placerat augue elementum at. Pellentesque auctor accumsan justo a sagittis. Phasellus quis accumsan erat. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur pellentesque ut lacus interdum lacinia. Nam pretium arcu sit amet auctor imperdiet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed sed nisi at massa fermentum tempor eu vitae dolor. Maecenas mattis ipsum sed odio blandit vehicula. Donec pharetra, quam ut scelerisque luctus, dolor lacus bibendum sapien, id consectetur orci nulla ut tellus. Pellentesque eu quam commodo, ultricies sem ut, sodales elit.</p>
				<p>Nam aliquet leo eros, id volutpat nibh facilisis ac. In eleifend, elit eget varius sodales, ipsum velit euismod risus, eget bibendum dolor erat vitae nisi. Praesent faucibus mattis tortor, at condimentum ipsum tempus quis. Maecenas egestas diam augue, vitae mattis sem vestibulum quis. Suspendisse non pulvinar nunc, eu dictum augue. Vivamus sit amet nisl at ante vulputate lacinia id id lorem. Nam a tortor pharetra, efficitur ex id, luctus augue. Integer non augue tortor. Cras nec diam ultrices, viverra felis vel, finibus enim. Vestibulum pharetra suscipit ante. Duis auctor, justo sit amet pellentesque volutpat, dui neque rutrum erat, non lobortis neque eros ac ex. Nullam pretium nunc vel justo ornare ultricies. Donec commodo purus vel neque congue, eu congue ante tincidunt.</p>
				<p>Cras ac sodales metus, eget tincidunt erat. Phasellus semper justo in leo ullamcorper vehicula. Quisque cursus vestibulum lorem. Praesent lectus magna, mattis at hendrerit sit amet, placerat vel mauris. Morbi nulla eros, egestas at pellentesque quis, tincidunt ultrices mauris. Integer justo ligula, aliquet sodales erat non, volutpat maximus mi. Curabitur id tempus eros, a vestibulum urna. Proin at porttitor diam. Integer pulvinar nibh sit amet nisl tempus laoreet. Pellentesque congue condimentum eros, ac sagittis orci facilisis non. Aenean congue et erat a vehicula. Nam et scelerisque tortor, eget convallis diam. Nulla vestibulum dui in nisi pharetra, ac condimentum urna aliquet. Nulla ullamcorper nisi elit, non volutpat ipsum aliquam ac.</p>
				<p>Phasellus in mollis leo. Curabitur gravida posuere ligula. Suspendisse cursus dui at nisi suscipit tincidunt. Curabitur lacinia volutpat diam nec convallis. Praesent vehicula velit a risus pulvinar tempus. Etiam nec viverra odio, eget ultrices ex. Donec condimentum eleifend nisi, sit amet placerat mauris elementum consequat. Integer aliquam nec diam et pharetra. Praesent ut nulla id sem scelerisque fermentum. Mauris at nunc sed justo maximus suscipit ac in lorem. Nam id turpis vehicula, tempor magna vel, placerat sem.</p>
			</div>

			</div>
			</div>
          <% } %>
      </div>
	</body>
</html>
