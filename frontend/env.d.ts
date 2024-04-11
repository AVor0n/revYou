interface ImportMetaEnv {
  /** Порт, на котором запускается фронт */
  readonly FRONT_PORT: string;
  /** Включить MockServiceWorker для имитации работы бэкенда */
  readonly FRONT_ENABLE_MOCK: 'true' | 'false';
  /** Адрес бэкенда к которому выполнять запросы */
  readonly FRONT_HOST: string;
  /** Url по которому можно скачать swagger-спецификацию api-контракта с бэком */
  readonly FRONT_SWAGGER_URL: string;
}
