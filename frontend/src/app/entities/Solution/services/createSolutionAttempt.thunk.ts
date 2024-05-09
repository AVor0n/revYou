import { createAsyncThunk } from '@reduxjs/toolkit';
import { type SolutionAttempt } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const createSolutionAttempt = createAsyncThunk<SolutionAttempt, number, ThunkConfig<string>>(
  'solution/createSolutionAttempt',
  async (homeworkId, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.createSolutionAttempt(homeworkId);

      return data;
    } catch (e) {
      return rejectWithValue(String(e));
    }
  },
);
