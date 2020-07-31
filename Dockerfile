FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/ChitraLimbu-0.0.1-SNAPSHOT.jar ChitraLimbu-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=production","-jar","ChitraLimbu-0.0.1-SNAPSHOT.jar"]
