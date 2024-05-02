import { http, passthrough } from 'msw';
import { setupWorker } from 'msw/browser';
import { homeworksHandlers } from './homeworks';
import { solutionsHandlers } from './solutions';
import { usersHandlers } from './user';

const ignoreUrls = [
  'https://fonts.googleapis.com/*',
  'https://fonts.gstatic.com/*',
  'https://gitlab.com/*',
  '/node_modules/*',
  '/src/*',
];

export const worker = setupWorker(
  ...ignoreUrls.map(url => http.get(url, passthrough)),
  ...homeworksHandlers,
  ...usersHandlers,
  ...solutionsHandlers,
);
