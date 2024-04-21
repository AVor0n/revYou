import { settings } from '@gravity-ui/date-utils';
import { ThemeProvider, ToasterComponent, ToasterProvider } from '@gravity-ui/uikit';
import { type AxiosError } from 'axios';
import { GetApi } from './api';
import { useAppDispatch } from './hooks';
import { AuthProvider, RouterProvider, refreshAuthToken, useAuth } from './providers';

import './global.css';

settings.loadLocale('ru').then(() => {
  settings.setLocale('ru');
});

export const App = () => {
  const dispatch = useAppDispatch();
  const { setRefreshToken, setAccessToken } = useAuth();

  GetApi().instance.interceptors.response.use(
    response => response,
    async (error: AxiosError) => {
      const originalRequest = error.config;
      // @ts-expect-error: Unreachable code error
      if (error.response?.status === 403 && !originalRequest?._retry) {
        // @ts-expect-error: Unreachable code error
        originalRequest._retry = true;
        const refreshToken = localStorage.getItem('refreshToken');
        if (refreshToken) {
          dispatch(refreshAuthToken({ refreshToken }))
            .unwrap()
            .then(({ accessToken, refreshToken: newRefreshToken }) => {
              setAccessToken(accessToken ?? '');
              setRefreshToken(newRefreshToken ?? '');
            });
        }
      }
      return Promise.reject(error);
    },
  );

  return (
    <ThemeProvider theme="light">
      <ToasterProvider>
        <AuthProvider>
          <RouterProvider />
        </AuthProvider>

        <ToasterComponent />
      </ToasterProvider>
    </ThemeProvider>
  );
};
