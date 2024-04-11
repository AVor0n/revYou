import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { Api } from '@domains';
import { userReducer, homeworkReducer } from 'app/entities';
import { type ThunkExtraArg, type StoreSchema } from './StoreSchema';

export function createReduxStore(initialState?: StoreSchema) {
  const rootReducers = {
    user: userReducer,
    homework: homeworkReducer,
  };

  const extraArg: ThunkExtraArg = {
    api: new Api(),
  };

  return configureStore({
    reducer: combineReducers(rootReducers),
    preloadedState: initialState,
    middleware: getDefaultMiddleware =>
      getDefaultMiddleware({
        thunk: {
          extraArgument: extraArg,
        },
      }),
  });
}

export type AppDispatch = ReturnType<typeof createReduxStore>['dispatch'];
