import { createContext, useContext } from 'react';

export const AuthContext = createContext<{
  refreshToken: string | null;
  setRefreshToken: (newToken: string) => void;
  accessToken: string | null;
  setAccessToken: (newToken: string) => void;
}>({
  refreshToken: null,
  setRefreshToken: () => {},
  accessToken: null,
  setAccessToken: () => {},
});

export const useAuth = () => useContext(AuthContext);
