# Event Sourcing with Axon

This project implements Command and Query Responsibility Segregation (CQRS) pattern and event sourcing in general, using Axon framework with Spring Boot for a microservice bank application.

Axon Framework, a Java framework for building scalable and high-performance event-driven applications.

## Technologies
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![XAMPP](https://img.shields.io/badge/XAMPP-FB7A24.svg?style=for-the-badge&logo=XAMPP&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36.svg?style=for-the-badge&logo=Apache-Maven&logoColor=white)
![AXON](https://camo.githubusercontent.com/a13e831e1d35fe5af514fbe63432e5d5b1ec2ace8319206d230bfd90123e2691/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f41584f4e2d4672616d65776f726b2d253233454532452e7376673f7374796c653d666f722d7468652d6261646765)
![IntelliJ](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=for-the-badge&logo=IntelliJ-IDEA&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1.svg?style=for-the-badge&logo=MySQL&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white)

## Project Tree
```
.
├── main
│   ├── java
│   │   └── ma
│   │       └── ab
│   │           └── event_sourcing_axon
│   │               ├── EventSourcingAxonApplication.java
│   │               ├── commands
│   │               │   ├── aggregates
│   │               │   │   └── AccountAggregate.java
│   │               │   └── controllers
│   │               │       └── AccountCommandController.java
│   │               ├── commonapi
│   │               │   ├── commands
│   │               │   │   ├── BaseCommand.java
│   │               │   │   ├── CreateAccountCommand.java
│   │               │   │   ├── CreditAccountCommand.java
│   │               │   │   └── DebitAccountCommand.java
│   │               │   ├── dtos
│   │               │   │   ├── CreateAccountRequestDTO.java
│   │               │   │   ├── CreditAccountRequestDTO.java
│   │               │   │   └── DebitAccountRequestDTO.java
│   │               │   ├── enums
│   │               │   │   ├── AccountStatus.java
│   │               │   │   └── OperationType.java
│   │               │   ├── events
│   │               │   │   ├── AccountActivatedEvent.java
│   │               │   │   ├── AccountCreatedEvent.java
│   │               │   │   ├── AccountCreditedEvent.java
│   │               │   │   ├── AccountDebitedEvent.java
│   │               │   │   └── BaseEvent.java
│   │               │   └── exceptions
│   │               │   │   └── AmountNegativeException.java
│   │               │   │   └── BalanceNotSufficientException.java
│   │               │   └── queries
│   │               │       └── GetAccountByIdQuery.java
│   │               │       └── GetAllAccountsQuery.java
│   │               └── query
│   │                   ├── controllers
│   │                   │   └── AccountQueryController.java
│   │                   └── entities
│   │                   │   └── Account.java
│   │                   │   └── Operation.java
│   │                   └── repositories
│   │                   │   └── AccountRepository.java
│   │                   │   └── OperationRepository.java
│   │                   └── service
│   │                       └── AccountServiceHandler.java
│   └── resources
│       ├── application.properties
│       ├── static
│       └── templates


```

## Commands
`Commands are the messages which represent requests to alter the state of an aggregate (a domain object).`

There are 3 commands in our application:
* Create Account Command
* Credit Amount to Account Command
* Debit Amount from Account Command
All of these commands extend from a Base Command as an abstract Class

## Event Building
`Events represent the results or consequences of those actions.`

There are 3 events in our application:
* Create Account event
* Credit Amount to Account event
* Debit Amount from Account event
  All of these events extend from a Base Event as an abstract Class

## Aggregate
`Aggregate is a fundamental concept that represents a cluster of related domain objects and encapsulates the business logic and state for a specific part of the domain.`
