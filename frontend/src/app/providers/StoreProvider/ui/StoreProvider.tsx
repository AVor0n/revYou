import { type PropsWithChildren } from 'react';
import { Provider } from 'react-redux';
import { createReduxStore } from '../config/store';
import { type StoreSchema } from '../config/StoreSchema';

interface StoreProviderProps extends PropsWithChildren {
  initialState?: StoreSchema;
}

export const StoreProvider = (props: StoreProviderProps) => {
  const { children, initialState } = props;

  const store = createReduxStore(initialState);

  return <Provider store={store}>{children}</Provider>;
};
