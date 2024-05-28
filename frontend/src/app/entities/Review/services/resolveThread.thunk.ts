import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

export const resolveThread = createAsyncThunk<number, number, ThunkConfig<string>>(
  'review/resolveThread',
  async (threadId, { extra, rejectWithValue }) => {
    try {
      await extra.api.resolveCommentsThread(threadId);

      return threadId;
    } catch (e) {
      return rejectWithValue(String(e));
    }
  },
);
