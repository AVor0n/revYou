import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

export const deleteHomework = createAsyncThunk<null, number, ThunkConfig<string>>(
  'homework/loadHomeworks',
  async (id, { extra, rejectWithValue }) => {
    try {
      await extra.api.delete(`/homeworks/${id}`);
      return null;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
