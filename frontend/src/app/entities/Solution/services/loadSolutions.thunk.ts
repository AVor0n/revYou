import { createAsyncThunk } from '@reduxjs/toolkit';
import { type Solution } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const loadSolutions = createAsyncThunk<Solution[], number, ThunkConfig<string>>(
  'solution/loadSolutions',
  async (homeworkId, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.readSolutions(homeworkId);

      return data.data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
