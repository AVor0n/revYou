import { type FC, type PropsWithChildren } from 'react';
import { useSelector } from 'react-redux';
import { Navigate, useLocation } from 'react-router-dom';
import { getUserIsAuth } from 'app';

// Если не залогинен, то редирект на "/"
export const AuthRequire: FC<PropsWithChildren> = ({ children }) => {
  const authData = useSelector(getUserIsAuth);
  const location = useLocation();

  if (!authData) {
    return <Navigate to="/" state={{ from: location }} replace />;
  }

  return children;
};
