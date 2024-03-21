import { ThemeProvider, ToasterComponent, ToasterProvider } from '@gravity-ui/uikit';
import './global.css';
import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { useEffect } from 'react';
import { NavLink } from 'react-router-dom';
import { RouterProvider } from './providers/RouterProvider/RouterProvider';

export const App = () => {
  useEffect(() => {
    toaster.add({
      name: 'hello',
      theme: 'success',
      title: 'Run successful',
      content: 'Hello from Frontend App!',
    });
  }, []);

  return (
    <ThemeProvider theme="light">
      <ToasterProvider>
        <div style={{ display: 'flex', gap: '10px' }}>
          <NavLink to="/">Главная</NavLink>
          <NavLink to="/auth">Авторизация</NavLink>
          <NavLink to="/homeworks">Домашки</NavLink>
        </div>
        <RouterProvider />
        <ToasterComponent />
      </ToasterProvider>
    </ThemeProvider>
  );
};
