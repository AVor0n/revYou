import { createAsyncThunk } from '@reduxjs/toolkit';
import { type CommentsThread, type ThreadPost } from '@api';
import { type ThunkConfig } from 'app/providers';

export const createThread = createAsyncThunk<CommentsThread, ThreadPost, ThunkConfig<string>>(
  'review/createThread',
  async (thread, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.startThread(thread);

      return data;
    } catch (e) {
      return rejectWithValue(String(e));
    }
  },
);
