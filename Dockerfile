FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/kafka-connect-monitor-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar