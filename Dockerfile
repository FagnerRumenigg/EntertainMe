FROM openjdk:21
ADD target/entertainme-api.jar entertainme.jar
ENTRYPOINT ["java", "-jar", "entertainme.jar"]