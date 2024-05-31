import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { signInUser } from '../services/signInUser.thunk';
import { signUpUser } from '../services/signUpUser.thunk';
import { type UserSchema } from '../types/User.types';

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
  },
  extraReducers(builder) {
    builder
      .addCase(signInUser.fulfilled, (state, { payload }) => {
        state.error = '';
        state.authData = payload;
        toaster.remove('authError');
      })
      .addCase(signInUser.rejected, (state, action: PayloadAction<string | undefined>) => {
        state.error = action.payload || '';
      })
      .addCase(signUpUser.fulfilled, state => {
        state.error = '';
        toaster.remove('authError');
      })
      .addCase(signUpUser.rejected, (state, action: PayloadAction<string | undefined>) => {
        state.error = action.payload || '';
      });
  },
});

export const { actions: userActions, reducer: userReducer } = userSlice;
