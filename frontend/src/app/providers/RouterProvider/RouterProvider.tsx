import { Route, Routes } from 'react-router-dom';
import { AuthPage, HomeworksPage, MainPage, NotFoundPage } from 'pages';

export const RouterProvider = () => (
  <Routes>
    <Route element={<MainPage />} path="/" />
    <Route element={<AuthPage />} path="/auth" />
    <Route element={<HomeworksPage />} path="/homeworks" />
    <Route element={<NotFoundPage />} path="/*" />
  </Routes>
);
