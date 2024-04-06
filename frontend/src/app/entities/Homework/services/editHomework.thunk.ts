import { createAsyncThunk } from '@reduxjs/toolkit';
import { type ThunkConfig } from 'app/providers';
import type { PatchHomework } from '@domains/__generated__';

export const editHomework = createAsyncThunk<null, [number, PatchHomework], ThunkConfig<string>>(
  'homework/editHomework',
  async ([id, homework], { extra, rejectWithValue }) => {
    try {
      await extra.api.patch(`/homeworks/${id}`, homework);
      return null;
    } catch (error) {
      return rejectWithValue(String(error));
    }
  },
);
