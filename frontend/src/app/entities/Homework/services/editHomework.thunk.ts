import { createAsyncThunk } from '@reduxjs/toolkit';
import { type HomeworkPatch } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const editHomework = createAsyncThunk<null, [number, HomeworkPatch], ThunkConfig<string>>(
  'homework/editHomework',
  async ([id, homework], { extra, rejectWithValue }) => {
    try {
      await extra.api.updateHomework(id, homework);
      return null;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
