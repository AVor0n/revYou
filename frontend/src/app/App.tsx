import { settings } from '@gravity-ui/date-utils';
import { ThemeProvider, ToasterComponent, ToasterProvider } from '@gravity-ui/uikit';
import { type AxiosError } from 'axios';
import { useEffect } from 'react';
import { GetApi } from './api';
import { type Role, userActions } from './entities';
import { useAppDispatch } from './hooks';
import { RouterProvider, refreshAuthToken, useAuth } from './providers';

import './global.css';

settings.loadLocale('ru').then(() => {
  settings.setLocale('ru');
});

export const App = () => {
  const dispatch = useAppDispatch();
  const { setRefreshToken, setAccessToken } = useAuth();

  useEffect(() => {
    GetApi().instance.interceptors.response.use(
      response => response,
      async (error: AxiosError) => {
        const originalRequest: (AxiosError['config'] & { _retry?: boolean }) | undefined = error.config;
        if (error.response?.status === 403 && originalRequest && !originalRequest._retry) {
          originalRequest._retry = true;
          const refreshToken = localStorage.getItem('refreshToken');
          if (refreshToken) {
            dispatch(refreshAuthToken({ refreshToken }))
              .unwrap()
              .then(({ accessToken, refreshToken: newRefreshToken, role }) => {
                setAccessToken(accessToken ?? '');
                setRefreshToken(newRefreshToken ?? '');
                userActions.setUserRole((role || null) as Role);
              });
          }
        }
        return Promise.reject(error);
      },
    );
  }, [dispatch, setAccessToken, setRefreshToken]);

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
