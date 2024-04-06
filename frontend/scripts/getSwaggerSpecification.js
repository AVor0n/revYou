import { writeFile, access } from 'fs/promises';
import { get } from 'https';
import { SWAGGER_SPEC_PATH, SWAGGER_URL } from './config.js';

const extractSwaggerJsonFromString = input => {
  const data = input;
  try {
    return JSON.parse(data);
  } catch {
    throw new Error('Не удалось распарсить данные спецификации');
  }
};

/**
 * Получает и парсит файл по URL
 * @param {string} url
 * @returns {object}
 */
const getSwaggerSpecFromUrl = url => {
  return new Promise((resolve, reject) => {
    get(url, response => {
      let data = '';

      response.on('data', chunk => {
        data += chunk;
      });

      response.on('end', () => {
        try {
          resolve(extractSwaggerJsonFromString(data));
        } catch (error) {
          console.error(error);
        }
      });
    }).on('error', () => console.error(`Ошибка при получении файла. Проверить доступность url: ${SWAGGER_URL}`));
  });
};

const isFileExist = async path => {
  return access(path)
    .then(() => true)
    .catch(() => false);
};

export const getSwaggerSpec = async () => {
  const swaggerSpecExist = await isFileExist(SWAGGER_SPEC_PATH);
  if (swaggerSpecExist) {
    console.log(`Файл swagger уже существует ${SWAGGER_SPEC_PATH}`);
  } else {
    console.log(`Создается новый swagger.json с '${SWAGGER_URL}'`);

    return getSwaggerSpecFromUrl(SWAGGER_URL)
      .then(async swaggerJSON => {
        await writeFile(SWAGGER_SPEC_PATH, JSON.stringify(swaggerJSON, null, 2))
          .then(() => console.log(`Файл создан: ${SWAGGER_SPEC_PATH}`))
          .catch(err => {
            throw new Error(`Ошибка при создании файла: ${err}`);
          });

        return swaggerJSON;
      })
      .catch(err => {
        throw new Error(`Ошибка при получении файла: ${err}`);
      });
  }
};
