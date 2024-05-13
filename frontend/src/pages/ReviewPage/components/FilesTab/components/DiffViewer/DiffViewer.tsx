import { editor as IEditor } from 'monaco-editor';
import { useRef } from 'react';
import { MonacoDiffEditor } from 'react-monaco-editor';

interface DiffViewerProps {
  sourceContent: string;
  targetContent: string;
}

export const DiffViewer = ({ sourceContent, targetContent }: DiffViewerProps) => {
  const editorRef = useRef<IEditor.IStandaloneDiffEditor | null>(null);

  return (
    <MonacoDiffEditor
      editorDidMount={editor => {
        editorRef.current = editor;
      }}
      height="80vh"
      language="typescript"
      original={sourceContent}
      value={targetContent}
      options={{
        renderSideBySide: true,
        readOnly: true,
        automaticLayout: true,
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
          enabled: true,
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
      }}
    />
  );
};
