import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import checker from 'vite-plugin-checker';
import typescriptPaths from 'vite-tsconfig-paths';

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080/',
      },
    },
  },
  envPrefix: 'FRONT_',
  envDir: '../',
  plugins: [
    react(),
    typescriptPaths(),
    checker({
      overlay: false,
      typescript: true,
    }),
  ],
  esbuild: {
    supported: {
      'top-level-await': true,
    },
  },
});
