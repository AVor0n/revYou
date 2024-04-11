import { createAsyncThunk } from '@reduxjs/toolkit';
import { type HomeworkPost } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const createHomework = createAsyncThunk<null, HomeworkPost, ThunkConfig<string>>(
  'homework/createHomework',
  async (homework, { extra, rejectWithValue }) => {
    try {
      await extra.api.createHomework(homework);
      return null;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
