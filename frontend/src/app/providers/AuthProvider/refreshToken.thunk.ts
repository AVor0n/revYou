import { createAsyncThunk } from '@reduxjs/toolkit';
import { type AxiosError } from 'axios';
import { type RefreshAccessTokenRequestDto, type SignInResponse } from '@domains';
import { type ThunkConfig } from 'app/providers';

export const refreshAuthToken = createAsyncThunk<SignInResponse, RefreshAccessTokenRequestDto, ThunkConfig<string>>(
  'auth/refresh',
  async ({ refreshToken }, { extra, rejectWithValue }) => {
    try {
      const resp = await extra.api.refreshAccessToken({ refreshToken });

      return resp.data;
    } catch (e) {
      return rejectWithValue((e as AxiosError).message);
    }
  },
);
