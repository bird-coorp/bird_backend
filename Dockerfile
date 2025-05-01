# Etapa 1 - Build da aplicação
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia o projeto
COPY pom.xml .
COPY src ./src

# Realiza o build do projeto (gera um .jar)
RUN mvn clean package -DskipTests

# Etapa 2 - Imagem final para execução
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia o .jar da etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta usada pela aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
