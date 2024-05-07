import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from 'app/providers';
import { App, StoreProvider } from './app';

async function runMockServer() {
  if (import.meta.env.FRONT_ENABLE_MOCK !== 'true') return null;

  const { worker } = await import('./mocks/browser');
  return worker.start();
}

await runMockServer();

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter>
      <StoreProvider>
        <AuthProvider>
          <App />
        </AuthProvider>
      </StoreProvider>
    </BrowserRouter>
  </React.StrictMode>,
);
