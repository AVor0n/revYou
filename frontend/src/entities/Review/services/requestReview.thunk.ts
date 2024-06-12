import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

export const requestReview = createAsyncThunk<null, number, ThunkConfig<string>>(
  'solution/requestReview',
  async (homeworkId, { extra, rejectWithValue }) => {
    try {
      await extra.api.createReview(homeworkId);

      return null;
    } catch (e) {
      return rejectWithValue(String(e));
    }
  },
);
