import { createAsyncThunk } from '@reduxjs/toolkit';
import { type Review } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const loadSolutionsForReview = createAsyncThunk<Review[], number, ThunkConfig<string>>(
  'solution/loadSolutionsForReview',
  async (homeworkId, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getReviewsToDo(homeworkId);

      return data.data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
