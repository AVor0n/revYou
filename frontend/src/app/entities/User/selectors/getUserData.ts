import { type StoreSchema } from 'app';

export const getUserError = (state: StoreSchema) => state.user.error || '';
