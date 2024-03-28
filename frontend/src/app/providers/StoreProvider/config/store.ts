import { configureStore } from '@reduxjs/toolkit';
import { authReducer } from '@pages/AuthPage';

export const store = configureStore({
  reducer: {
    auth: authReducer,
  },
});
