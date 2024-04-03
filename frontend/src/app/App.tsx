import { settings } from '@gravity-ui/date-utils';
import { ThemeProvider, ToasterComponent, ToasterProvider } from '@gravity-ui/uikit';
import { RouterProvider } from './providers';
import './global.css';

settings.loadLocale('ru').then(() => {
  settings.setLocale('ru');
});

export const App = () => (
  <ThemeProvider theme="light">
    <ToasterProvider>
      <RouterProvider />
      <ToasterComponent />
    </ToasterProvider>
  </ThemeProvider>
);
