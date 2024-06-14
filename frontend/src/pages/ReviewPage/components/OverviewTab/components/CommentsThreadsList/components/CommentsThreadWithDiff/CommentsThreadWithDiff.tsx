import { Skeleton, Switch } from '@gravity-ui/uikit';
import { useEffect, useState } from 'react';
import { MonacoDiffEditor } from 'react-monaco-editor';
import { useNavigate, useParams } from 'react-router-dom';
import { useLazyGetRawFileQuery, type CommentsThread as ICommentsThread } from '@api';
import { Link, EditorSelection, defaultDiffEditorOptions, useResizableDiffEditor } from '@ui';
import { Theme, useTheme } from 'app';
import { useAppSelector } from 'app/hooks';
import { useFileDiffContent } from '../../../../../../hooks';
import { CommentsThread } from '../../../../../CommentsThread';
import { useRangeEditorHeight } from './hooks';
import type { FileNode } from '@shared/types';
import styles from './CommentsThreadWithDiff.module.scss';

interface CommentsThreadWithDiffProps {
  thread: ICommentsThread;
  sourceSha: string;
  targetSha: string;
  file: FileNode;
}

export const CommentsThreadWithDiff = ({ thread, sourceSha, targetSha, file }: CommentsThreadWithDiffProps) => {
  const { theme } = useTheme();
  const navigate = useNavigate();
  const { homeworkId, reviewId, role } = useParams<Record<'homeworkId' | 'reviewId' | 'role', string>>();
  const [showOriginalDiff, setShowOriginalDiff] = useState(false);
  const projectId = useAppSelector(state => state.review.reviewInfo?.projectId);
  const { diffEditor, editorDidMount } = useResizableDiffEditor();
  const { sourceFile, targetFile: latestFile } = useFileDiffContent({ file, sourceSha, targetSha });
  const [loadCommentedFile, { data: commentedFile }] = useLazyGetRawFileQuery();

  useEffect(() => {
    if (projectId === undefined) {
      navigate(`/homeworks/${homeworkId}`);
    } else {
      loadCommentedFile({ projectId, filePath: thread.filePath, ref: thread.commitSha });
    }
  }, [homeworkId, loadCommentedFile, navigate, projectId, thread.commitSha, thread.filePath]);

  const editorHeight = useRangeEditorHeight({
    diffEditor,
    sourceFile,
    targetFile: latestFile,
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

  if (sourceFile === undefined || commentedFile === undefined || latestFile === undefined) {
    return <Skeleton className={styles.skeleton} />;
  }

  return (
    <CommentsThread thread={thread}>
      <div className={styles.commentedDiffContainer}>
        <div className={styles.header}>
          <Link
            href={`/homeworks/${homeworkId}/review/${reviewId}/${role}/files?path=${encodeURIComponent(thread.filePath)}`}
          >
            {thread.filePath}
          </Link>
          {latestFile !== commentedFile && (
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
          original={sourceFile}
          value={showOriginalDiff ? commentedFile : latestFile}
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
