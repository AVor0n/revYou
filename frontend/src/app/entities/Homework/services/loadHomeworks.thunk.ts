import { createAsyncThunk } from '@reduxjs/toolkit';
import { type Homework } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const loadHomeworks = createAsyncThunk<Homework[], undefined, ThunkConfig<string>>(
  'homework/loadHomeworks',
  async (_params, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getHomeworks();

      return data.data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
