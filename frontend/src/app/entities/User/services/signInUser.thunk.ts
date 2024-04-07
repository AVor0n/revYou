import { toaster } from '@gravity-ui/uikit/toaster-singleton';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type AxiosError } from 'axios';
import { type SignInSchema } from '@pages/AuthPage';
import { type ThunkConfig } from 'app/providers';

export const signInUser = createAsyncThunk<null, SignInSchema, ThunkConfig<string>>(
  'user/signInUser',
  async (data, thunkApi) => {
    const { extra, rejectWithValue } = thunkApi;

    try {
      const response = await extra.api.post('/auth/sign-in/', data);

      if (!response.data) {
        throw new Error('no data');
      }

      return null;
    } catch (e) {
      const error = (e as AxiosError).response?.data as { message: string };
      toaster.add({ name: 'authError', title: 'Ошибка авторизации', content: error.message, theme: 'danger' });
      return rejectWithValue(error.message);
    }
  },
);
