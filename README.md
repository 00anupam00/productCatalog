**Introduction**

This application, named "Product Catalog", catalogs the product in a specific category. Users need to login
in the app to view the products and categories present. Roles are associated to the user and available roles
are "ADMIN", "USER".

An admin user is allowed to modify the product and categories. Normal users can view the products in a category.

**Tools used**

    Name: Product Catalog
    Language: Java 8    
    Framework: SpringBoot, Spring Security with Jwt authentication
    Build Tool: Maven    
    Database Management: Liquibase.    
    JPA: Hibernate    
    Test Framework: Junit4 with Mockito and SpringRunner
    Database: Mysql 5.7
    Version Control: Git
    
**PRE-REQUISITES**

For the project to be run successfully below are the required tools to be pre-installed:
    
    1. Java 8+
    2. Maven 3.3+
    3. Mysql 5.7+
    
**DEPLOYMENT STEPS:**   
 
    1. This project can be retrieved from the github by cloning the repository with the below command:
        `git clone https://github.com/00anupam00/productCatalog.git`
    2. Is the project directory where the pom.xml, then run the command:
        `bash start.sh`  -- For Linux systems.
        `.\start.bat`    -- For Windows systems.

**ENDPOINTS**

**User Login:** This endpoint logs in an user and returns the authentication token.

      Request:
              HTTP Method = POST
              Request URI = /customers/login
               Parameters = {}
                  Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8"]
                     Body = {"customerId":0,"name":"Anupam","email":"anupam@gmail.com","password":"12345pwd","creditCard":"xxxx-xxxx-xxxx-xxxx","address1":"Estonia","address2":null,"city":null,"region":null,"postalCode":null,"country":null,"mobPhone":null,"authorities":"ADMIN"}
                     
       Response:
                  Status = 200
                 Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
            Content type = application/json;charset=UTF-8
                    Body = {"customer":{"customerId":1,"name":"Anupam","email":"anupam@gmail.com","password":"12345pwd","creditCard":null,"address1":"Estonia","address2":null,"city":null,"region":null,"postalCode":null,"country":null,"mobPhone":null,"authorities":"ADMIN"},"accessToken":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY2NzE2LCJpYXQiOjE1NzE5NDg3MTZ9.6qHhARVX0iZq9efwLt1ml4UxMrxq3EUIFdcypKN4na6lbToWjBxYkkVkDCfj3s4aRCZNJib6pB37giI6V6anww","expiresIn":"Fri Oct 25 04:25:16 EEST 2019"}

**Register a User:** This endpoint registers an user with the mentioned details in request body.

    Request:
          HTTP Method = POST
          Request URI = /customers
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8"]
                 Body = {"customerId":0,"name":"Anupam","email":"anupam@gmail.com","password":"12345pwd","creditCard":"xxxx-xxxx-xxxx-xxxx","address1":"Estonia","address2":null,"city":null,"region":null,"postalCode":null,"country":null,"mobPhone":null,"authorities":"ADMIN"}
                 
    Response:
               Status = 201
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"customer":{"customerId":1,"name":"Anupam","email":"anupam@gmail.com","password":"12345pwd","creditCard":null,"address1":"Estonia","address2":null,"city":null,"region":null,"postalCode":null,"country":null,"mobPhone":null,"authorities":"ADMIN"},"accessToken":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY2NzE2LCJpYXQiOjE1NzE5NDg3MTZ9.6qHhARVX0iZq9efwLt1ml4UxMrxq3EUIFdcypKN4na6lbToWjBxYkkVkDCfj3s4aRCZNJib6pB37giI6V6anww","expiresIn":"Fri Oct 25 04:25:16 EEST 2019"}
                 
**Update User:** An user can update his/her details. The user details to be updated are collected from the token.

    Request:
          HTTP Method = PUT
          Request URI = /customer
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY2NzE2LCJpYXQiOjE1NzE5NDg3MTZ9.6qHhARVX0iZq9efwLt1ml4UxMrxq3EUIFdcypKN4na6lbToWjBxYkkVkDCfj3s4aRCZNJib6pB37giI6V6anww"]
                 Body = {"customerId":1,"name":"Anupam Rakshit","email":"anupam@gmail.com","password":null,"creditCard":null,"address1":"Estonia updated","address2":null,"city":null,"region":null,"postalCode":null,"country":null,"mobPhone":null,"authorities":null}

    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"customerId":1,"name":"Anupam Rakshit","email":"anupam@gmail.com","password":null,"creditCard":null,"address1":"Estonia","address2":null,"city":null,"region":null,"postalCode":null,"country":null,"mobPhone":null,"authorities":null}


