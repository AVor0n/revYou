import { createAsyncThunk } from '@reduxjs/toolkit';
import { type Review } from '@api';
import { type ThunkConfig } from 'app/providers';

export const loadMySolutions = createAsyncThunk<Review[], number, ThunkConfig<string>>(
  'solution/loadMySolutions',
  async (homeworkId, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getMyReviews(homeworkId);

      return data.data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
