CREATE TABLE APP.products (
	productID	INT,
	name		VARCHAR(64),
	brand		VARCHAR(24),
	category	VARCHAR(2),
	price		FLOAT,
	number		INT,
	cpu			VARCHAR(48),
	volume		INT,
	screenSize	FLOAT,
	image		VARCHAR(256),
	PRIMARY KEY (productID)
);
INSERT INTO APP.products VALUES (1001, '15" laptop', 'Asus',  'PC', 1899.99, 10, 'i7-7700K', 1000, 0.0, 'http://www.pngmart.com/files/4/Asus-Laptop-PNG-Pic.png');
INSERT INTO APP.products VALUES (1002, '13" laptop', 'Acer',  'PC', 1299.99, 10, 'i7-7700K', 1000, 0.0, 'https://static.acer.com/up/Resource/Acer/Laptops/Aspire_ES1/Images/20140828/Acer-Aspire-E-ES1-512-black-nontouch-glare-sku-main.png');
INSERT INTO APP.products VALUES (1003, '12" laptop', 'Dell',  'PC', 1099.99, 10, 'i7-7700K', 1000, 0.0, 'https://toppng.com/public/uploads/preview/dell-laptop-11526908023sb8x0rfyoo.png');
INSERT INTO APP.products VALUES (1004, 'iPhone X',   'Apple', 'SM', 1579.00, 10, 'Apple A10X',  0, 5.8, 'https://staticshop.o2.co.uk/product/images/iphone-x-space-grey-sku-header.png?cb=25dc5afb0412fc40a28aa29d82cb53d0');
INSERT INTO APP.products VALUES (1005, 'MacBook',   'Apple', 'PC', 1899.00, 10, 'Intel Core m3 processor',  0, 12.0, 'https://i0.wp.com/ioshacker.com/wp-content/uploads/2016/10/overview_wireless_hero_enhanced.png?resize=1000%2C503&ssl=1');
INSERT INTO APP.products VALUES (1006, 'MacBook',   'Apple', 'PC', 2349.00, 10, 'Intel Core i5 processor',  0, 12.0, 'https://i0.wp.com/ioshacker.com/wp-content/uploads/2016/10/overview_wireless_hero_enhanced.png?resize=1000%2C503&ssl=1');
INSERT INTO APP.products VALUES (1007, 'MacBook Pro',   'Apple', 'PC', 1849.00, 10, 'Intel Core i5 processor',  0, 13.0, 'https://static.digit.in/product/thumb_85780_product_td_600.png');
INSERT INTO APP.products VALUES (1008, 'MacBook Pro',   'Apple', 'PC', 2199.00, 10, 'Intel Core i5 processor',  0, 13.0, 'https://static.digit.in/product/thumb_85780_product_td_600.png');
INSERT INTO APP.products VALUES (1009, 'MacBook Pro',   'Apple', 'PC', 2699.00, 10, 'Intel Core i5 processor',  0, 13.0, 'https://static.digit.in/product/thumb_85780_product_td_600.png');
INSERT INTO APP.products VALUES (1010, 'MacBook Pro',   'Apple', 'PC', 3499.00, 10, 'Intel Core i7 processor',  0, 15.0, 'https://static.digit.in/product/thumb_85780_product_td_600.png');
INSERT INTO APP.products VALUES (1011, 'MacBook Pro',   'Apple', 'PC', 4099.00, 10, 'Intel Core i7 processor',  0, 15.0, 'https://static.digit.in/product/thumb_85780_product_td_600.png');

CREATE TABLE APP.users(
	userID     	INT GENERATED ALWAYS AS IDENTITY,
	lastName	VARCHAR(24),
	firstName	VARCHAR(24),
	username 	VARCHAR(24),
	password	VARCHAR(24),
	email		VARCHAR(128),
	type	 	VARCHAR(2),
	address		VARCHAR(256),
	avatar		VARCHAR(256),
	PRIMARY KEY (userID)
);
INSERT INTO APP.users (lastName, firstName, username, password, email, type, address, avatar) VALUES ('Einstein', 'Albert ', 'user', '1234', 'albert.einstein@ias.edu', 'CU', 'Office 9 IAS Princeton USA', 'http://www.epsomps.vic.edu.au/wp-content/uploads/2016/09/512x512-1-300x300.png');
INSERT INTO APP.users (lastName, firstName, username, password, email, type, address, avatar) VALUES ('Newton', ' Issac', 'admin', 'admin', 'issac.newton@gmail.edu', 'RT', 'Office 9, IAS, Princeton, USA', 'http://www.epsomps.vic.edu.au/wp-content/uploads/2016/09/512x512-1-300x300.png');
INSERT INTO APP.users (lastName, firstName, username, password, email, type, address, avatar) VALUES ('Chee', 'Ivan ', 'ivanc', '1234', 'ichee@student.unimelb.edu.au', 'CU', 'Melbourne', 'https://i1.wp.com/www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png?fit=256%2C256&quality=100&ssl=1');
INSERT INTO APP.users (lastName, firstName, username, password, email, type, address, avatar) VALUES ('Bao', 'Yuwei ', 'yuweib', '1234', 'ybao1@student.unimelb.edu.au', 'CU', 'Melbourne', 'https://i1.wp.com/www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png?fit=256%2C256&quality=100&ssl=1');

CREATE TABLE APP.cartItems(
	customerID	INT,
	productID	INT,
	quantity	INT,
	CONSTRAINT PK_Cart PRIMARY KEY (customerID, productID),
	FOREIGN KEY (customerID) REFERENCES APP.users (userID),
	FOREIGN KEY (productID) REFERENCES APP.products (productID)
);
INSERT INTO APP.cartItems VALUES (1, 1001, 2);
INSERT INTO APP.cartItems VALUES (2, 1002, 2);

CREATE TABLE APP.orders(
	orderNum	VARCHAR(24),
	customerID	INT,
	subtotal	FLOAT,
	currency 	VARCHAR(3),
	PRIMARY KEY(orderNum),
	FOREIGN KEY (customerID) REFERENCES APP.users(userID)
);

CREATE TABLE APP.orderItems(
	productID	INT,
	quantity	INT,
	orderNum	VARCHAR(24),
	CONSTRAINT PK_Order PRIMARY KEY (orderNum, productID),
	FOREIGN KEY (orderNum) REFERENCES APP.orders (orderNum),
	FOREIGN KEY (productID) REFERENCES APP.products (productID)
);
