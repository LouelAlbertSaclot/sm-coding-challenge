# SiteMinder Coding Challenge

## Problem Statement

Create a service that accepts the necessary information and sends emails. The application should provide an abstraction 
between two different email service providers. If one of the services goes down, your service can quickly fail-over to a 
different provider without affecting your customers.

* Solution should cater for multiple email recipients, CCs and BCCs
* HTML email body types is optional only (plain text is OK)
* The solution should be implemented as one or more RESTful API calls
* No authentication is required for the scope of this exercise
* No 3rd party client library should be used to integrate with email providers
* Language to use primarily is Java

### Email Providers
* [Mailgun - Simple Send Documentation](https://documentation.mailgun.com/en/latest/api-sending.html)
* [SendGrid - Simple Send Documentation](https://sendgrid.com/docs/API_Reference/Web_API_v3/index.html)

## Solution Approach

The approach used for this project was to utilise a multi-module structure to allow loose coupling and potential extendability. 
There are different ways to modularise the application and the project opted to separate the Rest API (web), the service codes, 
and client modules. The service module would essentially have a one-to-many relationship with the different email providers 
by way of client modules (i.e. client-mailgun and client-sendgrid). 

For the fail-over requirement, the service module has a 'manager' class that contains the list of email provider beans (client-services). 
When a 'send email' request is received, the manager class would basically loop through its list of client beans until one 
successfully sends the email. The reasoning for this is to require minimal change to register an additional email provider.

Key Benefits:
* Adding a new email provider only requires to create a new client module and add that new bean to the 'manager' class
* Specific integration with email providers are self-contained in their respective module

Some Design Patterns used:
* Composite Pattern
* Builder Pattern
* Facade Pattern

### Status
* Deployed in HEROKU
* Documentation: [Swagger URL](https://simple-emailer-app.herokuapp.com/swagger-ui.html)

### Requirements
* [JDK 8 or Higher](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/download.cgi)
* [Spring Boot](https://spring.io/projects/spring-boot)

### File Structure
```
sm-coding-challenge
.
├── mailer-api              - Contains the controller and application for this Spring Boot application. This contains the swagger configuration as well.
├── services                - Contains the services that translates the request and invokes the client email provider(s) to send the email.
├── common                  - Contains the shared configurations, interfaces and classes across the package with the exception of mailer-api.
├── client-mailgun          - Contains the codes that allow integration with MailGun email provider.
├── client-sendgrid         - Contains the codes that allow integration with SendGrid email provider.
├── README.md               - Documentation for this project.
├── pom.xml                 - Contains dependency requirements.
```

### Testing
Currently only unit tests are existing for each module except for the 'common' module. Ideally some end-to-end test codes would be added in the 'mailer-api'.

#### Unit Testing - TODO add more unit tests
* Run test for all modules
```bash
cd /sm-coding-challenge
mvn clean test # clean first then run tests
```
* Run test for specific module (i.e. services)
```bash
cd /sm-coding-challenge/services # for other options, please see 'File Structure' section
mvn clean test
``` 

### How to run in local
This section will just assume it is to run in a terminal and not in any IDE.

1. Update the the 'application.yml' with the required API keys and Domain for email providers
```bash
vim /sm-coding-challenge/common/src/main/resources/application.yml
```
2. Build application using maven
```bash
cd /sm-coding-challenge
mvn clean install
```
3. After 'BUILD SUCCESS', navigate to 'mailer-api' module and run application
```bash
cd /sm-coding-challenge/mailer-api
mvn spring-boot:run     # or run the JAR file -> java -jar target/mailer-api-0.0.1-SNAPSHOT.jar
```
* Open [Local Swagger-UI](http://localhost:8080/swagger-ui.html) for quick validation and some documentation.

## TODO - Still need to complete
* Add validation checks in 'service' module for invalid inputs
* Add more varied unit tests per module (excluding common)
* Improve on error handling, return appropriate messages and proper logging
