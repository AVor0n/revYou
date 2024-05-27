import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';

export const deleteHomework = createAsyncThunk<null, number, ThunkConfig<string>>(
  'homework/deleteHomeworks',
  async (id, { extra, rejectWithValue }) => {
    try {
      await extra.api.deleteHomework(id);
      return null;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
