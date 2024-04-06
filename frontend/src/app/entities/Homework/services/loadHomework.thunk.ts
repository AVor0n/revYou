import { createAsyncThunk } from '@reduxjs/toolkit';
import { type GetHomework } from '@domains/__generated__';
import { type ThunkConfig } from 'app/providers';

export const loadHomework = createAsyncThunk<GetHomework, string, ThunkConfig<string>>(
  'homework/loadHomework',
  async (id, { extra, rejectWithValue }) => {
    try {
      const { data } = await extra.api.get<GetHomework>(`/homeworks/${id}`);

      return data;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
