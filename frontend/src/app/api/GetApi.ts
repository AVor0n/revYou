import { Api } from '@domains';

export const GetApi = () => {
  const api = new Api();

  return api;
};
