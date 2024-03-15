import { ThemeProvider, ToasterComponent, ToasterProvider } from '@gravity-ui/uikit';
import './global.css';
import { toaster } from '@gravity-ui/uikit/toaster-singleton-react-18';
import { useEffect } from 'react';

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
        {/* <RouterProvider /> */}
        <ToasterComponent />
      </ToasterProvider>
    </ThemeProvider>
  );
};
