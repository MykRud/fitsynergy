FROM openjdk:17
EXPOSE 9000
ADD target/fitsynergy-0.0.1-SNAPSHOT.jar fitsynergy-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/fitsynergy-0.0.1-SNAPSHOT.jar"]