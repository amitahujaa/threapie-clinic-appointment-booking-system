FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/threapie-clinic-appointment-booking-system-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]