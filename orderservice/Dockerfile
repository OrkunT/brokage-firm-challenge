FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/orderservice-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java","-jar","/order-service.jar"]