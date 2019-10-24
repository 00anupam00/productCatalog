#!/bin/bash
echo "Deploying Product Catalog App ..."
echo "Installing ..."
mvn clean install
echo "Starting Product Catalog Server ..."
mvn spring-boot:run &
echo "Started."