FROM openjdk:21
WORKDIR /app
ADD target/entertainme-api.jar entertainme.jar
ADD src/main/resources/application.yml application.yml
ENTRYPOINT ["java", "-jar", "entertainme.jar", "--spring.config.location=file:/app/application.yml"]