**Update CreditCard:** An user can update his/her credit card details.
    
    Request:
          HTTP Method = PUT
          Request URI = /customer/creditCard
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY2NzE2LCJpYXQiOjE1NzE5NDg3MTZ9.6qHhARVX0iZq9efwLt1ml4UxMrxq3EUIFdcypKN4na6lbToWjBxYkkVkDCfj3s4aRCZNJib6pB37giI6V6anww"]
                 Body = {"customerId":0,"name":"Anupam","email":"anupam12@gmail.com","password":"12345pwd","creditCard":"xxxx-xxxx-xxxx-xxxx","address1":"Estonia","address2":null,"city":null,"region":null,"postalCode":null,"country":null,"mobPhone":null,"authorities":"USER"}

    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"customerId":1,"name":null,"email":null,"password":null,"creditCard":"123498763456","address1":null,"address2":null,"city":null,"region":null,"postalCode":null,"country":null,"mobPhone":null,"authorities":null}


**Customer Details:** Customer details can be found out using the following endpoint.

    Request:
          HTTP Method = GET
          Request URI = /customers
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY2NzE2LCJpYXQiOjE1NzE5NDg3MTZ9.6qHhARVX0iZq9efwLt1ml4UxMrxq3EUIFdcypKN4na6lbToWjBxYkkVkDCfj3s4aRCZNJib6pB37giI6V6anww"]
    
    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"customerId":1,"name":"Anupam","email":"anupam@gmail.com","password":"12345pwd","creditCard":null,"address1":"Estonia","address2":null,"city":null,"region":null,"postalCode":null,"country":null,"mobPhone":null,"authorities":"ADMIN"}

**Get all Products:** This endpoint returns a list of product categories to the user.

    Request:
          HTTP Method = GET
          Request URI = /products
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8"]
                 Body = null
    
    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = [{"productId":1,"name":"Product Name1","description":"Product Description1","price":2014.2,"discountedPrice":1034.2,"category":null},{"productId":2,"name":"Product Name2","description":"Product Description2","price":204.2,"discountedPrice":104.2,"category":null}]

**Create a product:** This  endpoint creates a product. An Admin user can only create a product.

    Request:
          HTTP Method = POST
          Request URI = /products
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY4ODI2LCJpYXQiOjE1NzE5NTA4MjZ9.N_TXVRvk1lzaxc0qGMZrRzHaY_Mrjx46tJt4IDV_6yXcXau1aBOtZ5sJxEKXf_U10G5QJeJzfT6fG-m6ZDF6BA", Accept:"application/json;charset=UTF-8"]
                 Body = {"productId":1,"name":"Product Name1","description":"Product Description1","price":2014.2,"discountedPrice":1034.2,"categoryId":1}

    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"productId":1,"name":"Product Name1","description":"Product Description1","price":2014.2,"discountedPrice":1034.2,"category":null}

**Get a Product:** This endpoint retrieves a product by productId.

    Request:
          HTTP Method = GET
          Request URI = /products/{productId}
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8"]

    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"productId":1,"name":"Product Name1","description":"Product Description1","price":2014.2,"discountedPrice":1034.2,"category":null}

**Get All Products in a Category:** This endpoint retrieves all products in a category.

    Request:
          HTTP Method = GET
          Request URI = /products/inCategory/{categoryId}
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8"]

    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"rows":[{"productId":1,"name":"Product Name1","description":"Product Description1","price":2014.2,"discountedPrice":1034.2,"category":null},{"productId":2,"name":"Product Name2","description":"Product Description2","price":204.2,"discountedPrice":104.2,"category":null}]}

**Delete a product:** This endpoint deletes a product by productId, by ADMIN users only

    Request:
          HTTP Method = DELETE
          Request URI = /products/{productId}
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY4ODI2LCJpYXQiOjE1NzE5NTA4MjZ9.N_TXVRvk1lzaxc0qGMZrRzHaY_Mrjx46tJt4IDV_6yXcXau1aBOtZ5sJxEKXf_U10G5QJeJzfT6fG-m6ZDF6BA", Accept:"application/json;charset=UTF-8"]
                 Body = null

    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", Content-Length:"8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = Deleted.

