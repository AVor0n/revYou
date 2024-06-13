import { type PropsWithChildren } from 'react';
import { Provider } from 'react-redux';
import { store } from '../config/store';

export const StoreProvider = ({ children }: PropsWithChildren) => <Provider store={store}>{children}</Provider>;
