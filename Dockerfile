
FROM adoptopenjdk/openjdk11:alpine-jre

ENV DB_HOST=db \
     DB_USERNAME=postgres \
     DB_PASSWORD=12345678

ARG JAR_FILE=target/CoreCuentas-0.0.1-SNAPSHOT.jar
EXPOSE 8080
WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]