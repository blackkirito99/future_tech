# Future Tech
## SWEN90007 Software Design and Architecture
## 2018 Semester 2
### Team 28
#### Ivan Ken Weng Chee
#### Yuwei Bao

## Links
### Heroku
#### (Deliverable 3) http://future-tech.herokuapp.com/
#### (Deliverable 2) https://future-technology.herokuapp.com/
### Bitbucket Repo
#### https://bitbucket.org/blackkirito99/swen90007_team28_project/src/feature-b/

## Instructions
### Local Deployment
#### Clone github repository >> git clone https://blackkirito99@bitbucket.org/blackkirito99/swen90007_team28_project.git
#### Start Apache Derby network server (startNetworkServer.bat)
#### Remove workspace/.metadata/.plugins/org.eclipse.datatools.sqltools.result/result file in Eclipse workspace if it exists
#### Create new Derby 10.X connection named â€˜New Derbyâ€™
#### Connect to database
##### Database: eshop
##### Host: localhost
##### Port Number: 1527
##### Username: user
##### Password: 1234
##### Execute create.sql to populate initial data
#### Start Apache Tomcat server
#### Open browser and navigate to http://localhost:8080/FutureTech_Team28/

## Accounts
### Customer Login Details
#### Username: user
#### Password: 1234
### Retailer Login Details
#### Username: admin
#### Password: admin

## Test Scenario(s)
### Feature A-1
#### I am a new customer of Future Tech. I want to register a new account and then purchase one Macbook Pro and one iPhone X from the website.
#### Test cases:
##### 1. Entering Login page by clicking "User" icon on the top right corner of the page
##### 2. Register a new customer account by clicking â€œRegisterâ€ at the bottom of  the Login page
##### 3. â€œLoginâ€ using username and password as just created
##### 4. Browse website to find the required product, several approaches can be used for the product a. Browse all products by clicking â€œAll productâ€ in index page and find it myself b. Browse products based on category by clicking â€œBrowse by Categoryâ€ in index page and find among them c. Browse products based on brand by clicking â€œBrowse by Storeâ€ in index page and find among them d. Search aimed products by entering query in input bar on the top of the page
##### 5. Click on the products interested in and view the detail of the product
##### 6. Add the product to shopping cart by clicking â€œAdd to Cartâ€ (The page will redirect to cart page)
##### 7. If there are more products to purchase, click icon on the top left to return to index page and repeat actions from step 4. Otherwise, checkout shopping cart by clicking â€œCheckoutâ€. (Added product can change quantity by clicking â€œ+â€ and â€œ-â€ button on corresponding row)
##### 8. Confirm order and make payment by clicking â€˜Confirmâ€™ button
##### 9. Go back to user detail page by clicking â€œUserâ€ icon on the top right corner of the page
##### 10.In the User detail page, change personal information and click â€œUpdate Detailsâ€ to update personal information
##### 11.In the User detail page, click â€œLogoutâ€ to safely finish the whole activities.
### Feature A-2
#### I am a price checker. I want check the price of products on Future Tech Online shop without logining.
#### Test cases:
##### Test cases are the same as in those in Feature A-1 while all browsing actions are conducted without logging in.
##### Clicking â€œUserâ€ and â€œCartâ€ icon will redirect to Login page.
### Feature B
#### I am a retailer and I want to add new product to the store, at the same time, make some update to existing products and delete outdated product from the store.
#### Test cases:
##### 1. Entering Login page by clicking â€œUserâ€ icon on the top right corner of the page;
##### 2. "Login" using â€œadminâ€(username), â€œadminâ€(password) to enter Retailer account;
##### 3. Add new product by click â€œAdd new productâ€;
##### 4. Enter in all information and click â€œCreateâ€;
##### 5. Back to store page by click top left icon;
##### 6. Select the product to be update;
##### 7. Change the information of the product and click â€œUpdate Productâ€;
##### 8. Back to store page by click top left icon;
##### 9. Select the product to be deleted;
##### 10.Click the â€œDelete Productâ€ button (page will redirect to manage page);
##### 11.Go to user detail page by clicking â€œUserâ€ icon on the top right corner of the page;
##### 12.In the User detail page, click â€œLogoutâ€ to safely finish the whole activities.
###### \*All modification of products can be observed in non-retailer website (non-login user and customer).
###### \*\*Click "Cart" icon will be redirected to forbidden page which shows retailer doesnâ€™t has that permission.
