FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/_lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
ADD target/gateway-1.0-SNAPSHOT.jar /app/gateway-1.0-SNAPSHOT.jar
EXPOSE 13000
ENTRYPOINT ["java", "-XshowSettings:vm", "-jar", "/app/gateway-1.0-SNAPSHOT.jar",  "--spring.profiles.active=docker"  ]
#ENTRYPOINT ["java","-cp","app:app/lib/*","com/gateway/GatewayApplication"]