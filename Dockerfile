# Maven build container 

FROM maven:3.8.5-openjdk-11 AS maven_build

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package -DskipTests

#pull base image

FROM openjdk
MAINTAINER pablito
ENV DB_HOST=localhost \
    DB_USERNAME=postgres \
    DB_PASSWORD=12345678

EXPOSE 8080
CMD java -jar /data/CoreCuentas-0.0.1-SNAPSHOT.jar
COPY --from=maven_build /tmp/target/CoreCuentas-0.0.1-SNAPSHOT.jar /data/CoreCuentas-0.0.1-SNAPSHOT.jar