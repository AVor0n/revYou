## Запуск

### Через Intellij IDEA Ultimate
1. Запустить, используя конфигурацию BackendApplicationDev

### Через консоль
1. Установить [Java JDK 21](https://www.oracle.com/cis/java/technologies/downloads/)
2. Установить [Maven](https://maven.apache.org/download.cgi)
3. Запустить приложение
```shell
mvn spring-boot:run
```

### Через Docker
1. Установить [Docker](https://docs.docker.com/get-docker/)
2. Собрать образ
```shell
docker build -t cross-review-back .
```
3. Запустить контейнер из образа
```shell
docker run -p 8080:8080 cross-review-back
```
4. Приложение доступно по адресу http://localhost:8080
