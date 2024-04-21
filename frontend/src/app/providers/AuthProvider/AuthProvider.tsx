import axios from 'axios';
import { type FC, type PropsWithChildren, useEffect, useMemo, useState } from 'react';
import { AuthContext } from './authContext';

export const AuthProvider: FC<PropsWithChildren> = ({ children }) => {
  // State to hold the authentication accessToken
  const [accessToken, SET_ACCESS_TOKEN] = useState(localStorage.getItem('accessToken'));
  const [refreshToken, SET_REFRESH_TOKEN] = useState(localStorage.getItem('refreshToken'));

  // Function to set the authentication accessToken
  const setAccessToken = (newToken: string) => {
    SET_ACCESS_TOKEN(newToken);
  };

  const setRefreshToken = (newToken: string) => {
    SET_REFRESH_TOKEN(newToken);
  };

  useEffect(() => {
    if (accessToken) {
      axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
      localStorage.setItem('accessToken', accessToken);
    } else {
      delete axios.defaults.headers.common.Authorization;
      localStorage.removeItem('accessToken');
    }
  }, [accessToken]);

  useEffect(() => {
    if (refreshToken) {
      localStorage.setItem('refreshToken', refreshToken);
    } else {
      localStorage.removeItem('refreshToken');
    }
  }, [refreshToken]);

  // Memoized value of the authentication context
  const contextValue = useMemo(
    () => ({
      refreshToken,
      setRefreshToken,
      accessToken,
      setAccessToken,
    }),
    [accessToken, refreshToken],
  );

  // Provide the authentication context to the children components
  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};
