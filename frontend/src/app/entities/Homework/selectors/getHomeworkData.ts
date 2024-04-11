import { type StoreSchema } from 'app';

export const getHomeworks = (state: StoreSchema) => state.homework.homeworks;
export const getHomeworkForEdit = (state: StoreSchema) => state.homework.homeworkForEdit;
export const getSelectedHomework = (state: StoreSchema) => state.homework.selectedHomework;
