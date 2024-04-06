interface ImportMetaEnv {
  /** Порт, на котором запускается фронт */
  readonly FRONT_PORT: number;
  /** Включить MockServiceWorker для имитации работы бэкенда */
  readonly FRONT_ENABLE_MOCK: boolean;
}
