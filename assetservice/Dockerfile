FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/assetservice-0.0.1-SNAPSHOT.jar asset-service.jar
ENTRYPOINT ["java","-jar","/asset-service.jar"]
