# Demo Shopping REST API
Demo shopping is an application that is developed in the scope of imaginary works!

# Installation
## Pre Requisities
- This system need **Java 8** and **Maven** installation for running the application. 
- It is also need to have **Git** version controlling system. 
- Clone the repository with the command below:
```
$ git clone https://github.com/denizg/demo-shopping.git
```

## Run the Application
- H2 runtime database is chosen in order to run and evaluate quickly this application. 
- Run the [runShoppingApp.bat](https://github.com/denizg/demo-shopping/blob/master/runShoppingApp.bat) in the root folder for starting the application. 
- The bat script basically includes those commands below: 
```
$ mvn install && java -jar target/demo-0.0.1-SNAPSHOT.jar
```
## Test the API
You can run **Postman** for testing the API easily. Please refer the [postman collection](https://github.com/denizg/demo-shopping/blob/master/src/test/resources/ShoppingRESTCollection.postman_collection.json) that is provided with the project.
# Documentation
Please refer the Swagger documentation after you run the application by using this link: 
http://localhost:8080/shopping-api/swagger-ui/#/

# Questions
1. For authentication, we can use JWT tokens. Those tokens includes a secret and expiration date and carries with encrypted format on the wire. 
For checking and updating expired tokens, we need to define authentication filters, they're basically working like interceptors, works before the methods we mark.
2. For redundancy, I would say in the future if we want to divide the system into modules for practicing micorservice architecture, we can design critical services in a fail safe manner. 
For example placing orders can be served from module A and its replicated version can ben run be run in module B. With that, we can be sure that if Module A fails somehow, we have order placement is still running. 
