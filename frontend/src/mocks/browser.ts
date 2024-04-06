import { setupWorker } from 'msw/browser';
import { homeworksHandlers } from './homeworks';

export const worker = setupWorker(...homeworksHandlers);
