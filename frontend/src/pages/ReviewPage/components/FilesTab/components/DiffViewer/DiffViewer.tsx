import { editor } from 'monaco-editor';
import { useState } from 'react';
import { MonacoDiffEditor } from 'react-monaco-editor';
import useResizeObserver from 'use-resize-observer';
import { type CommentsThread } from '@domains';
import { Theme, useTheme } from 'app';
import { CreateThreadWidget, EditorThreadComments } from './components';
import { editorOptions } from './constants';
import './monaco-worker';
import './DiffViewer.scss';

interface DiffViewerProps {
  sourceContent: string;
  targetContent: string;
  sourceSha: string;
  targetSha: string;
  sourceCommentThreads: CommentsThread[];
  targetCommentThreads: CommentsThread[];
  reviewId: number;
}

export const DiffViewer = ({
  sourceContent,
  targetContent,
  sourceSha,
  targetSha,
  sourceCommentThreads,
  targetCommentThreads,
  reviewId,
}: DiffViewerProps) => {
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
    <>
      <MonacoDiffEditor
        theme={theme === Theme.LIGHT ? 'vs' : 'vs-dark'}
        height="80vh"
        // TODO: сделать автоопределение языка на основе расширения файла
        language="typescript"
        original={sourceContent}
        value={targetContent}
        options={editorOptions}
        editorDidMount={setDiffEditor}
      />
      {diffEditor && (
        <>
          <EditorThreadComments editor={diffEditor.getOriginalEditor()} threads={sourceCommentThreads} />
          <EditorThreadComments editor={diffEditor.getModifiedEditor()} threads={targetCommentThreads} />
          <CreateThreadWidget editor={diffEditor.getOriginalEditor()} commitSha={sourceSha} reviewId={reviewId} />
          <CreateThreadWidget editor={diffEditor.getModifiedEditor()} commitSha={targetSha} reviewId={reviewId} />
        </>
      )}
    </>
  );
};