**Update a Product:** This endpoint updates a product by ADMIN users only.

    MockHttpServletRequest:
          HTTP Method = PUT
          Request URI = /products/{productId}
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY4ODI2LCJpYXQiOjE1NzE5NTA4MjZ9.N_TXVRvk1lzaxc0qGMZrRzHaY_Mrjx46tJt4IDV_6yXcXau1aBOtZ5sJxEKXf_U10G5QJeJzfT6fG-m6ZDF6BA", Accept:"application/json;charset=UTF-8"]
                 Body = {"productId":1,"name":"Product Name1","description":"Product Description1","price":2014.2,"discountedPrice":1034.2,"categoryId":1}

    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"productId":1,"name":"Product Name1","description":"Product Description1","price":2014.2,"discountedPrice":1034.2,"category":null}

**Get all Categories:** This endpoint returns a list of product categories to the user.

    Request:
          HTTP Method = GET
          Request URI = /categories
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW0xMkBnbWFpbC5jb20iLCJleHAiOjE1NzE5Njk1MjgsImlhdCI6MTU3MTk1MTUyOH0.aunMk48NEBxP6k2OfZ3JiZsFacrbWNMhtBPaBt8RigX5AJjy44bzCIDZQfNypWmaxaibOVioLJ7X8NagZUs5yA"]
     
    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = [{"category_id":1,"name":"Category 1","description":"Description 1","department_id":0},{"category_id":1,"name":"Category 2","description":"Description 2","department_id":0}]

**Get a category:** This endpoint returns a single category using the category id.

    Request:
          HTTP Method = GET
          Request URI = /categories/{categoryId}
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY5NTI5LCJpYXQiOjE1NzE5NTE1Mjl9.yB9Y-VxheKyhJC5VlDaQxQbHJMIdt8mE7Ks5V7nwtJwQKV0WJIj4wQ-UXu_OsUj5luDTH1VwSVYsWRMjH0XRtg"]
     
    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"category_id":1,"name":"Category 2","description":"Description 2","department_id":0}

**Create a category:** This endpoint creates a category by ADMIN users only.

    Request:
          HTTP Method = POST
          Request URI = /categories
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY5NTI5LCJpYXQiOjE1NzE5NTE1Mjl9.yB9Y-VxheKyhJC5VlDaQxQbHJMIdt8mE7Ks5V7nwtJwQKV0WJIj4wQ-UXu_OsUj5luDTH1VwSVYsWRMjH0XRtg"]
                 Body = {"name":"Category 1","description":"Category Description 1","productIds":[1]}
    
    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"categoryId":1,"name":"Category 2","description":"Description 2","products":[]}

**Delete a category:** This endpoint deletes a category by ADMIN users only, and returns the categoryId of the removed category.

    Request:
          HTTP Method = DELETE
          Request URI = /categories/{categoryId}
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY5NTI4LCJpYXQiOjE1NzE5NTE1Mjh9.SzakmxZCBro2AUWsNtQArqaysvVxqQeLtjtb3mek0a06Y52kbGizLshExnwVhH5p43oUYjo-jWf4ql9W21ux8A"]
                 Body = null

    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = 1

**Update a Category:** This endpoint updates a category by ADMIN users only.

    Request:
          HTTP Method = PUT
          Request URI = /categories/{categoryId}
           Parameters = {}
              Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json;charset=UTF-8", USER-KEY:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnVwYW1AZ21haWwuY29tIiwiZXhwIjoxNTcxOTY5NTI5LCJpYXQiOjE1NzE5NTE1Mjl9.yB9Y-VxheKyhJC5VlDaQxQbHJMIdt8mE7Ks5V7nwtJwQKV0WJIj4wQ-UXu_OsUj5luDTH1VwSVYsWRMjH0XRtg"]
                 Body = {"name":"Category 1","description":"Category Description 1","productIds":[1]}

    Response:
               Status = 200
        Error message = null
              Headers = [Content-Type:"application/json;charset=UTF-8", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
         Content type = application/json;charset=UTF-8
                 Body = {"categoryId":1,"name":"Category 1","description":"Category Description 1","products":[{"productId":0,"name":null,"description":null,"price":0.0,"discountedPrice":0.0,"category":null}]}
