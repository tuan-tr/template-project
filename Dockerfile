FROM eclipse-temurin:17-jdk-alpine
RUN apk update && apk upgrade
RUN apk add --no-cache ca-certificates && update-ca-certificates
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT java -Duser.timezone=Asia/Ho_Chi_Minh -jar app.jar
