import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { loadFileDiff, loadReview } from '../services';
import { type ReviewSchema } from '../types';

const initialState: ReviewSchema = {
  solutionInfo: null,
  filesTree: null,
  activeFilePath: '',
  sourceActiveFileContent: null,
  targetActiveFileContent: null,
  error: '',
};

export const reviewSlice = createSlice({
  name: 'Review',
  initialState,
  reducers: {
    setActiveFilePath(state, { payload }: PayloadAction<ReviewSchema['activeFilePath']>) {
      if (payload === state.activeFilePath) return;

      state.activeFilePath = payload;
      state.sourceActiveFileContent = null;
      state.targetActiveFileContent = null;
    },
  },
  extraReducers(builder) {
    builder.addCase(loadReview.fulfilled, (state, { payload }) => {
      state.filesTree = payload.diffFileTree;
      state.error = '';
    });
    builder.addCase(loadFileDiff.fulfilled, (state, { payload }) => {
      state.sourceActiveFileContent = payload.sourceFileContent;
      state.targetActiveFileContent = payload.targetFileContent;
    });
    builder.addCase(loadFileDiff.rejected, state => {
      state.activeFilePath = '';
      state.sourceActiveFileContent = null;
      state.targetActiveFileContent = null;
    });
    builder.addCase(loadReview.rejected, state => {
      state.error = 'Не удалось загрузить решение';
    });
  },
});

export const { actions: reviewActions, reducer: reviewReducer } = reviewSlice;
