FROM openjdk:19 as build

LABEL maintainer="bassamemad000@gmail.com"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
