import { createAsyncThunk } from '@reduxjs/toolkit';
import { type Homework } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const loadHomework = createAsyncThunk<Homework, number, ThunkConfig<string>>(
  'homework/loadHomework',
  async (id, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getHomework(id);

      return data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
