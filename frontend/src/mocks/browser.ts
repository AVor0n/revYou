import { http, passthrough } from 'msw';
import { setupWorker } from 'msw/browser';
import { homeworksHandlers } from './homeworks';

export const worker = setupWorker(http.get('fonts.gstatic.com/*', passthrough), ...homeworksHandlers);
