import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

export const startReview = createAsyncThunk<null, { reviewId: number; homeworkId: number }, ThunkConfig<string>>(
  'solution/startReview',
  async ({ reviewId, homeworkId }, { extra, rejectWithValue }) => {
    try {
      await extra.api.startReview(reviewId, String(homeworkId));

      return null;
    } catch (e) {
      return rejectWithValue(String(e));
    }
  },
);
