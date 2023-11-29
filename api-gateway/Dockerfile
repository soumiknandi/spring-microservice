FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR extracted
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM eclipse-temurin:17-jre-alpine
WORKDIR application
COPY --from=builder extracted/target/*.jar extracted/*.jar
#COPY --from=builder extracted/spring-boot/ ./
#COPY --from=builder extracted/snapshot-dependencies/ ./
#COPY --from=builder extracted/application/ ./
EXPOSE 8090
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
ENTRYPOINT ["java", "-jar", "extracted/*.jar" ]