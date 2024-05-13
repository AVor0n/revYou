import { createSlice } from '@reduxjs/toolkit';
import { loadMySolutions, loadSolutionsForReview } from '../services';
import { type SolutionSchema } from '../types';

const initialState: SolutionSchema = {
  mySolutions: null,
  solutionsForReview: null,
  error: '',
};

export const solutionSlice = createSlice({
  name: 'Solution',
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder.addCase(loadMySolutions.fulfilled, (state, { payload }) => {
      state.mySolutions = payload;
    });
    builder.addCase(loadMySolutions.rejected, (state, { payload }) => {
      state.mySolutions = null;
      state.error = payload ?? '';
    });
    builder.addCase(loadSolutionsForReview.fulfilled, (state, { payload }) => {
      state.solutionsForReview = payload;
    });
    builder.addCase(loadSolutionsForReview.rejected, (state, { payload }) => {
      state.solutionsForReview = null;
      state.error = payload ?? '';
    });
  },
});

export const { actions: solutionActions, reducer: solutionReducer } = solutionSlice;
