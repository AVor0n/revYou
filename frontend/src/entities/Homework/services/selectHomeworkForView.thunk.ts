import { createAsyncThunk } from '@reduxjs/toolkit';
import { type Homework } from '@api';
import { type ThunkConfig } from 'app/providers';

export const selectHomeworkForEdit = createAsyncThunk<Homework, number, ThunkConfig<string>>(
  'homework/selectHomeworkForEdit',
  async (id, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getHomework(id);

      return data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
