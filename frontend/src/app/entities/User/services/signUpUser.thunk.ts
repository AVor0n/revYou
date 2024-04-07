import { toaster } from '@gravity-ui/uikit/toaster-singleton';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type AxiosError } from 'axios';
import { type SignUpSchema } from '@pages/AuthPage';
import { type ThunkConfig } from 'app/providers';
import { type UserAuthDataType } from '../types/User.types';

export const signUpUser = createAsyncThunk<UserAuthDataType, SignUpSchema, ThunkConfig<string>>(
  'user/signUpUser',
  async (data, thunkApi) => {
    const { extra, rejectWithValue } = thunkApi;

    try {
      const response = await extra.api.post<UserAuthDataType>('/auth/sign-up/', data);

      if (!response.data.email) {
        throw new Error('no data');
      }

      return response.data;
    } catch (e) {
      const error = (e as AxiosError).response?.data as { message: string };
      toaster.add({ name: 'authError', title: 'Ошибка авторизации', content: error.message, theme: 'danger' });
      return rejectWithValue(error.message);
    }
  },
);
