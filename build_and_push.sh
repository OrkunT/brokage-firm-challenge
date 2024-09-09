#!/bin/bash

# Set your Docker Hub username
DOCKER_USERNAME="your-dockerhub-username"

# Array of microservices
services=("auth-service" "transactionservice" "orderservice" "assetservice" "adminservice")

# Loop through each service, build and push the Docker image
for service in "${services[@]}"; do
  echo "Building Docker image for $service..."
  docker build -t $DOCKER_USERNAME/$service ./$service
  echo "Pushing Docker image for $service..."
  docker push $DOCKER_USERNAME/$service
done
