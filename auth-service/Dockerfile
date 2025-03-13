FROM 172.16.230.200:10000/amazonecorretto:11-alpine3.17
COPY ./auth-service/target/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=git", "-jar", "/app.jar"]