FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY target/adminservice-0.0.1-SNAPSHOT.jar admin-service.jar
ENTRYPOINT ["java","-jar","/admin-service.jar"]