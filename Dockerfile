FROM openjdk:17-jdk

COPY target/SurfSafly-0.0.1-SNAPSHOT.jar /surf-safely.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/surf-safely.jar"]

