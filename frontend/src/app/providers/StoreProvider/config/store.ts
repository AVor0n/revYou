import { configureStore } from '@reduxjs/toolkit';
import { setupListeners } from '@reduxjs/toolkit/query';
import { api } from 'shared/api';

export const store = configureStore({
  reducer: {
    [api.reducerPath]: api.reducer,
  },
  middleware: getDefaultMiddleware => getDefaultMiddleware().concat(api.middleware),
});

export type AppDispatch = typeof store.dispatch;

setupListeners(store.dispatch);
