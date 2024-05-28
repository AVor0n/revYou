import { editor } from 'monaco-editor';
import { useState } from 'react';
import { MonacoDiffEditor } from 'react-monaco-editor';
import { Theme, useTheme } from 'app';
import useResizeObserver from 'use-resize-observer';

interface DiffViewerProps {
  sourceContent: string;
  targetContent: string;
}

export const DiffViewer = ({ sourceContent, targetContent }: DiffViewerProps) => {
  const editorRef = useRef<IEditor.IStandaloneDiffEditor | null>(null);
  const { theme } = useTheme();
    
  const [diffEditor, setDiffEditor] = useState<editor.IDiffEditor | null>(null);

  const onResizeEditorContainer = () => {
    editor.getDiffEditors().forEach(editorPart => editorPart.layout());
  };

  useResizeObserver({
    ref: diffEditor?.getContainerDomNode(),
    onResize: onResizeEditorContainer,
  });

  return (
    <MonacoDiffEditor
      theme={theme === Theme.LIGHT ? 'vs' : 'vs-dark'}
      editorDidMount={setDiffEditor}
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
          enabled: editor.ShowLightbulbIconMode.Off,
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
