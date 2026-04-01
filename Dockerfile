FROM eclipse-temurin:21-jdk-alpine as build

WORKDIR /app
COPY . .
RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/usuario.jar

EXPOSE 8080

CMD ["java","-jar", "/app/usuario.jar"]
