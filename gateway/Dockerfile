FROM maven:alpine
WORKDIR /usr/src/app
COPY pom.xml .

COPY src/lib/common-1.0-SNAPSHOT.jar src/lib/common-1.0-SNAPSHOT.jar
RUN mvn install
COPY . .
#FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/gateway-1.0-SNAPSHOT.jar /app/gateway-1.0-SNAPSHOT.jar
EXPOSE 13000
MAINTAINER bradleywilliamsgb@gmail.com
ENTRYPOINT ["java", "-XshowSettings:vm", "-jar", "/app/gateway-1.0-SNAPSHOT.jar",  "--spring.profiles.active=docker"  ]