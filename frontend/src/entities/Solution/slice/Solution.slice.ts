import { createSlice } from '@reduxjs/toolkit';
import {
  createSolution,
  loadAllSolutions,
  loadAvailableReviewers,
  loadMySolutions,
  loadSolutionsForReview,
} from '../services';
import { type SolutionSchema } from '../types';

const initialState: SolutionSchema = {
  mySolutions: null,
  allSolutions: null,
  solutionsForReview: null,
  requestInProgress: {},
  availableReviewers: null,
  error: '',
};

export const solutionSlice = createSlice({
  name: 'Solution',
  initialState,
  reducers: {
    clear() {
      return initialState;
    },
  },
  extraReducers(builder) {
    builder.addCase(loadAvailableReviewers.fulfilled, (state, { payload }) => {
      state.availableReviewers = payload;
      state.requestInProgress.loadAvailableReviewers = false;
    });
    builder.addCase(loadAvailableReviewers.pending, state => {
      state.requestInProgress.loadAvailableReviewers = true;
    });
    builder.addCase(loadAvailableReviewers.rejected, state => {
      state.requestInProgress.loadAvailableReviewers = false;
    });
    builder.addCase(createSolution.fulfilled, state => {
      state.requestInProgress.sendSolution = false;
    });
    builder.addCase(createSolution.pending, state => {
      state.requestInProgress.sendSolution = true;
    });
    builder.addCase(createSolution.rejected, state => {
      state.requestInProgress.sendSolution = false;
    });
    builder.addCase(loadAllSolutions.fulfilled, (state, { payload }) => {
      state.allSolutions = payload.data.map(review => ({
        ...review,
        sourceCommitId: payload.sourceCommitId,
      }));
    });
    builder.addCase(loadAllSolutions.rejected, (state, { payload }) => {
      state.allSolutions = null;
      state.error = payload ?? '';
    });
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
