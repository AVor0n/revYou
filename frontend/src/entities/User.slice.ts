import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import type { SignInResponse } from '@shared/api';

export type FullUserInfo = Omit<SignInResponse, 'accessToken' | 'refreshToken'>;

export interface UserSchema {
  authData: SignInResponse | null;
  error: string;
}

const getInitialAuthInfo = () => {
  const userInfoRaw = localStorage.getItem('userInfo');
  if (!userInfoRaw) return null;
  try {
    return JSON.parse(userInfoRaw) as SignInResponse;
  } catch {
    return null;
  }
};

const initialState: UserSchema = {
  authData: getInitialAuthInfo(),
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
