FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/Ahmeddhibi19/EduHousing-back.git

FROM maven:3.8.3-openjdk-17-alpine as build
WORKDIR /app
COPY --from=clone /app/EduHousing-back /app
RUN mvn clean install -DskipTests

FROM adoptopenjdk/openjdk17:alpine-jre
CMD mkdir /jar
COPY --FROM=build /app/target/*.jar /jar/
EXPOSE 8080
