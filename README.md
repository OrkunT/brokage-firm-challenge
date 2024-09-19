# brokage-firm-challenge

To design a robust backend for the brokerage firm, leveraging microservices architecture, can be beneficial. 

Design Patterns

1. API Gateway Pattern
   - Purpose: Acts as a single entry point for all client requests, handling request routing, composition, and protocol translation.
   - Benefits: Simplifies client interactions, centralizes authentication and authorization, and can implement cross-cutting concerns like logging and rate limiting.
   - Tools: Spring Cloud Gateway, Netflix Zuul. Later Cloud Services.

2. Aggregator Pattern
   - Purpose: Aggregates responses from multiple microservices into a single response.
   - Benefits: Reduces the number of client calls and simplifies client-side logic.
   - Tools: GraphQL, RESTful APIs.

3. Circuit Breaker Pattern
   - Purpose: Prevents cascading failures by stopping requests to a failing service.
   - Benefits: Enhances system resilience and stability.
   - Tools: Resilience4j, Netflix Hystrix.

4. CQRS (Command Query Responsibility Segregation) Pattern
   - Purpose: Separates read and write operations into different models.
   - Benefits: Optimizes performance and scalability, especially useful for complex business logic.
   - Tools: Axon Framework.

5. Saga Pattern
   - Purpose: Manages distributed transactions across multiple microservices.
   - Benefits: Ensures data consistency and handles long-running transactions.
   - Tools: Eventuate Tram, Axon Framework.

6. Event Driven Pattern (minimum to none code change, transaction support)
6.1. Guaranteeing Transactions and Rollbacks
     -Using Kafka Transactions
     -Kafka provides a transactional API that allows you to produce and consume messages within a transaction. This ensures that either all operations within the transaction succeed or none do, maintaining data consistency.

      Transactional Producers: When a service updates its database and publishes an event to Kafka, it does so within a transaction.
      Transactional Consumers: When another service consumes the event and updates its database, it also does so within a transaction.
For example:

Begin a Transaction:

producer.initTransactions();
producer.beginTransaction();

Produce Messages:

producer.send(record);

Commit or Abort the Transaction:

producer.commitTransaction(); // or producer.abortTransaction();

Consume Messages Transactionally:

consumer.commitSync();

If any part of the transaction fails, you can abort the transaction, and Kafka will ensure that none of the messages are committed12.

Chaining Kafka and Database Transactions
You can chain Kafka transactions with database transactions to ensure atomicity. For example, using Spring Kafka and JPA, you can manage both Kafka and database transactions together:

Begin Kafka Transaction: When receiving new messages, Spring Kafka will automatically begin a Kafka transaction.
Begin Database Transaction: The @Transactional annotation will create a JPA transaction.
Commit or Rollback: If the JPA transaction fails, the Kafka transaction will also fail and be rolled back.

6.2. Configuring Source and Sink Connectors Seperate or Same DB Without Modifying Services
Kafka Connect allows you to configure source and sink connectors to capture and apply changes without modifying your existing services in transactional way. 
Transactional source Source Connector
Configuration: Configure the source connector to capture changes from any databases (e.g., admindb, orderdb) and publish them to Kafka topics.
Example:
JSON

{
  "name": "h2-source-connector",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
    "tasks.max": "1",
    "connection.url": "jdbc:h2:~/test;AUTO_SERVER=TRUE",
    "mode": "incrementing",
    "incrementing.column.name": "id",
    "topic.prefix": "h2-"
  }
}


Transactional Sink Connector
Configuration: Configure the sink connector to listen to Kafka topics and apply changes to the respective databases.
Example:
JSON

{
  "name": "h2-sink-connector",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
    "tasks.max": "1",
    "connection.url": "jdbc:h2:~/test;AUTO_SERVER=TRUE",
    "topics": "h2-events",
    "auto.create": "true",
    "insert.mode": "upsert",
    "pk.mode": "record_key",
    "pk.fields": "id"
  }
}

6.3
Libraries
Debezium: For capturing database changes and publishing them to Kafka.
Spring Boot: Provides support for implementing sagas and managing transactions.
Kafka Streams: For processing events and managing state transitions.

By using Kafka Connect, we can achieve data synchronization between services without modifying the existing service code.

Tools and Technologies

1. Spring Boot and Spring Cloud
   - Purpose: Frameworks for building microservices with Java.
   - Benefits: Provides a comprehensive set of tools for microservices development, including configuration management, service discovery, and circuit breakers.

2. H2 Database
   - Purpose: In-memory database for development and testing.
   - Benefits: Lightweight and easy to integrate with Spring Boot.

3. Kubernetes (K8S)
   - Purpose: Container orchestration platform.
   - Benefits: Manages deployment, scaling, and operations of application containers across clusters of hosts.

4. Spring Security
   - Purpose: Provides authentication and authorization.
   - Benefits: Ensures secure access to endpoints.

5. Docker
   - Purpose: Containerization platform.
   - Benefits: Simplifies deployment and ensures consistency across environments.

### Implementation Steps

1. Create Order Service
   - Endpoints: Create Order, List Orders, Delete Order.
   - Logic: Check TRY asset’s usable size before creating an order. Update usable size on order creation and cancellation.

2. Asset Service
   - Endpoints: List Assets.
   - Logic: Manage asset information for customers.

3. Transaction Service
   - Endpoints: Deposit Money, Withdraw Money.
   - Logic: Handle financial transactions, update asset sizes accordingly.

4. Authentication Service
   - Endpoints: Login, Admin actions.
   - Logic: Ensure secure access to services, manage user sessions.

5. Admin Service
   - Endpoints: Match Orders.
   - Logic: Match pending orders, update asset sizes for both TRY and bought assets.

Deployment

1. Containerize Services
   - Use Docker to create images for each microservice.

2. Deploy to Kubernetes
   - Use Kubernetes to manage and scale your microservices.

3. CI/CD Pipeline
   - Implement a CI/CD pipeline using tools like Jenkins or GitHub Actions to automate the build and deployment process.

  Sample Output with Axon event server each microservice have its own db command segragation, seperation of conserns as well as saga implemented
   - ![image](https://github.com/user-attachments/assets/9a05856a-7142-42c3-b67a-9a70a441c95f)

   - ![image](https://github.com/user-attachments/assets/5e864337-2c6b-4b3c-810f-b2627992af11)

