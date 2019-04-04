FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=gateway/target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 13000
ENTRYPOINT ["java","-cp","app:app/lib/*","com/gateway/GatewayApplication"]