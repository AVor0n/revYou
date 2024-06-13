import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import type { SignInResponse } from '@shared/api';

export type FullUserInfo = Omit<SignInResponse, 'accessToken' | 'refreshToken'>;

export interface UserSchema {
  authData: FullUserInfo | null;
  error: string;
}

const initialState: UserSchema = {
  authData: null,
  error: '',
};

export const userSlice = createSlice({
  name: 'User',
  initialState,
  reducers: {
    setUserInfo(state, { payload }: PayloadAction<UserSchema['authData']>) {
      state.authData = payload;
    },
    clear() {
      return initialState;
    },
  },
});

export const { actions: userActions, reducer: userReducer } = userSlice;
