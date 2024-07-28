/** @type {import('@rtk-query/codegen-openapi').ConfigFile} */
module.exports = {
  schemaFile: './openapi.yaml',
  apiFile: '../src/store/api.ts',
  outputFile: '../src/store/__generated__.ts',
  hooks: {
    lazyQueries: true,
    mutations: true,
    queries: true,
  },
  tag: true,
};
