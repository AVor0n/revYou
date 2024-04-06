import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { type GetHomework } from '@domains/__generated__';
import { loadHomeworks } from '../services';
import { type HomeworkSchema } from '../types';

const initialState: HomeworkSchema = {
  homeworks: null,
  error: '',
};

export const homeworkSlice = createSlice({
  name: 'Homework',
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder.addCase(loadHomeworks.fulfilled, (state, { payload }: PayloadAction<GetHomework[]>) => {
      state.homeworks = payload;
    });
    builder.addCase(loadHomeworks.rejected, (state, { payload }: PayloadAction<string | undefined>) => {
      state.error = payload ?? '';
    });
  },
});

export const { actions: homeworkActions, reducer: homeworkReducer } = homeworkSlice;
