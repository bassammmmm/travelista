FROM openjdk:19

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8000

CMD ["java", "-jar", "app.jar"]
