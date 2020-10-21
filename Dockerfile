FROM openjdk:15
VOLUME tmp
ADD target/itunesclone-0.0.1-SNAPSHOT.jar itunesclone-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]