import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import checker from 'vite-plugin-checker';
import typescriptPaths from 'vite-tsconfig-paths';
import { resolve } from 'path';
import dotenv from 'dotenv';

dotenv.config({ path: resolve(process.cwd(), '../.env') });

const getServerSettings = (env: ImportMetaEnv) => {
  const port = env.FRONT_PORT ? +env.FRONT_PORT : 3000;

  if (env.FRONT_ENABLE_MOCK === 'true') return { port, target: `http://localhost:${port}` };
  if (env.FRONT_HOST) return { port, target: env.FRONT_HOST };
  return { port, target: `http://localhost:${port}` };
};

// https://vitejs.dev/config/
export default defineConfig(() => {
  const { port, target } = getServerSettings(process.env as ImportMetaEnv);

  return {
    server: {
      port,
      strictPort: true,
      proxy: {
        '/api': { target },
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
  };
});
