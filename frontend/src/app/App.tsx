import { ThemeProvider, ToasterComponent, ToasterProvider } from '@gravity-ui/uikit';
import './global.css';
import { RouterProvider } from './providers/RouterProvider/RouterProvider';

export const App = () => (
  <ThemeProvider theme="light">
    <ToasterProvider>
      <RouterProvider />
      <ToasterComponent />
    </ToasterProvider>
  </ThemeProvider>
);
