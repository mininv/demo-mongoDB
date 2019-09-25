#!/usr/bin/env bash
mvn clean install -DskipTests
mvn package -DskipTests
docker build -t demo-spring-mongo
docker run -d -p 27000:27017 --name mongo mongo
docker run -p 8090:8080 --name springboot-mongo --link=mongo demo-spring-mongo
