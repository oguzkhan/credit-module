## CREDIT MODULE CASE STUDY

<br>

This application serves as a credits Rest API for a fictive banking organization. 

This project is authored upon request by Mehmet Oğuzhan Özavar and submitted to Ing Hubs. The project is mainly for educational purposes and licenced under the terms of the MIT license. For any questions, please contact Oğuzhan at hanoguz@gmail.com.

<br>


### PACKING THE CODE AND RUNNING THE APPLICATION
This is a Java 21 application working with H2 in memory database which is embedded inside the application. Only requirement for building a runnable jar package and run the application is to install JDK 21 and maven tool and then run below command:
<br>
`mvn clean spring-boot:run`
H2 database console is available default at:
http://localhost:8080/h2-console
<br>
DB connection string:
jdbc:h2:mem:crediting
<br>
DB username and password: db-user and db-user-pw

### TESTING THE ENDPOINTS
Application starts at 8080 port by default. 2 sample customers are created by running data.sql file under /resources folder. Below are the sample endpoint URL's and request payloads for testing purposes:
<br>

Application asks for a basic authentication in order to call the endpoints. This means you should provide below credentials as basic authentication request header.
<br>
Username and password: admin and pass
<br>

You can use this customerId for testing which is inserted already: d0f8bbf3-d6e4-4b30-acc6-8c7c6f69f77e
<br>

Create Loan Endpoint:
<br>
Method: POST
<br>
Endpoint URL: http://localhost:8080/api/customers/{customerId}/loans
<br>
Sample Request Payload: 
<br>
{	
	"customerId":"{customerId}",
"loanAmount":4000.00,
	"interestRate":0.2,
	"numberOfInstallments":12
}
<br>
Response includes loanId that is created
<br>

List Loans:
<br>
Method: GET
<br>
Endpoint URL: http://localhost:8080/api/customers/{customerId}/loans
<br>


List Loan Installments:
<br>
Method: GET
<br>
Endpoint URL: http://localhost:8080/api/customers/{customerId}/loans/{loanId}}/installments
<br>

Pay Loan:
<br>
Method: POST
<br>
Endpoint URL: http://localhost:8080/api/customers/{customerId}/pay-loan
<br>
Sample Request Payload:
<br>
{
"loanId":"{loanId}",
"amount":2103.30
}
<br>
Sample Response Payload:
<br>
{
"customerId": "{customerId}}",
"loanId": "{loanId}",
"numberOfPaidInstallments": 2,
"totalAmountSpent": 1516.6600,
"allInstallmentsPaid": false
}

### GENERAL ARCHITECTURE

- Code is arranged according to the hexagonal architecture (ports and adaptors) practices.
  - core package consists of application (services), domain and the ports. Core is not coupled or loosely coupled with the frameworks.
  - adapter package contains the specific technologies that implements the ports. A rest api driving adapter is also included inside adapter package.
  - infra package contains most of the configurations (security etc.) related with the spring framework.

<br>
