FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN sed -i 's/\r$//' mvnw
RUN ./mvnw dependency:go-offline
COPY ./src ./src
COPY ./checkstyle ./checkstyle
RUN ./mvnw clean install -Dmaven.test.skip

FROM eclipse-temurin:21-jre-alpine
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
