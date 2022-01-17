# moneytransfer
# Problem Statement
    #Application Requirements:
    1- Service should expose a REST API to accept money transfers to other accounts. Money transfers should persist new balance of accounts
    2- Service should expose a REST API for getting the account details. You can disregard currencies at this time
    
    #Points to consider:
    1- Keep the design simple and to the point. The architecture should be scalable for adding new features
    2- Proper handling of concurrent transactions for the accounts (with unit tests)
    3- The datastore should run in-memory for the tests
    4- Proper unit testing and decent coverage is a must
    5- Upload the code to a repository
    6- Disregard Currency or Rate Conversion
    7- Improvise where details are not provided
     
# Setup And Build
    1- Import the project as a maven project.
    2- Clean and build the project through maven.
    3- Run the MoneyTransferApplication.java for starting the project.
    4- Default data will be pushed automatically at the startup of application
    you dont need to push any data, just get the data from /accounts/all
    endpoint and start the transactions.
    Now Project is up and runing.
# Technologies Used

    1- Spring Boot
    2- Swagger open api
    3- Lombok
    4- H2 in memory DB

# URL for api docs/testing
    http://localhost:8080/swagger-ui.html
# Sample URL & Requests for Testing
    
    POST: http://localhost:8080/transaction/
    {
      "sourceAccountNo": "0919191919",
      "destinationAccountNo": "01245464",
      "txnCurrency": "USD",
      "txnAomunt": 1000,
      "txnDate": "2022-01-17T18:39:34.708Z",
      "txnType": "PayNow"
    }
    
    GET: http://localhost:8080/accounts/all
    GET: http://localhost:8080/transaction/all
    
# Focused Area
    Scalable Architecture and simple design
