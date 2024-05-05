import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { GetApi } from 'app/api';
import { userReducer, homeworkReducer, reviewReducer, solutionReducer } from 'app/entities';
import { type ThunkExtraArg, type StoreSchema } from './StoreSchema';

export function createReduxStore(initialState?: StoreSchema) {
  const rootReducers = {
    user: userReducer,
    homework: homeworkReducer,
    review: reviewReducer,
    solution: solutionReducer,
  };

  const extraArg: ThunkExtraArg = {
    api: GetApi(),
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
