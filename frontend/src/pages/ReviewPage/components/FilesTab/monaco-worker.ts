import EditorWorker from 'monaco-editor/esm/vs/editor/editor.worker?worker';
import CssWorker from 'monaco-editor/esm/vs/language/css/css.worker?worker';
import HtmlWorker from 'monaco-editor/esm/vs/language/html/html.worker?worker';
import JsonWorker from 'monaco-editor/esm/vs/language/json/json.worker?worker';
import TsWorker from 'monaco-editor/esm/vs/language/typescript/ts.worker?worker';

// @ts-expect-error https://github.com/Microsoft/monaco-editor/blob/main/docs/integrate-esm.md#using-vite
globalThis.MonacoEnvironment = {
  getWorker(_: unknown, label: string) {
    if (label === 'json') {
      return new JsonWorker();
    }
    if (['css', 'scss', 'less'].includes(label)) {
      return new CssWorker();
    }
    if (['html', 'handlebars', 'razor'].includes(label)) {
      return new HtmlWorker();
    }
    if (['typescript', 'javascript'].includes(label)) {
      return new TsWorker();
    }
    return new EditorWorker();
  },
};
