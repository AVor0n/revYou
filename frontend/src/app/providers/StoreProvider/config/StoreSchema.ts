import { type Api } from '@domains';
import { type UserSchema, type HomeworkSchema } from 'app/entities';

export interface StoreSchema {
  user: UserSchema;
  homework: HomeworkSchema;
}

export interface ThunkExtraArg {
  api: Api;
}

export interface ThunkConfig<T> {
  rejectValue: T;
  extra: ThunkExtraArg;
  state: StoreSchema;
}
