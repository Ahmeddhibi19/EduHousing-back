# Use a Java runtime as a base image
FROM openjdk:17-jdk-alpine
CMD mkdir /jar
COPY target/EduHousing-0.0.1-RC.jar /jar/
EXPOSE 8081

