## Запуск

### Через NodeJS и Yarn

1. Установить [NodeJS](https://nodejs.org/en)
2. Установить [yarn](https://yarnpkg-ko.github.io/en/docs/install)

```shell
npm install -g yarn
```

3. Поставить зависимости командой `yarn`
4. Запустить dev-server командой `yarn dev`
5. Приложение можно открыть по адресу http://localhost:3000

### Через Docker

1. Установить [Docker](https://docs.docker.com/get-docker/)
2. Собрать образ

```shell
docker build -t cross-review-front .
```

3. Запустить контейнер из образа

```shell
docker run -p 3000:80 cross-review-front
```

4. Приложение можно открыть по адресу http://localhost:3000

## Доступные команды

- `yarn` - установка зависимостей
- `yarn dev` - запуск dev-сервера
- `yarn build` - сборка проекта
- `yarn build:dev` - сборка dev-версии проекта, код не минифицируется
- `yarn preview` - запуск сервера с production версией
- `typecheck` - проверка типизации в проекте
- `yarn lint` - проверка кода линтерами eslint, stylelint
- `yarn lint:fix` - проверка и автоисправление кода линтерами eslint, stylelint
- `format` - проверка форматирования кода
- `format:fix` - автоисправление форматирования кода
