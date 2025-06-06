# Use the Eclipse Temurin official image (alpine ou slim poderia ser mais leve, mas openjdk:22 direto tá ok)
FROM openjdk:22

# Create and change to the app directory.
WORKDIR /app

# Copiar somente os arquivos necessários para o build, evitando copiar arquivos desnecessários
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Dar permissão de execução ao mvnw
RUN chmod +x mvnw

# Build the app usando o Maven Wrapper
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

# Expor a porta
EXPOSE 8080
ENV PORT=8080

# Comando para rodar a aplicação Quarkus
CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]
