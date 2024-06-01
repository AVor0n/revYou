import { settings } from '@gravity-ui/date-utils';
import { ToasterComponent, ToasterProvider } from '@gravity-ui/uikit';
import { useEffect } from 'react';
import { type FullUserInfo, userActions } from './entities';
import { useAppDispatch } from './hooks';
import { RouterProvider, ThemeProvider } from './providers';

import './global.css';

settings.loadLocale('ru').then(() => {
  settings.setLocale('ru');
});

export const App = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    const userInfoRaw = localStorage.getItem('userInfo');
    if (!userInfoRaw) return;
    try {
      const userInfo = JSON.parse(userInfoRaw) as FullUserInfo;
      dispatch(userActions.setUserInfo(userInfo));
    } catch {
      dispatch(userActions.setUserInfo(null));
    }
  }, [dispatch]);

  return (
    <ThemeProvider>
      <ToasterProvider>
        <RouterProvider />
        <ToasterComponent />
      </ToasterProvider>
    </ThemeProvider>
  );
};
