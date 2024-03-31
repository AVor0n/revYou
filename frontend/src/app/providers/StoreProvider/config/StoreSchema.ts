import { type AxiosInstance } from 'axios';
import { type UserSchema } from 'app/entities';

export interface StoreSchema {
  user: UserSchema;
}

export interface ThunkExtraArg {
  api: AxiosInstance;
}

export interface ThunkConfig<T> {
  rejectValue: T;
  extra: ThunkExtraArg;
  state: StoreSchema;
}
