# Система управления банковскими картами

## Технологии
- Java 17+
- Spring Boot (Web, Security, Data JPA)
- PostgreSQL
- Liquibase
- JWT
- Swagger / OpenAPI
- Docker / Docker Compose
- JUnit / Mockito

## Запуск проекта

### 1. Клонирование репозитория
git clone https://github.com/<your-username>/bank-rest.git

### 2. Запуск базы данных
docker-compose up -d

### 3. Запуск приложения
./mvnw spring-boot:run

### После запуска можно открыть Swagger UI:
http://localhost:8080/swagger-ui/index.html

Там можно протестировать все эндпоинты без Postman.

- /api/auth/login – вход в систему
- /api/auth/register – регистрация
- /api/cards – управление картами
- /api/transfers – переводы между картами

### Для использования ADMIN команд необходим токен, который можно получить при выполнении логина в админ аккаунт - логин: admin, пароль: admin
