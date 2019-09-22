#!/bin/bash

mvn clean install -DskipTests
sudo docker rm chitralimbu
sudo docker image remove chitralimbu
sudo docker build -f Dockerfile -t chitralimbu .
sudo docker run -p 8080:8080 --name chitralimbu --link=mongodb chitralimbu
