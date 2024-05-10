import { settings } from '@gravity-ui/date-utils';
import { ThemeProvider, ToasterComponent, ToasterProvider } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { type Role, userActions } from './entities';
import { useAppDispatch } from './hooks';
import { RouterProvider } from './providers';

import './global.css';

settings.loadLocale('ru').then(() => {
  settings.setLocale('ru');
});

export const App = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(userActions.setUserRole(localStorage.getItem('role') as Role));
  }, [dispatch]);

  return (
    <ThemeProvider theme="light">
      <ToasterProvider>
        <RouterProvider />

        <ToasterComponent />
      </ToasterProvider>
    </ThemeProvider>
  );
};
