FROM openjdk:17
COPY target/message-service-twitterlike-0.0.1-SNAPSHOT.jar message-service-twitterlike-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "message-service-twitterlike-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
