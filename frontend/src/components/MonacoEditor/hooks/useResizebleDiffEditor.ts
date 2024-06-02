import { editor } from 'monaco-editor';
import { useEffect, useState } from 'react';
import useResizeObserver from 'use-resize-observer';

export const useResizableDiffEditor = () => {
  const [diffEditor, setDiffEditor] = useState<editor.IDiffEditor | undefined>();
  const [editorIsReady, setEditorIsReady] = useState(false);

  const onResizeEditorContainer = () => {
    editor.getDiffEditors().forEach(editorPart => editorPart.layout());
  };

  useResizeObserver({
    ref: diffEditor?.getContainerDomNode(),
    onResize: onResizeEditorContainer,
  });

  useEffect(() => {
    if (diffEditor) {
      diffEditor.getOriginalEditor().onDidChangeModelContent(() => setEditorIsReady(false));
      diffEditor.onDidUpdateDiff(() => setEditorIsReady(true));
    }
  }, [diffEditor]);

  return {
    diffEditor: editorIsReady ? diffEditor : undefined,
    editorDidMount: setDiffEditor,
  };
};
