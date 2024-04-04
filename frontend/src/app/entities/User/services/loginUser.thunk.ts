import { createAsyncThunk } from '@reduxjs/toolkit';
import { type AuthSchema } from '@pages/AuthPage';
import { type ThunkConfig } from 'app/providers';
import { type UserAuthDataType } from '../types/User.types';

export const loginUser = createAsyncThunk<UserAuthDataType, AuthSchema, ThunkConfig<string>>(
  'user/loginUser',
  ({ login, password }, thunkApi) => {
    const { rejectWithValue } = thunkApi;

    try {
      // const response = await extra.api.get<UserSchema>(...);

      // if (!response.data) {
      //     throw new Error();
      // }

      // return response.data;
      return { login, password, role: 'Student' };
    } catch (e) {
      // console.error(e);
      return rejectWithValue('error');
    }
  },
);
