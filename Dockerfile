# docker images
FROM eclipse-temurin:17 AS builder

WORKDIR workspace

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} catalog-service.jar

RUN java -Djarmode=layertools -jar catalog-service.jar extract

FROM eclipse-temurin:17

# security

RUN useradd spring

USER spring

WORKDIR workspace

# layer jar | uber jar

COPY --from=builder workspace/dependencies/ ./

COPY --from=builder workspace/spring-boot-loader/ ./

COPY --from=builder workspace/snapshot-dependencies/ ./

COPY --from=builder workspace/application/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]