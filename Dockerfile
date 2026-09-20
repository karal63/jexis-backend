FROM gradle:9.1.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle --no-daemon bootJar -x test

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
