# Kotlin Spring Boot REST
Demonstrating a simple create account REST service using Kotlin, Spring Boot and H2 in-memory DB.
The account can only be opened from Monday to Friday within working hours and the user may have a single savings account.

## Prerequisited
- JDK 8 and above
- Maven

## Configuration
The configuration file contains settings for HTTP port, working days, hours and accepted currencies.
```
src/main/resources/application.properties
```
## Building
```
mvn package
```
## Running
```
java -jar target/account-0.0.1-SNAPSHOT.jar
```
## Testing
The service can be tested directly via cURL:
```
curl -H "Content-Type: application/json" -d '{"userId":"u1","type":"Savings","currency":"USD"}' http://localhost:8080/api/savings
```
Also, a web page is provided for testing:
http://localhost:8080/
