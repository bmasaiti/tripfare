#! /bin/bash

echo "building application jar"
./gradlew clean build
echo "Building application image \n"
echo "..................................................."
docker build -t tripfare .
echo "..................................................."

echo "Setting up date database."
docker-compose up
echo "..................................................."
