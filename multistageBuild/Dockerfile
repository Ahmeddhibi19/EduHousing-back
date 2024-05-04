FROM alpine/git as clone
WORKDIR /app
RUN git clone http://gitlab.example.com/pipeline/app.git

FROM maven:3.8.3-openjdk-17-alpine as build
WORKDIR /app
COPY --from=clone /app/EduHousing-back /app
RUN mvn clean install -DskipTests

FROM openjdk:17-slim-buster
CMD mkdir /jar
COPY --FROM=build /app/target/*.jar /jar/
EXPOSE 8080
