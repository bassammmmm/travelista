#as build separate the build environment from the final runtime environment
FROM openjdk:19-jdk-alpine3.14 as build

LABEL maintainer="bassamemad000@gmail.com"

#specify the directory instead of using run cd multiple times
#used one time
WORKDIR /app

#copying the maven files and dependencies from local file to image
#using copy instead of add because add is more complex and powerful
#copy makes the file easier to understand
COPY mvnw .mvn pom.xml ./
COPY src ./src/

#download all maven dependencies then
#build the application maven package and skip tests because tests slow down the build process
#then removes the unnecessary cached files from the maven repository to reduce size of image
RUN ./mvnw dependency:go-offline && \
    ./mvnw package -DskipTests && \
    rm -rf ~/.m2

#final image
FROM openjdk:19-jdk-alpine3.14

#copying the built jar file above to the final image
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
