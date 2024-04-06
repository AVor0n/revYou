import { type AxiosInstance } from 'axios';
import { type UserSchema, type HomeworkSchema } from 'app/entities';

export interface StoreSchema {
  user: UserSchema;
  homework: HomeworkSchema;
}

export interface ThunkExtraArg {
  api: AxiosInstance;
}

export interface ThunkConfig<T> {
  rejectValue: T;
  extra: ThunkExtraArg;
  state: StoreSchema;
}
