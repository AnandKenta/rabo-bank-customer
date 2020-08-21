# Rabobank Customer Statement Processor

## Objective
Rabobank receives monthly deliveries of customer statement records. This information is delivered in JSON Format.
These records need to be validated.

### Condition
Implement a REST service which receives the customer statement JSON as a POST data, Perform the below validations.  
1. All transaction references should be unique  
2. The end balance needs to be validated (Start Balance +/- Mutation = End Balance)  

## Technology
- JAVA 8
- Spring Boot (swagger,Restful,Junit, Global Exception handling)
- Apache Maven
- Sprint Tool Suit
- Postman

## To Run The application
Do the follwing commends in folder path after clone  
`mvn clean Package`  
`mvn spring-boot:run`  

## To Check The Swagger
- To Test the configuration open this link http://localhost:8080/v2/api-docs
- To check the generated documentation rendered by Swagger UI open link http://localhost:8080/swagger-ui.html

## Implementation Details
- Used spring boot start web dependency
- Implemented Integration test using Junit and Mockito
- Validation for the records has been implemented in the service impl class according to the condition
- Java 8 and lambda expressions are used
