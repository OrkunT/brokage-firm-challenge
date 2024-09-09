#!/bin/bash

# Define an array of service folders and their corresponding YAML files
declare -A services
services=(
    ["adminservice"]="admin-service-deployment.yml admin-service-service.yml"
    ["assetservice"]="asset-service-deployment.yml asset-service-service.yml"
    ["orderservice"]="order-service-deployment.yml order-service-service.yml"
    ["transactionservice"]="transaction-service-deployment.yml transaction-service-service.yml"
    ["auth-service"]="auth-service-deployment.yml auth-service-service.yml"
)

# Loop through each service folder and apply the YAML files
for service in "${!services[@]}"; do
    echo "Applying Kubernetes configurations for $service..."
    for file in ${services[$service]}; do
        kubectl apply -f "$service/$file"
    done
done

echo "All configurations applied successfully."
