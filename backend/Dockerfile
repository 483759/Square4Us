# 윈도우 환경에서는 에러남 
# 리눅스 환경에서 테스트 할 것
FROM openjdk:8 AS build
WORKDIR /app
# COPY . .
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x gradlew
RUN ["./gradlew", "clean", "build", "--stacktrace", "--exclude-task", "test"]

FROM openjdk:8-jre-slim
# WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jar