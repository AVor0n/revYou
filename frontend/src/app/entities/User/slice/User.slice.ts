import { toaster } from '@gravity-ui/uikit/toaster-singleton';
import { type PayloadAction, createSlice } from '@reduxjs/toolkit';
import { signInUser } from '../services/signInUser.thunk';
import { signUpUser } from '../services/signUpUser.thunk';
import { type Role, type UserSchema } from '../types/User.types';

const initialState: UserSchema = {
  authData: {
    userId: 0,
    username: '',
    email: '',
  },
  role: null,
  error: '',
};

export const userSlice = createSlice({
  name: 'User',
  initialState,
  reducers: {
    setUserRole(state, { payload }: PayloadAction<Role>) {
      state.role = payload;
    },
  },
  extraReducers(builder) {
    builder
      .addCase(signInUser.fulfilled, (state, { payload }) => {
        state.error = '';
        state.role = (payload.role || null) as Role;
        toaster.remove('authError');
      })
      .addCase(signInUser.rejected, (state, action: PayloadAction<string | undefined>) => {
        state.error = action.payload || '';
      })
      .addCase(signUpUser.fulfilled, (state, action) => {
        state.authData = { ...action.payload };
        state.error = '';
        toaster.remove('authError');
      })
      .addCase(signUpUser.rejected, (state, action: PayloadAction<string | undefined>) => {
        state.error = action.payload || '';
      });
  },
});

export const { actions: userActions } = userSlice;
export const { reducer: userReducer } = userSlice;
