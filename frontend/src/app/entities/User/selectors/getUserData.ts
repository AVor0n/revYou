import { type StoreSchema } from 'app';

export const getUserError = (state: StoreSchema) => state.user.error || '';
export const getUserRole = (state: StoreSchema) => state.user.authData?.role || null;
export const getAuthData = (state: StoreSchema) => state.user.authData || null;
