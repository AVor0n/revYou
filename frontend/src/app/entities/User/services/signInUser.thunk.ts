import { toaster } from '@gravity-ui/uikit/toaster-singleton';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type AxiosError } from 'axios';
import { type SignInRequest, type SignInResponse } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const signInUser = createAsyncThunk<SignInResponse, SignInRequest, ThunkConfig<string>>(
  'user/signInUser',
  async (data, thunkApi) => {
    const { extra, rejectWithValue } = thunkApi;

    try {
      const response = await extra.api.signIn(data);

      if (!response.data.accessToken) {
        throw new Error('no data');
      }
      localStorage.setItem('role', response.data.role || '');

      return response.data;
    } catch (e) {
      const error = (e as AxiosError).response?.data as { message: string };
      toaster.add({ name: 'authError', title: 'Ошибка авторизации', content: error.message, theme: 'danger' });
      return rejectWithValue(error.message);
    }
  },
);
