FROM maven:3.9.9-eclipse-temurin-21 AS builder
LABEL project="e_commerce_backend"
LABEL service="apigateway"
LABEL authors="abrahamnyagar"

# set working dir to ./app
WORKDIR ./app
# copy the pom.xml file first to enable caching
COPY pom.xml .
# copy the rest of the application
COPY . .

#build the artifact
RUN mvn clean package -DskipTests

FROM debian:bookworm-slim
#set java home env in this image
ENV JAVA_HOME=/opt/java/openjdk

COPY --from=eclipse-temurin:21 $JAVA_HOME $JAVA_HOME

# add java and javac to path
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=builder /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]