import { type StoreSchema } from 'app';

export const getUserIsAuth = (state: StoreSchema) => state.user.isAuth || false;
export const getUserError = (state: StoreSchema) => state.user.error || '';
