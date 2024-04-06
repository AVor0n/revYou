import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';
import type { PostHomework } from '@domains/__generated__';

export const createHomework = createAsyncThunk<null, PostHomework, ThunkConfig<string>>(
  'homework/createHomework',
  async (homework, { extra, rejectWithValue }) => {
    try {
      await extra.api.post(`/homeworks`, homework);
      return null;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
