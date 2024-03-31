import { useSelector } from 'react-redux';
import { Route, Routes } from 'react-router-dom';
import { getUserIsAuth } from 'app/entities';
import { AuthPage, HomeworksPage, MainPage, NotFoundPage } from 'pages';
import { Layout } from '../../../components';
import { AuthRequire } from './AuthRequire';

const navLinks = [
  { title: 'Главная', href: '/', isNeedAuth: false, element: <MainPage /> },
  { title: 'Домашки', href: '/homeworks', isNeedAuth: true, element: <HomeworksPage /> },
  { title: 'Авторизация', href: '/auth', isNeedAuth: false, element: <AuthPage /> },
];

export const RouterProvider = () => {
  const isAuth = useSelector(getUserIsAuth);

  // Убираем из доступных роутов /auth
  const links = isAuth ? navLinks.filter(el => el.href !== '/auth') : navLinks;

  return (
    <Routes>
      <Route element={<Layout navLinks={isAuth ? links : links.filter(el => !el.isNeedAuth)} />}>
        {links.map(el => (
          <Route
            element={el.isNeedAuth ? <AuthRequire>{el.element}</AuthRequire> : el.element}
            path={el.href}
            key={el.href}
          />
        ))}
        <Route element={<NotFoundPage />} path="/*" />
      </Route>
    </Routes>
  );
};
