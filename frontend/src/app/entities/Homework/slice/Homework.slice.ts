import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { loadHomeworks } from '../services';
import { type HomeworkSchema } from '../types';

const initialState: HomeworkSchema = {
  homeworkForEdit: null,
  homeworks: null,
  error: '',
};

export const homeworkSlice = createSlice({
  name: 'Homework',
  initialState,
  reducers: {
    setHomeworkForEdit(state, { payload }: PayloadAction<HomeworkSchema['homeworkForEdit']>) {
      state.homeworkForEdit = payload;
    },
  },
  extraReducers(builder) {
    builder.addCase(loadHomeworks.fulfilled, (state, { payload }: PayloadAction<HomeworkSchema['homeworks']>) => {
      state.homeworks = payload;
    });
    builder.addCase(loadHomeworks.rejected, (state, { payload }: PayloadAction<string | undefined>) => {
      state.error = payload ?? '';
    });
  },
});

export const { actions: homeworkActions, reducer: homeworkReducer } = homeworkSlice;
