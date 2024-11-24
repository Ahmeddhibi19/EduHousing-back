# Use a Java runtime as a base image
FROM openjdk:17-jdk-alpine
CMD mkdir /jar
<<<<<<< HEAD
COPY target/EduHousing-0.0.1-SNAPSHOT.jar /jar/
=======
COPY target/EduHousing-0.0.1.jar /jar/
>>>>>>> master
EXPOSE 8081

