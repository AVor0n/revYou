import { Route, Routes } from 'react-router-dom';
import { HomeworkDetailPage, AuthPage, HomeworksPage, NotFoundPage, ReviewPage } from '@pages';
import { Layout } from '../../../components';
import { AuthRequire } from './AuthRequire';

export const RouterProvider = () => (
  <Routes>
    <Route element={<Layout />}>
      <Route index element={<AuthPage />} path="/auth" />
    </Route>

    <Route element={<Layout />}>
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
