import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { loadSolutions } from '../services';
import { type SolutionSchema } from '../types';

const initialState: SolutionSchema = {
  solutions: null,
  error: '',
};

export const solutionSlice = createSlice({
  name: 'Solution',
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder.addCase(loadSolutions.fulfilled, (state, { payload }: PayloadAction<SolutionSchema['solutions']>) => {
      state.solutions = payload;
    });
    builder.addCase(loadSolutions.rejected, (state, { payload }: PayloadAction<string | undefined>) => {
      state.error = payload ?? '';
    });
  },
});

export const { actions: solutionActions, reducer: solutionReducer } = solutionSlice;
