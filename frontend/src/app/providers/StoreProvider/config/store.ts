import { type Reducer, configureStore, type ReducersMapObject, combineReducers } from '@reduxjs/toolkit';
import { $api } from 'app/api';
import { userReducer } from 'app/entities';
import { type ThunkExtraArg, type StoreSchema } from './StoreSchema';

export function createReduxStore(initialState?: StoreSchema) {
  const rootReducers: ReducersMapObject<StoreSchema> = {
    user: userReducer,
  };

  const extraArg: ThunkExtraArg = {
    api: $api,
  };

  const store = configureStore({
    reducer: combineReducers(rootReducers) as Reducer<StoreSchema>,
    preloadedState: initialState,
    middleware: getDefaultMiddleware =>
      getDefaultMiddleware({
        thunk: {
          extraArgument: extraArg,
        },
      }),
  });

  return store;
}

export type AppDispatch = ReturnType<typeof createReduxStore>['dispatch'];
