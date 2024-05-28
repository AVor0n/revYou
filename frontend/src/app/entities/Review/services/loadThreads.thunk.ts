import { createAsyncThunk } from '@reduxjs/toolkit';
import { type CommentsThread } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const loadThreads = createAsyncThunk<CommentsThread[], number, ThunkConfig<string>>(
  'review/loadThreads',
  async (reviewId, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getAllThreads(reviewId);

      return data.data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
