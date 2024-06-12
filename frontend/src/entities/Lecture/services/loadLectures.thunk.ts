import { createAsyncThunk } from '@reduxjs/toolkit';
import { type Lecture } from '@api';
import { type ThunkConfig } from 'app/providers';

export const loadLectures = createAsyncThunk<Lecture[], undefined, ThunkConfig<string>>(
  'Lecture/loadLectures',
  async (_params, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.getAllLectures();

      return data.data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
