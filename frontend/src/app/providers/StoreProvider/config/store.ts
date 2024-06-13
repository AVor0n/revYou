import { configureStore } from '@reduxjs/toolkit';
import { setupListeners } from '@reduxjs/toolkit/query';
import { homeworkSlice, userSlice } from '@entities';
import { api } from 'shared/api';

export const store = configureStore({
  reducer: {
    user: userSlice.reducer,
    homework: homeworkSlice.reducer,
    [api.reducerPath]: api.reducer,
  },
  middleware: getDefaultMiddleware => getDefaultMiddleware().concat(api.middleware),
});

export type AppDispatch = typeof store.dispatch;
export type StoreSchema = ReturnType<typeof store.getState>;

setupListeners(store.dispatch);
