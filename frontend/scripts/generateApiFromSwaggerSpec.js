import { generateApi } from 'swagger-typescript-api';
import { resolve } from 'path';
import dotenv from 'dotenv';

const frontRoot = process.cwd();
dotenv.config({ path: resolve(frontRoot, '../.env') });

const SWAGGER_URL = process.env.FRONT_SWAGGER_URL;
const SWAGGER_SPEC_PATH = resolve(frontRoot, './scripts/openapi.yaml');
const API_TEMPLATES_PATH = resolve(frontRoot, './src/domains/templates');
const API_OUTPUT_PATH = resolve(frontRoot, './src/domains/__generated__');

generateApi({
  url: SWAGGER_URL,
  output: API_OUTPUT_PATH,
  input: SWAGGER_SPEC_PATH,
  templates: API_TEMPLATES_PATH,
  httpClientType: 'axios',
  generateClient: true,
  generateRouteTypes: false,
  generateResponses: true,
  extractRequestParams: true,
  extractRequestBody: true,
  extractEnums: true,
  defaultResponseType: 'void',
  singleHttpClient: false,
  cleanOutput: true,
  enumNamesAsValues: false,
  moduleNameFirstTag: false,
  generateUnionEnums: true,
  modular: true,
  addReadonly: false,
});
