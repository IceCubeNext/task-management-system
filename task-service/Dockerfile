FROM amazoncorretto:17
COPY ./target/*.jar app.jar
#ENV TZ="Europe/Moscow"
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]