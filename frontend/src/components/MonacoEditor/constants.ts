import { editor as IEditor } from 'monaco-editor';

export const defaultDiffEditorOptions: IEditor.IDiffEditorConstructionOptions = {
  renderSideBySide: true,
  readOnly: true,
  automaticLayout: false,
  contextmenu: false,
  diffAlgorithm: 'advanced',
  enableSplitViewResizing: true,
  experimental: {
    showMoves: true,
  },
  hideCursorInOverviewRuler: false,
  renderOverviewRuler: true,
  lightbulb: {
    // FIXME: doesn't work https://github.com/microsoft/monaco-editor/issues/3873
    enabled: IEditor.ShowLightbulbIconMode.Off,
  },
  lineNumbersMinChars: 4,
  quickSuggestions: false,
  ignoreTrimWhitespace: true,
  occurrencesHighlight: 'off',
  renderIndicators: true,
  selectionHighlight: true,
  selectOnLineNumbers: true,
  copyWithSyntaxHighlighting: false,
  domReadOnly: true,
  glyphMargin: false,
  fontLigatures: true,
  hideUnchangedRegions: {
    contextLineCount: 3,
    enabled: false,
    minimumLineCount: 7,
    revealLineCount: 5,
  },
  renderMarginRevertIcon: false,
  roundedSelection: true,
  overviewRulerBorder: false,
  diffCodeLens: false,
  showDeprecated: true,
  showUnused: false,
  smoothScrolling: true,
  unfoldOnClickAfterEndOfLine: false,
  scrollbar: {
    vertical: 'hidden',
  },
};
