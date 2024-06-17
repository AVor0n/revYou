import { settings } from '@gravity-ui/date-utils';
import { ToasterComponent, ToasterProvider } from '@gravity-ui/uikit';
import { RouterProvider, ThemeProvider } from './providers';
import './global.css';

settings.loadLocale('ru').then(() => {
  settings.setLocale('ru');
});

export const App = () => (
  <ThemeProvider>
    <ToasterProvider>
      <RouterProvider />
      <ToasterComponent />
    </ToasterProvider>
  </ThemeProvider>
);
