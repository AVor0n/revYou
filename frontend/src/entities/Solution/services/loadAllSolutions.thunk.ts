import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ReviewInfoWrapper } from '@api';
import { type ThunkConfig } from 'app/providers';

export const loadAllSolutions = createAsyncThunk<ReviewInfoWrapper, number, ThunkConfig<string>>(
  'solution/loadAllSolutions',
  async (homeworkId, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getReviewsInfo(homeworkId);

      return data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
