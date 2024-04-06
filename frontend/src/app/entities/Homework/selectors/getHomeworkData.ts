import { type StoreSchema } from 'app';

export const getHomeworks = (state: StoreSchema) => state.homework.homeworks;
export const getHomeworkForEdit = (state: StoreSchema) => state.homework.homeworkForEdit;
