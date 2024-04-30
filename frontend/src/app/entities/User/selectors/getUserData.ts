import { type StoreSchema } from 'app';

export const getUserError = (state: StoreSchema) => state.user.error || '';
export const getUserRole = (state: StoreSchema) => state.user.role || null;
