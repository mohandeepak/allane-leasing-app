# Allane Leasing Application
A leasing application to administrate leasing contracts.

**Steps to run the application:**

1) Clone the repository using the following command

   git clone https://github.com/mohandeepak/allane-leasing-app.git

2) Change the following values to the following fields in application.properties file according to your database configuration

    **spring.datasource.url**=jdbc:mysql://localhost:3306/DB_NAME
    
    **spring.datasource.username**= DB_USERNAME
    
    **spring.datasource.password**= DB_PASSWORD

3) Build the gradle file and run the spring boot application 

The overview of backend REST-API's for the 3 entities Customer, Vehicle and Contract can be found at 

    http://localhost:8080/swagger-ui/index.html


**Technology Stack used:**
* Java 11
* Spring Boot  2.7
* MySQL Database
* Junit & Mockito
* Gradle
* Flyway database migration to create the initial schema
* OpenAPI specification to generate server/client.

**Design Decisions:**
1) Should contact Overview be a part of Contract Model class?

   It makes sense to have a separate POJO class for getting the overview of available contracts.
    
    **Reason 1: In accordance with "Single Responsibility Principle" of a class.**
    
    **Reason 2: Makes it easier to pass an entire model to frontend layer for display.**
    
2) Many-to-One and One-to-One mappings for contract 
    
    This is done as a part of contract class for better readability
    
    -> many contracts can belong to one customer (many to one mapping)
    
    -> one contract can be mapped to only one vehicle  (one to one mapping)
    
**Edgecases**:

1) If vehicle already has a contract assigned to it, and a customer 
tries to create another one  then "ContractException" is thrown.

2) If either vehicle or customer don't exist in their respective table
but user tries to create a contract with new values for customer
and vehicle object then by default its a violation of foreign key constraint.

**Possible refactoring**

The getter,setter methods and constructors of the model classes can be replaced by appopriate
annotation supported by lambok library like @Getter, @Setter, @AllArgsConstructor,@NoArgsConstructor, etc.
Although it is a good idea to use them as it promotes better readability by reducing boilerplate code

I chose not to for this project because of the below reasons:

**1) Enormous coupling**
Business Logic is forced to depend on Lombok which isn't ideal as it 
is a good practise to keep components as independent as possible.

**2) Liberty of design**
Since, I had the liberty to design from scratch I chose this approach,
wouldn't fret much for an already existing codebase.

3) I didn't have to spend time writing the entire boiler plate code as 
IntelliJ can generate getters, setters and cosntructors in just 2 clicks.


**Overview of the Task:**

1) Full Back-end development of all rest apis - Done
2) Fly way migration for intial schema - Done
3) Unit tests for back end
    a) Controller layer
    b) Service Layer
4) Front end Development
    
    
 
   