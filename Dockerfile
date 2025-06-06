FROM openjdk:22

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copia só arquivos necessários para build (evita copiar código antes de build, se quiser otimizar)

RUN chmod +x ./mvnw

COPY src ./src

RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

EXPOSE 8080
ENV PORT=8080

CMD ["sh", "-c", "java -jar target/quarkus-app/quarkus-run.jar"]
