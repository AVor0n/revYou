import { createAsyncThunk } from '@reduxjs/toolkit';
import { type SolutionPatch } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const changeSolutionStatus = createAsyncThunk<null, [number, SolutionPatch], ThunkConfig<string>>(
  'solution/changeSolutionStatus',
  async ([id, solution], { extra, rejectWithValue }) => {
    try {
      await extra.api.updateSolution(id, solution);
      return null;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
