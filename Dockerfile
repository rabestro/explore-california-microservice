FROM openjdk:17
WORKDIR /
ADD build/libs/explore-california-2.0.0-SNAPSHOT.jar //
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=default", "/explore-california-2.0.0-SNAPSHOT.jar"]
