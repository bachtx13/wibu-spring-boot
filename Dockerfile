#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean install

#
# Package stage
#
FROM eclipse-temurin:17-jdk
COPY --from=build /target/wibu-spring-boot-0.0.1-SNAPSHOT.jar wibu-spring-boot.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","wibu-spring-boot-0.0.1-SNAPSHOT"]