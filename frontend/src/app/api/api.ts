import axios from 'axios';

export const $api = axios.create({
  baseURL: import.meta.env.FRONT_ENABLE_MOCK ? '/api' : 'http://localhost:8080',
});
