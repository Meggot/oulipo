FROM openjdk:8-jdk-alpine
VOLUME /tmp
ONBUILD RUN mvn install
ADD target/gateway-1.0-SNAPSHOT.jar /app/gateway-1.0-SNAPSHOT.jar
EXPOSE 13000
ENTRYPOINT ["java", "-XshowSettings:vm", "-jar", "/app/gateway-1.0-SNAPSHOT.jar",  "--spring.profiles.active=docker"  ]