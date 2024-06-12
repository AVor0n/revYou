import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { userReducer, homeworkReducer, reviewReducer, solutionReducer, lectureReducer } from 'entities';
import { Api } from 'shared/api';
import { type ThunkExtraArg, type StoreSchema } from './StoreSchema';

export function createReduxStore(initialState?: StoreSchema) {
  const rootReducers = {
    user: userReducer,
    homework: homeworkReducer,
    review: reviewReducer,
    lecture: lectureReducer,
    solution: solutionReducer,
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
