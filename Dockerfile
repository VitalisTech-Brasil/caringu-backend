# Etapa de build
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e o diretório src para dentro do container
COPY pom.xml .
COPY src ./src

# Executa o build do Maven para gerar o JAR
RUN mvn clean package

# Segunda etapa: imagem final, mais leve
FROM eclipse-temurin:21-jdk-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa anterior para o container
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-Xms512m", "-Xmx10g", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]