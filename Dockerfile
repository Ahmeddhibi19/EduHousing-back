# Use a Java runtime as a base image
FROM openjdk:17-jdk-alpine
CMD mkdir /jar
COPY target/EduHousing-0.0.1-SNAPSHOT.jar /jar/
EXPOSE 8081

