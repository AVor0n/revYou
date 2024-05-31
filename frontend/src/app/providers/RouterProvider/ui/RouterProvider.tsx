import { Route, Routes } from 'react-router-dom';
import { HomeworkDetailPage } from '@pages/HomeworkDetailPage';
import { AuthPage, HomeworksPage, NotFoundPage, ReviewPage } from 'pages';
import { Layout } from '../../../components';
import { AuthRequire } from './AuthRequire';

const onLogout = () => {
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
  localStorage.removeItem('userInfo');
};

const navLinks = [{ title: 'Выйти', href: '/auth', element: <AuthPage />, onClick: onLogout }];

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
              <Route index path="/" element={<HomeworksPage />} />
              <Route element={<HomeworksPage />} path="/homeworks/create?" />
              <Route element={<HomeworksPage />} path="/homeworks/:homeworkId/edit" />
              <Route element={<HomeworkDetailPage />} path="/homeworks/:homeworkId/:tab?" />
              <Route element={<ReviewPage />} path="/homeworks/:homeworkId/review/:reviewId/:role/:tab?" />
              <Route element={<NotFoundPage />} path="/*" />
            </Routes>
          </AuthRequire>
        }
      />
    </Route>
  </Routes>
);
