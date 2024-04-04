import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { loginUser } from '../services/loginUser.thunk';
import { type UserAuthDataType, type UserSchema } from '../types/User.types';

const initialState: UserSchema = {
  authData: {
    login: '',
    password: '',
    role: 'Student',
  },
  isAuth: false,
  error: '',
};

export const userSlice = createSlice({
  name: 'User',
  initialState,
  reducers: {
    setTest: (state, action: PayloadAction<string>) => {
      state.authData.login = action.payload;
    },
  },
  extraReducers(builder) {
    builder
      .addCase(loginUser.fulfilled, (state, action: PayloadAction<UserAuthDataType>) => {
        state.authData = { ...action.payload };
        state.isAuth = true;
      })
      .addCase(loginUser.rejected, (state, action: PayloadAction<string | undefined>) => {
        state.error = action.payload || '';
      });
  },
});

export const { actions: userActions } = userSlice;
export const { reducer: userReducer } = userSlice;
