# Use a Java runtime as a base image
FROM openjdk:17-slim-buster
CMD mkdir /jar
COPY target/*.jar /jar/
ENTRYPOINT ["java", "-jar"]
EXPOSE 8081

