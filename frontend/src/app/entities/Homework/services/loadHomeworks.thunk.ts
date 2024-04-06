import { createAsyncThunk } from '@reduxjs/toolkit';
import { type GetHomework, type HomeworkList } from '@domains/__generated__';
import { type ThunkConfig } from 'app/providers';

export const loadHomeworks = createAsyncThunk<GetHomework[], undefined, ThunkConfig<string>>(
  'homework/loadHomeworks',
  async (_params, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.get<HomeworkList>('/homeworks');

      if (!data.data) {
        throw new Error('No data');
      }

      return data.data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
