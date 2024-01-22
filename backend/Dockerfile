FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG SPRING_PROFILES_ACTIVE=aks.properties
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=aks","/app.jar"]