import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import type { Homework } from '@shared/api';

interface HomeworkSchema {
  selectedHomework: Homework | null;
  homeworks: Homework[] | null;
  error: string;
}

const initialState: HomeworkSchema = {
  selectedHomework: null,
  homeworks: null,
  error: '',
};

export const homeworkSlice = createSlice({
  name: 'Homework',
  initialState,
  reducers: {
    setSelectedHomework(state, { payload }: PayloadAction<HomeworkSchema['selectedHomework']>) {
      state.selectedHomework = payload;
    },
    clear() {
      return initialState;
    },
  },
});

export const { actions: homeworkActions, reducer: homeworkReducer } = homeworkSlice;
