# ChitraLimbu
Chitra Limbu (http://chitralimbu.com/) 

## Description

Portfolio page, this is where I experiment with Spring boot, Docker, Kubernetes and Mongodb. 
Deployed in AWS EC2 instance. 

### Technologies Used

```
Docker - As containerizaion tool
MongoDb - As a database
Spring - Backend Framework
Thymeleaf - As template engine
Bootstrap 3 - As CSS engine
```

### Features

```
Rest API
  -Secured with basic auth & Spring security
RestTemplate - Consuming Rest API
Spring Data Mongodb
Spring Security - Roles
GridFS (Mongodb - Storing media)
Actuator - Prometheus - Grafana (Monitoring)
```

### Upcoming

```
Kubernetes - As container orchestration tool
Kubernetes -> Sidecar -> Statefulset -> Mongodb Replicaset
-Used to scale and orchestrate Mongodb replia Set. When a mongodb node goes down, it should automatically get restarted
and be added back to the replicaset, while another node is elected as a primary node. 
-Should easily scale with a simple kubernetes command such as "kubectl scale --replicas=5 statefulset mongo".
Search functionality
Kafka - Messaging broker
HATEOAS - For Rest API
Jenkins - CI/CD Pipeline
```
