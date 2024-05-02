import { createAsyncThunk } from '@reduxjs/toolkit';
import { type Homework } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const selectHomework = createAsyncThunk<Homework, number, ThunkConfig<string>>(
  'homework/selectHomework',
  async (id, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getHomework(id);

      return data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
