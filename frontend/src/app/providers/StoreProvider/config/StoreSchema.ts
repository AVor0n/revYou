import { type Api } from '@domains';
import { type UserSchema, type HomeworkSchema, type ReviewSchema } from 'app/entities';

export interface StoreSchema {
  user: UserSchema;
  homework: HomeworkSchema;
  review: ReviewSchema;
}

export interface ThunkExtraArg {
  api: Api;
}

export interface ThunkConfig<T> {
  rejectValue: T;
  extra: ThunkExtraArg;
  state: StoreSchema;
}
