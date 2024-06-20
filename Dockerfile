# docker images
FROM amazoncorretto:17

WORKDIR workspace

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} catalog-service.jar

ENTRYPOINT ["java","-jar", "catalog-service.jar"]