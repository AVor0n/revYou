import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { loadHomeworks, selectHomework } from '../services';
import { type HomeworkSchema } from '../types';

const initialState: HomeworkSchema = {
  selectedHomework: null,
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
    setSelectedHomework(state, { payload }: PayloadAction<HomeworkSchema['selectedHomework']>) {
      state.selectedHomework = payload;
    },
  },
  extraReducers(builder) {
    builder.addCase(loadHomeworks.fulfilled, (state, { payload }) => {
      state.homeworks = payload;
    });
    builder.addCase(selectHomework.fulfilled, (state, { payload }) => {
      state.selectedHomework = payload;
    });
    builder.addCase(loadHomeworks.rejected, (state, { payload }: PayloadAction<string | undefined>) => {
      state.error = payload ?? '';
    });
  },
});

export const { actions: homeworkActions, reducer: homeworkReducer } = homeworkSlice;
