FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=backend/target/arcgis-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /usr/local/lib/application.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/application.jar"]