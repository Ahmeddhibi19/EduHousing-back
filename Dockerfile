# Use a Java runtime as a base image
FROM openjdk:17-slim-buster

# Set the working directory in the container
WORKDIR /app
RUN mkdir /jar

# Copy the executable JAR file from the target directory into the container
COPY target/*.jar /jar/

# Expose port 8080 to the outside world
EXPOSE 8081


# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]