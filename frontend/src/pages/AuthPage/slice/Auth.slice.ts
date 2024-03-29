import { createSlice, type PayloadAction } from '@reduxjs/toolkit';
import { type AuthSchema } from '../types/Auth.types';

const initialState: AuthSchema = {
  login: '',
  password: '',
};

export const authSlice = createSlice({
  name: 'Auth',
  initialState,
  reducers: {
    setAuthLogin: (state, action: PayloadAction<string>) => {
      state.login = action.payload;
    },
    setAuthPassword: (state, action: PayloadAction<string>) => {
      state.password = action.payload;
    },
  },
});

export const { actions: authActions } = authSlice;
export const { reducer: authReducer } = authSlice;
