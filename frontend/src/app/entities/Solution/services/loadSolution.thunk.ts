import { createAsyncThunk } from '@reduxjs/toolkit';
import { type Solution } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const loadSolution = createAsyncThunk<Solution, number, ThunkConfig<string>>(
  'solution/loadSolution',
  async (homeworkId, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.readSolution(homeworkId);

      return data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
