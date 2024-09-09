#!/bin/bash

# Check if the correct number of arguments is provided
if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <service-name> <port>"
  exit 1
fi

# Set the service name and port from the arguments
SERVICE_NAME=$1
PORT=$2

# Navigate to the service's folder
cd $SERVICE_NAME || { echo "Service folder $SERVICE_NAME not found"; exit 1; }

# Run the JAR file with the specified settings
java -jar target/$SERVICE_NAME.jar \
  --server.port=$PORT \
  --spring.application.name=$SERVICE_NAME \
  --spring.datasource.url=jdbc:h2:mem:testdb \
  --spring.datasource.driverClassName=org.h2.Driver \
  --spring.datasource.username=sa \
  --spring.datasource.password=password \
  --spring.jpa.database-platform=org.hibernate.dialect.H2Dialect \
  --spring.h2.console.enabled=true \
  --spring.h2.console.path=/h2-console \
  --spring.jpa.hibernate.ddl-auto=update \
  --feign.client.config.default.connectTimeout=5000 \
  --feign.client.config.default.readTimeout=5000 \
  --spring.security.user.name=admin \
  --spring.security.user.password=admin \
  --spring.kafka.bootstrap-servers=localhost:9092 \
  --axon.axonserver.suppressDownloadMessage=true

# Return to the base folder
cd ..
