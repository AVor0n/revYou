import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';
import type { UserDetailDto } from '@domains';

export const loadAvailableReviewers = createAsyncThunk<UserDetailDto[], number, ThunkConfig<string>>(
  'solution/loadAvailableReviewers',
  async (homeworkId, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getAvailableReviewers(homeworkId);

      return data.data;
    } catch (e) {
      return rejectWithValue(String(e));
    }
  },
);
