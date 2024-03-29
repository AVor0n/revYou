import { type StoreSchema } from 'app';

export const getAuthLogin = (state: StoreSchema) => state.auth.login || '';
export const getAuthPassword = (state: StoreSchema) => state.auth.password || '';
