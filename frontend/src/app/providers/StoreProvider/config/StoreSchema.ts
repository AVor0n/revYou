import { type Api } from '@domains';
import { type UserSchema, type HomeworkSchema, type ReviewSchema, type LectureSchema } from 'app/entities';
import { type SolutionSchema } from 'app/entities/Solution/types';

export interface StoreSchema {
  user: UserSchema;
  homework: HomeworkSchema;
  review: ReviewSchema;
  solution: SolutionSchema;
  lecture: LectureSchema;
}

export interface ThunkExtraArg {
  api: Api;
}

export interface ThunkConfig<T> {
  rejectValue: T;
  extra: ThunkExtraArg;
  state: StoreSchema;
}
