import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { createHomework, deleteHomework, editHomework, loadHomeworks, selectHomeworkForEdit } from '../services';
import { selectHomeworkForView } from '../services/selectHomeworkForEdit.thunk';
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
    builder.addCase(selectHomeworkForView.fulfilled, (state, { payload }) => {
      state.selectedHomework = payload;
    });
    builder.addCase(selectHomeworkForEdit.fulfilled, (state, { payload }) => {
      state.homeworkForEdit = payload;
    });
    builder.addCase(createHomework.fulfilled, state => {
      state.homeworks = null;
    });
    builder.addCase(editHomework.fulfilled, state => {
      state.homeworks = null;
    });
    builder.addCase(deleteHomework.fulfilled, state => {
      state.homeworks = null;
    });
    builder.addCase(loadHomeworks.rejected, (state, { payload }: PayloadAction<string | undefined>) => {
      state.error = payload ?? '';
    });
  },
});

export const { actions: homeworkActions, reducer: homeworkReducer } = homeworkSlice;
