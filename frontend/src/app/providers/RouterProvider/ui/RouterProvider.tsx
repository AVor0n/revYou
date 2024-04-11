import { Route, Routes } from 'react-router-dom';
import { HomeworkDetailPage } from '@pages/HomeworkDetailPage';
import { AuthPage, HomeworksPage, NotFoundPage } from 'pages';
import { Layout } from '../../../components';
import { AuthRequire } from './AuthRequire';

const navLinks = [{ title: 'Домашки', href: '/homeworks', element: <HomeworksPage /> }];

export const RouterProvider = () => (
  <Routes>
    <Route element={<Layout />}>
      <Route index element={<AuthPage />} path="/auth" />
    </Route>

    <Route element={<Layout navLinks={navLinks} />}>
      <Route
        path="/*"
        element={
          <AuthRequire>
            <Routes>
              {navLinks.map(link => (
                <Route key={link.href} element={link.element} path={link.href} />
              ))}
              <Route element={<HomeworksPage />} path="/homeworks/create?" />
              <Route element={<HomeworksPage />} path="/homeworks/:id/edit" />
              <Route element={<HomeworkDetailPage />} path="/homeworks/:id/:tab?" />
              <Route element={<NotFoundPage />} path="/*" />
            </Routes>
          </AuthRequire>
        }
      />
    </Route>
  </Routes>
);
