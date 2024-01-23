FROM gradle:8.5-jdk17-alpine AS builder
WORKDIR /
COPY . ./
RUN gradle assemble --no-daemon

FROM eclipse-temurin:17-jdk-alpine
COPY --from=builder build/libs/backend-0.0.1-SNAPSHOT.jar .
ARG SPRING_PROFILES_ACTIVE=aks.properties

ENTRYPOINT ["java","-Dspring.profiles.active=${env_spring_profiles}", "-jar","/backend-0.0.1-SNAPSHOT.jar"]