import { Route, Routes } from 'react-router-dom';
import { AuthPage, HomeworksPage, MainPage, NotFoundPage } from 'pages';
import { Layout } from '../../components';

const navLinks = [
  { title: 'Главная', href: '/' },
  { title: 'Авторизация', href: '/auth' },
  { title: 'Домашки', href: '/homeworks' },
];

export const RouterProvider = () => (
  <Routes>
    <Route element={<Layout navLinks={navLinks} />}>
      <Route element={<MainPage />} path="/" />
      <Route element={<AuthPage />} path="/auth" />
      <Route element={<HomeworksPage />} path="/homeworks" />
      <Route element={<NotFoundPage />} path="/*" />
    </Route>
  </Routes>
);
