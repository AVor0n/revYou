import axios from 'axios';
import { type FC, type PropsWithChildren, useEffect, useMemo, useState } from 'react';
import { AuthContext } from './authContext';

export const AuthProvider: FC<PropsWithChildren> = ({ children }) => {
  // State to hold the authentication token
  const [token, SET_TOKEN] = useState(localStorage.getItem('token'));

  // Function to set the authentication token
  const setToken = (newToken: string) => {
    SET_TOKEN(newToken);
  };

  useEffect(() => {
    if (token) {
      axios.defaults.headers.common.Authorization = `Bearer ${token}`;
      localStorage.setItem('token', token);
    } else {
      delete axios.defaults.headers.common.Authorization;
      localStorage.removeItem('token');
    }
  }, [token]);

  // Memoized value of the authentication context
  const contextValue = useMemo(
    () => ({
      token,
      setToken,
    }),
    [token],
  );

  // Provide the authentication context to the children components
  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};
