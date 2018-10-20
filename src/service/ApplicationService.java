package service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auth.AppSession;
import datasource.Registry;
import datasource.UnitOfWork;
import domain.CartItem;
import domain.Customer;
import domain.Order;
import domain.Product;
import domain.Retailer;
import domain.ShoppingCart;
import domain.User;

public class ApplicationService {

    public static void addToCart(int CustomerID, int productID, int quantity) {
        // check if already in chart
        Product item = Product.getProduct(productID);
        List < CartItem > items = ShoppingCart.getCartOf(CustomerID).getAllItemsInCart();
        int index = getIndexOfProduct(items, item);
        if (index >= 0) {
            items.get(index).addQuantity(quantity);
        } else {
            // if not, create new a CartItem
            CartItem cartItem = new CartItem(CustomerID, item.getProductID(), quantity, true);
            // save to register
            Registry.addCartItem(cartItem);
            items.add(cartItem);
        }
        unitOfWorkCommit();
    }


    public static void increaseSingleProductInCart(int CustomerID, int productID, int delta) {
        // find item in chart
        Product item = Product.getProduct(productID);
        List < CartItem > items = ShoppingCart.getCartOf(CustomerID).getAllItemsInCart();
        int index = getIndexOfProduct(items, item);
        if (index >= 0) {
            items.get(index).addQuantity(delta);
            unitOfWorkCommit();
        } else {
            // if not, error
            System.out.println("Increase Error!");
        }
    }
    public static void decreaseSingleProductInCart(int CustomerID, int productID, int delta) {
        // find item in chart
        Product item = Product.getProduct(productID);
        List < CartItem > items = ShoppingCart.getCartOf(CustomerID).getAllItemsInCart();
        int index = getIndexOfProduct(items, item);
        if (index >= 0) {
            items.get(index).removeQuantity(delta);
            unitOfWorkCommit();
        } else {
            // if not, error
            System.out.println("Decrease Error!");
        }
    }

    public static void updateSingleProductInCart(int CustomerID, int productID, int quantity) {
        // find item in chart
        Product item = Product.getProduct(productID);
        List < CartItem > items = ShoppingCart.getCartOf(CustomerID).getAllItemsInCart();
        int index = getIndexOfProduct(items, item);
        if (index >= 0) {
            items.get(index).setQuantity(quantity);
        } else {
            // if not, error
            System.out.println("Update Error!");
        }
    }

    // precondition: index of quantity is same as the change target
    public static void updateProductsInCart(int CustomerID, List < Product > items, List < Integer > quantities) {
        // find each item in chart
        List < CartItem > cartItems = ShoppingCart.getCartOf(CustomerID).getAllItemsInCart();
        for (int i = 0; i < items.size(); i++) {
            int index = getIndexOfProduct(cartItems, items.get(i));
            if (index >= 0) {
                cartItems.get(index).setQuantity(quantities.get(index));

            }
        }
        unitOfWorkCommit();
    }

    public static void deleteProductInCart(int CustomerID, int productID) {
        // find item in chart
        Product item = Product.getProduct(productID);
        List < CartItem > items = ShoppingCart.getCartOf(CustomerID).getAllItemsInCart();
        int index = getIndexOfProduct(items, item);
        if (index >= 0) {
            items.get(index).setQuantity(0);
  	      	UnitOfWork.getCurrent().commit();
        } else {
            // if not, error
            System.out.println("Error!");
        }
    }


    public static Order checkout(int CustomerID) {
        List < CartItem > items = ShoppingCart.getCartOf(CustomerID).getAllItemsInCart();
        Order order  = null;
        // only create order when there are items in cart
        if(items.size() > 0) {
        	order = new Order("FT_000" + (int) new Date().getTime(), CustomerID, true);
	        float subtotal = 0;
	        Map < Product, Integer > products = new HashMap < Product, Integer > ();
	        System.out.println(items.size());
	        for (CartItem item: items) {
	            Product p = Product.getProduct(item.getProductID());
	            // update product stock level
	            //int currentStock = p.getStockNumber();
	            p.setStockNumber(p.getStockNumber()-item.getQuantity());
	            products.put(p, item.getQuantity());
	            // mark it as deleted
	            subtotal += p.getPrice() * item.getQuantity();
	            //remove all
	            item.removeQuantity(item.getQuantity());
	        }
	        // we set it default as AUD
	        order.setCurrency("AUD");
	        order.setTotalPrice(subtotal);
	        order.setProductCopies(products);
	
	        // save to register
	        Registry.addOrder(order);
	        unitOfWorkCommit();
        }
	    return order;
    }

    public static void registerNewCustomer(String username, String lastName, String firstName, String emailAddress,
    		String address, String password) {
    	   	Customer newCustomer = new Customer(0, username, AppSession.CUSTOMER_ROLE, emailAddress,
    	    		  "http://www.epsomps.vic.edu.au/wp-content/uploads/2016/09/512x512-1-300x300.png", true);
    	     newCustomer.setAddress(address);
    	      newCustomer.setPassword(password);
    	      newCustomer.setFirstName(firstName);
    	      newCustomer.setLastName(lastName);
    	      unitOfWorkCommit();
    	   // get generated userID from db and update cache
    	      newCustomer.setUserID(User.login(username, password).getUserID());
       }
    
    public static void registerNewRetailer(String username, String lastName, String firstName, String emailAddress,
        String password) {
        Retailer newRetailer = new Retailer(0, username, AppSession.RETAILER_ROLE, emailAddress,
                  "http://www.epsomps.vic.edu.au/wp-content/uploads/2016/09/512x512-1-300x300.png", true);
        newRetailer.setPassword(password);
        newRetailer.setFirstName(firstName);
        newRetailer.setLastName(lastName);
        unitOfWorkCommit();
       // get generated userID from db and update cache
        newRetailer.setUserID(User.login(username, password).getUserID());
   }

    public static void updateCustomerPersonalDetail(int customerID, String lastName, String firstName,
         String email, String address) {
        Customer customer = (Customer) User.getUser(customerID);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setAddress(address);
        unitOfWorkCommit();
    }

    public static void updateRetailerPersonalDetail(int retailerID, String lastName, String firstName, 
    		String email) {
    	Retailer retailer = (Retailer) User.getUser(retailerID);
    	retailer.setFirstName(firstName);
    	retailer.setLastName(lastName);
        retailer.setEmail(email);
        unitOfWorkCommit();
    }
    
    public static void updateProductInformtion(int productID, String name, String brand, 
    		String category, double price, int stock) {
    	  Product product = Product.getProduct(productID);
	      product.setName(name);
	      product.setCategory(category);
	      product.setPrice(price);
	      product.setStockNumber(stock);
	      product.setBrand(brand);
	      Product.updateProduct(product);
	      unitOfWorkCommit();
    }
    

    private static int getIndexOfProduct(List < CartItem > items, Product item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProductID() == item.getProductID()) {
                return i;
            }
        }
        return -1;
    }
    
    public static void unitOfWorkCommit(){
    	UnitOfWork uow = UnitOfWork.getCurrent();
    	if(uow == null) {
    		uow.newCurrent();
    		uow = UnitOfWork.getCurrent();
    	}
    	uow.commit();
    }
}