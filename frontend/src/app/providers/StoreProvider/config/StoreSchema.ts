import { type Api } from '@api';
import { type UserSchema, type HomeworkSchema, type ReviewSchema, type LectureSchema } from 'entities';
import { type SolutionSchema } from 'entities/Solution/types';

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
