import { type StoreSchema } from 'app';

export const getAllLectures = (state: StoreSchema) => state.lecture.lectures;
