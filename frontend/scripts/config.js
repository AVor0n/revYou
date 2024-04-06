import { resolve } from 'path';

export const SWAGGER_URL = '';

export const SWAGGER_SPEC_PATH = resolve(process.cwd(), './scripts/openapi.yaml');

export const API_TEMPLATES_PATH = resolve(process.cwd(), './src/domains/templates');

export const API_OUTPUT_PATH = resolve(process.cwd(), './src/domains/__generated__');
