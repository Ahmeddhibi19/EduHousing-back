# Use a Java runtime as a base image
FROM adoptopenjdk/openjdk17:alpine-jre

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file from the target directory into the container
COPY target/*.jar app.jar

# Expose port 8080 to the outside world
EXPOSE 8081

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]