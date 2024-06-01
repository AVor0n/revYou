import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type HomeworkPost } from '@domains';
import { type ThunkConfig } from 'app/providers';
import type { AxiosError } from 'axios';

export const createHomework = createAsyncThunk<null, HomeworkPost, ThunkConfig<string>>(
  'homework/createHomework',
  async (homework, { extra, rejectWithValue }) => {
    try {
      await extra.api.createHomework(homework);
      return null;
    } catch (e) {
      const error = (e as AxiosError).response?.data as { message: string };
      toaster.add({
        name: 'createHomework',
        title: 'Ошибка создания',
        content: error.message,
        theme: 'danger',
      });
      return rejectWithValue(error.message);
    }
  },
);
