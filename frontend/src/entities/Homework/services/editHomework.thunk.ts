import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type HomeworkPatch } from '@api';
import { type ThunkConfig } from 'app/providers';
import type { AxiosError } from 'axios';

export const editHomework = createAsyncThunk<null, [number, HomeworkPatch], ThunkConfig<string>>(
  'homework/editHomework',
  async ([id, homework], { extra, rejectWithValue }) => {
    try {
      await extra.api.updateHomework(id, homework);
      return null;
    } catch (e) {
      const error = (e as AxiosError).response?.data as { message: string };
      toaster.add({
        name: 'editHomework',
        title: 'Ошибка редактирования',
        content: error.message,
        theme: 'danger',
      });
      return rejectWithValue(String(error));
    }
  },
);
