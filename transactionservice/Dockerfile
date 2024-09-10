FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/transactionservice-0.0.1-SNAPSHOT.jar transaction-service.jar
COPY ../orderservice/target/orderservice-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java","-cp","/transaction-service.jar;/order-service.jar","Main"]
