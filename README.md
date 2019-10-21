For an advanced user features to handle about 1,000,000 active users two of the few approaches might be considered.
1. Follow a microservice architecture instead of the monolithic structure. 
    For Example, 
    1. Microservice 1: Accounts and Authentication service: The whole function of this service would be to create new
    customers and autenticate the customers for transactional purposes.
    2. Microservice 2: Handle products and departments. This service would only have the product related
    responsibilities.
    3. Microservice 3: This service would only handle the payment and orders related functions viz. connecting to 
    stripe endpoint for payment etc.
    
2. Moreover, the application can be hosted in cloud platform for having multiple instances with a load balancer,
which redirects the api calls to available instances present following a rule to balance out the load. Also the mysql
database used to support the application could be configured for High Availibility(HA) to support more load from the 
application. 
