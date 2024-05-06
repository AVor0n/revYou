import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { createSolution, loadSolution, loadSolutions } from '../services';
import { type SolutionSchema } from '../types';

const initialState: SolutionSchema = {
  solutionInfo: null,
  solutions: null,
  error: '',
};

export const solutionSlice = createSlice({
  name: 'Solution',
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder.addCase(createSolution.fulfilled, (state, { payload }: PayloadAction<SolutionSchema['solutionInfo']>) => {
      state.solutionInfo = payload;
    });
    builder.addCase(createSolution.rejected, (state, { payload }: PayloadAction<string | undefined>) => {
      state.error = payload ?? '';
      state.solutionInfo = null;
    });
    builder.addCase(loadSolution.fulfilled, (state, { payload }: PayloadAction<SolutionSchema['solutionInfo']>) => {
      state.solutionInfo = payload;
    });
    builder.addCase(loadSolution.rejected, (state, { payload }: PayloadAction<string | undefined>) => {
      state.error = payload ?? '';
      state.solutionInfo = null;
    });
    builder.addCase(loadSolutions.fulfilled, (state, { payload }: PayloadAction<SolutionSchema['solutions']>) => {
      state.solutions = payload;
    });
    builder.addCase(loadSolutions.rejected, (state, { payload }: PayloadAction<string | undefined>) => {
      state.error = payload ?? '';
    });
  },
});

export const { actions: solutionActions, reducer: solutionReducer } = solutionSlice;
