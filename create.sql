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
INSERT INTO APP.products VALUES (1001, 'iPhone SE', 'Apple', 'SM', 429.00, 10, 'Apple A9 Chip', 10, 4.0, 'https://www.unionwireless.com/content/images/thumbs/0000284_apple-iphone-se_550.png');
INSERT INTO APP.products VALUES (1002, 'iPhone 6', 'Apple', 'SM', 399.00, 10, 'Apple A8 Chip', 10, 4.7, 'https://staticshop.o2.co.uk/product/images/iphone6s-plus-rsgld-sku-header.png?cb=0c25ff5af69f144e2712d04e1c9c6631');
INSERT INTO APP.products VALUES (1003, 'iPhone 6 Plus', 'Apple', 'SM', 479.00, 10, 'Apple A8 Chip', 10, 5.5, 'https://staticshop.o2.co.uk/product/images/iphone6s-plus-rsgld-sku-header.png?cb=0c25ff5af69f144e2712d04e1c9c6631');
INSERT INTO APP.products VALUES (1004, 'iPhone 6S', 'Apple', 'SM', 569.00, 10, 'Apple A9 Chip', 10, 4.7, 'https://staticshop.o2.co.uk/product/images/iphone6s-plus-rsgld-sku-header.png?cb=0c25ff5af69f144e2712d04e1c9c6631');
INSERT INTO APP.products VALUES (1005, 'iPhone 6S Plus', 'Apple', 'SM', 679.00, 10, 'Apple A9 Chip', 10, 5.5, 'https://staticshop.o2.co.uk/product/images/iphone6s-plus-rsgld-sku-header.png?cb=0c25ff5af69f144e2712d04e1c9c6631');
INSERT INTO APP.products VALUES (1006, 'iPhone 7', 'Apple', 'SM', 749.00, 10, 'Apple A10 Fusion Chip', 10, 4.7, 'https://boltmobile.ca/wp-content/uploads/2016/09/iphone7-plus-front-web-boltmobile-sasktel.png');
INSERT INTO APP.products VALUES (1007, 'iPhone 7 Plus', 'Apple', 'SM', 929.00, 10, 'Apple A10 Fusion Chip', 10, 5.5, 'https://boltmobile.ca/wp-content/uploads/2016/09/iphone7-plus-front-web-boltmobile-sasktel.png');
INSERT INTO APP.products VALUES (1008, 'iPhone 8', 'Apple', 'SM', 979.00, 10, 'Apple A11 Chip', 10, 4.7, 'https://boltmobile.ca/wp-content/uploads/2016/09/iphone7-plus-front-web-boltmobile-sasktel.png');
INSERT INTO APP.products VALUES (1009, 'iPhone 8 Plus', 'Apple', 'SM', 1149.00, 10, 'Apple A11 Chip', 10, 5.5, 'https://boltmobile.ca/wp-content/uploads/2016/09/iphone7-plus-front-web-boltmobile-sasktel.png');
INSERT INTO APP.products VALUES (1010, 'iPhone X', 'Apple', 'SM', 1579.00, 10, 'Apple A11 Bionic Chip', 10, 5.8, 'https://staticshop.o2.co.uk/product/images/iphone-x-space-grey-sku-header.png?cb=25dc5afb0412fc40a28aa29d82cb53d0');
INSERT INTO APP.products VALUES (1011, 'iPhone XR', 'Apple', 'SM', 1229.00, 10, 'Apple A12 Bionic Chip', 10, 6.1, 'https://iphoneimei.net/images/models/iphone-xr.png');
INSERT INTO APP.products VALUES (1012, 'iPhone XS', 'Apple', 'SM', 1629.00, 10, 'Apple A12 Bionic Chip', 10, 5.8, 'https://store.storeimages.cdn-apple.com/8755/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone/xs/iphone-xs-max-select-2018-3?wid=181&hei=491&fmt=png-alpha&.v=1536269229769');
INSERT INTO APP.products VALUES (1013, 'iPhone XS Max', 'Apple', 'SM', 1799.00, 10, 'Apple A12 Bionic Chip', 10, 6.5, 'https://store.storeimages.cdn-apple.com/8755/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone/xs/iphone-xs-max-select-2018-3?wid=181&hei=491&fmt=png-alpha&.v=1536269229769');
INSERT INTO APP.products VALUES (1014, 'MacBook', 'Apple', 'PC', 1899.00, 10, 'Intel Core m3 processor', 5, 12.0, 'https://i0.wp.com/ioshacker.com/wp-content/uploads/2016/10/overview_wireless_hero_enhanced.png?resize=1000%2C503&ssl=1');
INSERT INTO APP.products VALUES (1015, 'MacBook Pro', 'Apple', 'PC', 1849.00, 10, 'Intel Core i5 processor', 5, 13.0, 'https://static.digit.in/product/thumb_85780_product_td_600.png');
INSERT INTO APP.products VALUES (1016, 'Zenbook', 'Asus', 'PC', 1899.99, 10, 'i7-7700K', 1000, 14.0, 'https://www.notebookcheck.net/fileadmin/Notebooks/Asus/Zenbook_UX303UB-DH74T/ux30343.png');
INSERT INTO APP.products VALUES (1017, 'Aspire ES1', 'Acer', 'PC', 1299.99, 10, 'i7-7700K', 1000, 15.6, 'https://static.acer.com/up/Resource/Acer/Laptops/Aspire_ES1/Images/20140828/Acer-Aspire-E-ES1-512-black-nontouch-glare-sku-main.png');
INSERT INTO APP.products VALUES (1018, 'XPS 15', 'Dell', 'PC', 1099.99, 10, 'i7-7700K', 1000, 15.0, 'https://cdn.vox-cdn.com/thumbor/__18fLBr_6KXQd7poqTxOXnnwoo=/0x0:4000x2830/1200x800/filters:focal(1680x1095:2320x1735)/cdn.vox-cdn.com/uploads/chorus_image/image/59251847/XPS_15___1.0.png');

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
