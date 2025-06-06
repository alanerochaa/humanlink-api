FROM openjdk:22

WORKDIR /app

COPY . ./

# Permissão para mvnw
RUN chmod +x ./mvnw

RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

EXPOSE 8080
ENV PORT=8080

CMD ["sh", "-c", "java -jar target/quarkus-app/quarkus-run.jar"]
