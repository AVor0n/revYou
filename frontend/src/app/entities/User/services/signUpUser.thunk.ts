import { toaster } from '@gravity-ui/uikit/toaster-singleton';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { type AxiosError } from 'axios';
import { type SignInResponse, type SignInRequest } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const signUpUser = createAsyncThunk<SignInResponse, SignInRequest, ThunkConfig<string>>(
  'user/signUpUser',
  async (data, thunkApi) => {
    const { extra, rejectWithValue } = thunkApi;

    try {
      const response = await extra.api.signIn(data);

      if (!response.data.accessToken) {
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
