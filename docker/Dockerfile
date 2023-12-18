FROM openjdk:21

ARG APP_JAR=*.jar

COPY ${APP_JAR} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]