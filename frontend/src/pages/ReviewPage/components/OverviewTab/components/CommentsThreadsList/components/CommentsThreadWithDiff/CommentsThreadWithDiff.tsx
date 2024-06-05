import { Skeleton, Switch, Text } from '@gravity-ui/uikit';
import { useEffect, useState } from 'react';
import { MonacoDiffEditor } from 'react-monaco-editor';
import { EditorSelection, defaultDiffEditorOptions, useResizableDiffEditor } from '@components/MonacoEditor';
import { type CommentsThread as ICommentsThread } from '@domains';
import { Theme, useTheme } from 'app';
import { useFile } from '../../../../../../hooks';
import { CommentsThread } from '../../../../../CommentsThread';
import { useRangeEditorHeight } from './hooks';
import styles from './CommentsThreadWithDiff.module.scss';

interface CommentsThreadWithDiffProps {
  thread: ICommentsThread;
  sourceSha: string;
  targetSha: string;
}

export const CommentsThreadWithDiff = ({ thread, sourceSha, targetSha }: CommentsThreadWithDiffProps) => {
  const { theme } = useTheme();
  const [showOriginalDiff, setShowOriginalDiff] = useState(false);
  const { diffEditor, editorDidMount } = useResizableDiffEditor();
  const sourceFile = useFile({ filePath: thread.filePath, ref: sourceSha });
  const latestFile = useFile({ filePath: thread.filePath, ref: targetSha });
  const targetFile = useFile({ filePath: thread.filePath, ref: thread.commitSha });

  const editorHeight = useRangeEditorHeight({
    diffEditor,
    sourceFile,
    targetFile,
    startLine: thread.startLine,
    endLine: thread.endLine,
  });

  useEffect(() => {
    diffEditor?.revealRangeInCenter({
      endColumn: thread.endSymbol,
      endLineNumber: thread.endLine,
      startColumn: thread.startLine,
      startLineNumber: thread.startLine,
    });
  }, [diffEditor, thread.endLine, thread.endSymbol, thread.startLine]);

  if (sourceFile === undefined || targetFile === undefined || latestFile === undefined) {
    return <Skeleton className={styles.skeleton} />;
  }

  return (
    <CommentsThread thread={thread}>
      <div className={styles.commentedDiffContainer}>
        <div className={styles.header}>
          <Text>{thread.filePath}</Text>
          {latestFile !== targetFile && (
            <Switch checked={showOriginalDiff} onChange={() => setShowOriginalDiff(!showOriginalDiff)}>
              Показать исходную версию
            </Switch>
          )}
        </div>
        <MonacoDiffEditor
          className={styles.editor}
          theme={theme === Theme.LIGHT ? 'vs' : 'vs-dark'}
          height={editorHeight}
          // TODO: сделать автоопределение языка на основе расширения файла
          language="typescript"
          original={sourceFile ?? ''}
          value={showOriginalDiff ? targetFile ?? '' : latestFile ?? ''}
          options={defaultDiffEditorOptions}
          editorDidMount={editorDidMount}
        />
        {diffEditor && (
          <EditorSelection
            editor={thread.commitSha === sourceSha ? diffEditor.getOriginalEditor() : diffEditor.getModifiedEditor()}
            className={styles.commentSelection}
            startLineNumber={thread.startLine}
            startColumn={thread.startSymbol}
            endLineNumber={thread.endLine}
            endColumn={thread.endSymbol}
          />
        )}
      </div>
    </CommentsThread>
  );
};
